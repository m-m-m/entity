package io.github.mmm.entity.id;

import java.util.Objects;

import io.github.mmm.marshall.MarshallableObject;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.marshall.Unmarshaller;

/**
 * Interface extending {@link Id} with generic features to be used by frameworks.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #get() ID}.
 * @param <V> type of the {@link #getVersion() version}.
 * @since 1.0.0
 * @see AbstractId
 */
public interface GenericId<E, I, V extends Comparable<?>>
    extends Id<E>, IdFactory<I, V>, MarshallableObject, Unmarshaller<GenericId<E, I, V>> {

  /**
   * Name of the {@link #get() ID} property (e.g. for JSON or XML) in case of a {@link Long}.
   *
   * @see LongInstantId
   * @see LongVersionId
   */
  String PROPERTY_LONG_ID = "l";

  /**
   * Name of the {@link #get() ID} property (e.g. for JSON or XML) in case of a {@link String}.
   *
   * @see StringInstantId
   * @see StringVersionId
   */
  String PROPERTY_STRING_ID = "s";

  /**
   * Name of the {@link #get() ID} property (e.g. for JSON or XML) in case of a {@link java.util.UUID}.
   *
   * @see UuidInstantId
   * @see UuidVersionId
   */
  String PROPERTY_UUID = "u";

  /**
   * Name of the {@link #getVersion() version} property (e.g. for JSON or XML) in case of a {@link Long}.
   *
   * @see LongVersionId
   * @see UuidVersionId
   * @see StringVersionId
   */
  String PROPERTY_LONG_VERSION = "v";

  /**
   * Name of the {@link #getVersion() version} property (e.g. for JSON or XML) in case of an {@link java.time.Instant}
   * ("t" for "timestamp").
   *
   * @see LongInstantId
   * @see UuidInstantId
   * @see StringInstantId
   */
  String PROPERTY_INSTANT_VERSION = "t";

  @Override
  I get();

  /**
   * @return the {@link Class} reflecting the {@link #get() primary key}.
   */
  @Override
  Class<I> getIdType();

  @Override
  V getVersion();

  /**
   * @return the {@link Class} reflecting the {@link #getVersion() version}.
   */
  @Override
  Class<V> getVersionType();

  /**
   * @param valueString the {@link #toString() string representation} of this {@link GenericId}.
   * @return the parsed {@link GenericId}.
   */
  default GenericId<E, I, V> create(String valueString) {

    return create(getEntityType(), valueString);
  }

  @Override
  default Id<E> withoutVersion() {

    return withVersion(null);
  }

  /**
   * @param newVersion the new value of {@link #getVersion() version}.
   * @return a copy of this {@link Id} with the given {@link #getVersion() version} or this {@link Id} itself if already
   *         satisfying.
   */
  default GenericId<E, I, V> withVersion(V newVersion) {

    if (Objects.equals(getVersion(), newVersion)) {
      return this;
    }
    return create(getEntityType(), get(), newVersion);
  }

  /**
   * @param newId the new value of {@link #get() primary key}.
   * @param newVersion the new value of {@link #getVersion() version}.
   * @return a copy of this {@link Id} with the given {@link #get() primary key} and {@link #getVersion() version} or
   *         this {@link Id} itself if already satisfying.
   */
  default GenericId<E, I, V> withIdAndVersion(I newId, V newVersion) {

    if (Objects.equals(getVersion(), newVersion) && Objects.equals(get(), newId)) {
      return this;
    }
    return create(getEntityType(), newId, newVersion);
  }

  /**
   * <b>ATTENTION</b>: This method is designed to ensure and verify the expected {@link #getEntityType() type}. It will
   * fail if a different type is already assigned.
   *
   * @param newEntityType the new value of {@link #getEntityType()}. Exact type should actually be
   *        {@link Class}{@literal <E>} but this prevents simple generic usage. As the {@link #getEntityType() type} can
   *        not actually be changed with this method, this should be fine.
   * @return a copy of this {@link Id} with the given {@link #getEntityType() type} or this {@link Id} itself if already
   *         satisfying.
   * @throws IllegalArgumentException if this {@link Id} already has a different {@link #getEntityType() type} assigned.
   */
  @SuppressWarnings("unchecked")
  default GenericId<E, I, V> withEntityType(Class<?> newEntityType) {

    Class<E> entityType = getEntityType();
    if (entityType == null) {
      return create((Class<E>) newEntityType, get(), getVersion());
    } else if (entityType != newEntityType) {
      throw new IllegalArgumentException(
          "Illegal type " + newEntityType + " - already typed to " + entityType.getName() + " at " + toString());
    }
    return this;
  }

  /**
   * @return the property name of the {@link #get() id} for marshalling.
   * @see #PROPERTY_LONG_ID
   * @see #PROPERTY_UUID
   * @see #PROPERTY_STRING_ID
   */
  String getMarshalPropertyId();

  /**
   * @return the property name of the {@link #getVersion() version} for marshalling.
   * @see #PROPERTY_LONG_VERSION
   * @see #PROPERTY_INSTANT_VERSION
   */
  String getMarshalPropertyVersion();

  @SuppressWarnings("exports")
  @Override
  default void write(StructuredWriter writer) {

    V version = getVersion();
    I id = get();
    if (version == null) {
      if (id == null) {
        writer.writeValueAsNull();
      } else {
        writer.writeValue(id);
      }
    } else {
      writer.writeStartObject();
      writer.writeName(getMarshalPropertyId());
      assert (id != null);
      writer.writeValue(id);
      writer.writeName(getMarshalPropertyVersion());
      writer.writeValue(version);
      writer.writeEnd();
    }
  }

  @SuppressWarnings("exports")
  @Override
  default GenericId<E, I, V> readObject(StructuredReader reader) {

    return IdMarshalling.readObject(reader, this, getEntityType());
  }

}
