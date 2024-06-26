package io.github.mmm.entity.id;

import java.util.Objects;

import io.github.mmm.marshall.MarshallableObject;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.marshall.Unmarshaller;
import io.github.mmm.marshall.id.StructuredIdMapping;
import io.github.mmm.marshall.id.StructuredIdMappingMap;
import io.github.mmm.marshall.id.StructuredIdMappingObject;

/**
 * Interface extending {@link Id} with generic features to be used by frameworks.
 *
 * @param <E> type of the identified {@link io.github.mmm.entity.Entity}.
 * @param <I> type of the {@link #get() ID}.
 * @param <R> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 * @see AbstractId
 */
public interface GenericId<E, I, R extends Comparable<?>>
    extends Id<E>, IdFactory<I, R>, MarshallableObject, Unmarshaller<GenericId<E, I, R>>, StructuredIdMappingObject {

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
   * Name of the {@link #getRevision() revision} property (e.g. for JSON or XML) in case of a {@link Long}.
   *
   * @see LongVersionId
   * @see UuidVersionId
   * @see StringVersionId
   */
  String PROPERTY_LONG_REVISION = "v";

  /**
   * Name of the {@link #getRevision() revision} property (e.g. for JSON or XML) in case of an {@link java.time.Instant}
   * ("t" for "timestamp").
   *
   * @see LongInstantId
   * @see UuidInstantId
   * @see StringInstantId
   */
  String PROPERTY_INSTANT_REVISION = "t";

  @Override
  I get();

  /**
   * @return the {@link Class} reflecting the {@link #get() primary key}.
   */
  @Override
  Class<I> getType();

  @Override
  R getRevision();

  /**
   * @return {@code true} if this {@link Id} implementation has a field to store the {@link #getRevision() revision},
   *         {@code false} otherwise (see {@link NoRevision} and {@link AbstractRevisionlessId}).
   */
  boolean hasRevisionField();

  /**
   * @return the {@link Class} reflecting the {@link #getRevision() revision}.
   */
  @Override
  Class<R> getRevisionType();

  /**
   * @param valueString the {@link #toString() string representation} of this {@link GenericId}.
   * @return the parsed {@link GenericId}.
   */
  default GenericId<E, I, R> create(String valueString) {

    return create(getEntityClass(), valueString);
  }

  @Override
  default Id<E> withoutRevision() {

    return withRevision(null);
  }

  /**
   * @param newRevision the new value of {@link #getRevision() revision}.
   * @return a copy of this {@link Id} with the given {@link #getRevision() revision} or this {@link Id} itself if
   *         already satisfying.
   */
  default GenericId<E, I, R> withRevision(R newRevision) {

    if (Objects.equals(getRevision(), newRevision)) {
      return this;
    }
    return create(getEntityClass(), get(), newRevision);
  }

  /**
   * @param newId the new value of {@link #get() primary key}.
   * @param newRevision the new value of {@link #getRevision() revision}.
   * @return a copy of this {@link Id} with the given {@link #get() primary key} and {@link #getRevision() revision} or
   *         this {@link Id} itself if already satisfying.
   */
  default GenericId<E, I, R> withIdAndRevision(I newId, R newRevision) {

    if (Objects.equals(getRevision(), newRevision) && Objects.equals(get(), newId)) {
      return this;
    }
    return create(getEntityClass(), newId, newRevision);
  }

  @SuppressWarnings("unchecked")
  @Override
  default <T> GenericId<T, I, R> withEntityType(Class<T> newEntityType) {

    Class<E> entityType = getEntityClass();
    if (entityType == null) {
      return create(newEntityType, get(), getRevision());
    } else if (entityType != newEntityType) {
      throw new IllegalArgumentException(
          "Illegal type " + newEntityType + " - already typed to " + entityType.getName() + " at " + toString());
    }
    return (GenericId<T, I, R>) this;
  }

  /**
   * <b>ATTENTION</b>: This method is designed to ensure and verify the expected {@link #getEntityClass() type}. It will
   * fail if a different type is already assigned.
   *
   * @param newEntityType the new value of {@link #getEntityClass()}. Exact type should actually be
   *        {@link Class}{@literal <E>} but this prevents simple generic usage. As the {@link #getEntityClass() type}
   *        can not actually be changed with this method, this should be fine.
   * @return a copy of this {@link Id} with the given {@link #getEntityClass() type} or this {@link Id} itself if
   *         already satisfying.
   * @throws IllegalArgumentException if this {@link Id} already has a different {@link #getEntityClass() type}
   *         assigned.
   */
  @SuppressWarnings("unchecked")
  default GenericId<E, I, R> withEntityTypeGeneric(Class<?> newEntityType) {

    return (GenericId<E, I, R>) withEntityType(newEntityType);
  }

  /**
   * @param currentRevision the current {@link #getRevision() revision}.
   * @return the updated or incremented revision.
   */
  R updateRevision(R currentRevision);

  /**
   * @return a new {@link GenericId} with an {@link #updateRevision(Comparable) updated} {@link #getRevision()
   *         revision}.
   */
  default GenericId<E, I, R> updateRevision() {

    R newRevision = updateRevision(getRevision());
    return withRevision(newRevision);
  }

  /**
   * @return the property name of the {@link #get() id} for marshalling.
   * @see #PROPERTY_LONG_ID
   * @see #PROPERTY_UUID
   * @see #PROPERTY_STRING_ID
   */
  String getMarshalPropertyId();

  /**
   * @return the property name of the {@link #getRevision() revision} for marshalling.
   * @see #PROPERTY_LONG_REVISION
   * @see #PROPERTY_INSTANT_REVISION
   */
  String getMarshalPropertyRevision();

  @Override
  default void write(StructuredWriter writer) {

    I id = get();
    if (hasRevisionField()) {
      R revision = getRevision();
      writer.writeStartObject(this);
      writer.writeName(getMarshalPropertyId());
      assert (id != null);
      writer.writeValue(id);
      writer.writeName(getMarshalPropertyRevision());
      writer.writeValue(revision);
      writer.writeEnd();
    } else {
      writer.writeValue(id);
    }
  }

  @Override
  default GenericId<E, I, R> readObject(StructuredReader reader) {

    return IdMarshalling.readObject(reader, this, getEntityClass());
  }

  /**
   * @param <E> type of the identified entity.
   * @param id the {@link Id}.
   * @return the new {@link Id} with {@link #updateRevision() updated revision}.
   */
  public static <E> Id<E> updateRevision(Id<E> id) {

    return ((GenericId<E, ?, ?>) id).updateRevision();
  }

  @Override
  default StructuredIdMapping defineIdMapping() {

    StructuredIdMappingMap map = StructuredIdMappingMap.of(10);
    // primary keys
    map.put(1, PROPERTY_LONG_ID);
    map.put(2, PROPERTY_UUID);
    map.put(3, PROPERTY_STRING_ID);
    // revisions
    map.put(8, PROPERTY_LONG_REVISION);
    map.put(9, PROPERTY_INSTANT_REVISION);
    return map;
  }

  @Override
  default Object asTypeKey() {

    return Id.class;
  }

}
