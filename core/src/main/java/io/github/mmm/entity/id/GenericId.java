package io.github.mmm.entity.id;

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
 * @param <P> type of the {@link #getPk() primary key}.
 * @param <R> type of the {@link #getRevision() revision}.
 * @param <SELF> type of this class itself for fluent API calls.
 * @since 1.0.0
 * @see AbstractId
 */
public interface GenericId<E, P, R extends Comparable<?>, SELF extends GenericId<E, P, R, SELF>>
    extends Id<E>, IdFactory<P, R>, MarshallableObject, Unmarshaller<SELF>, StructuredIdMappingObject {

  /**
   * Name of the {@link #getPk() ID} property (e.g. for JSON or XML) in case of a {@link Long}.
   *
   * @see PkIdLong
   */
  String PROPERTY_PK_LONG = "l";

  /**
   * Name of the {@link #getPk() ID} property (e.g. for JSON or XML) in case of a {@link String}.
   *
   * @see PkIdString
   */
  String PROPERTY_PK_STRING = "s";

  /**
   * Name of the {@link #getPk() ID} property (e.g. for JSON or XML) in case of a {@link java.util.UUID}.
   *
   * @see PkIdUuid
   */
  String PROPERTY_PK_UUID = "u";

  /**
   * Name of the {@link #getRevision() revision} property (e.g. for JSON or XML) in case of a {@link Long}.
   *
   * @see RevisionedIdVersion
   */
  String PROPERTY_REVISION_VERSION = "v";

  /**
   * Name of the {@link #getRevision() revision} property (e.g. for JSON or XML) in case of an {@link java.time.Instant}
   * ("t" for "timestamp").
   *
   * @see RevisionedIdInstant
   */
  String PROPERTY_REVISION_INSTANT = "t";

  @Override
  P getPk();

  @Override
  Class<P> getPkClass();

  @Override
  R getRevision();

  /**
   * @return {@code true} if this {@link Id} implementation has a field to store the {@link #getRevision() revision},
   *         {@code false} otherwise (see {@link PkId} and {@link #withoutRevision()}).
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
  default GenericId<E, P, R, ?> create(String valueString) {

    return create(getEntityClass(), valueString);
  }

  @Override
  PkId<E, P, ?> withoutRevision();

  /**
   * @param newRevision the new value of {@link #getRevision() revision}.
   * @return a copy of this {@link Id} with the given {@link #getRevision() revision} or this {@link Id} itself if
   *         already satisfying.
   */
  SELF withRevision(R newRevision);

  /**
   * @param newPk the new value of {@link #getPk() primary key}.
   * @return a copy of this {@link Id} with the given {@link #getPk() primary key} or this {@link Id} itself if already
   *         satisfying.
   */
  SELF withPk(P newPk);

  /**
   * @param newPk the new value of {@link #getPk() primary key}.
   * @param newRevision the new value of {@link #getRevision() revision}.
   * @return a copy of this {@link Id} with the given {@link #getPk() primary key} and {@link #getRevision() revision}
   *         or this {@link Id} itself if already satisfying.
   */
  SELF withPkAndRevision(P newPk, R newRevision);

  @Override
  <T> GenericId<T, P, R, ?> withEntityTypeGeneric(Class<T> newEntityType);

  @Override
  SELF withEntityType(Class<E> newEntityType);

  /**
   * @param currentRevision the current {@link #getRevision() revision}.
   * @return the updated or incremented revision.
   */
  R updateRevision(R currentRevision);

  /**
   * @return a new {@link GenericId} with an {@link #updateRevision(Comparable) updated} {@link #getRevision()
   *         revision}.
   */
  default SELF updateRevision() {

    R newRevision = updateRevision(getRevision());
    return withRevision(newRevision);
  }

  /**
   * @return the property name of the {@link #getPk() id} for marshalling.
   * @see #PROPERTY_PK_LONG
   * @see #PROPERTY_PK_UUID
   * @see #PROPERTY_PK_STRING
   */
  String getMarshalPropertyId();

  /**
   * @return the property name of the {@link #getRevision() revision} for marshalling.
   * @see #PROPERTY_REVISION_VERSION
   * @see #PROPERTY_REVISION_INSTANT
   */
  String getMarshalPropertyRevision();

  @Override
  default void write(StructuredWriter writer) {

    P id = getPk();
    if (id == null) {
      writer.writeValueAsNull();
    } else if (hasRevisionField()) {
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

  @SuppressWarnings("unchecked")
  @Override
  default SELF readObject(StructuredReader reader) {

    return (SELF) IdMarshalling.readObject(reader, this, getEntityClass());
  }

  /**
   * @param <E> type of the identified entity.
   * @param id the {@link Id}.
   * @return the new {@link Id} with {@link #updateRevision() updated revision}.
   */
  public static <E> Id<E> updateRevision(Id<E> id) {

    return ((GenericId<E, ?, ?, ?>) id).updateRevision();
  }

  @Override
  default StructuredIdMapping defineIdMapping() {

    StructuredIdMappingMap map = StructuredIdMappingMap.of(10);
    // primary keys
    map.put(1, PROPERTY_PK_LONG);
    map.put(2, PROPERTY_PK_UUID);
    map.put(3, PROPERTY_PK_STRING);
    // revisions
    map.put(8, PROPERTY_REVISION_VERSION);
    map.put(9, PROPERTY_REVISION_INSTANT);
    return map;
  }

  @Override
  default Object asTypeKey() {

    return Id.class;
  }

}
