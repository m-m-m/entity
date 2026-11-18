package io.github.mmm.entity.id;

import io.github.mmm.marshall.MarshallableObject;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.marshall.Unmarshaller;
import io.github.mmm.marshall.id.StructuredIdMapping;
import io.github.mmm.marshall.id.StructuredIdMappingMap;
import io.github.mmm.marshall.id.StructuredIdMappingObject;

/// Interface extending [Id] with generic features to be used by frameworks.
///
/// @param <E> type of the identified [io.github.mmm.entity.Entity].
/// @param <P> type of the [primary key][#getPk()].
/// @param <R> type of the [revision][#getRevision()].
/// @param <SELF> type of this class itself for fluent API calls.
/// @since 1.0.0
/// @see AbstractId
public interface GenericId<E, P, R extends Comparable<?>, SELF extends GenericId<E, P, R, SELF>>
    extends Id<E>, IdFactory<P, R>, MarshallableObject, Unmarshaller<SELF>, StructuredIdMappingObject {

  /// Name of the [ID][#getPk()] property (e.g. for JSON or XML) in case of a [Long].
  ///
  /// @see PkIdLong
  String PROPERTY_PK_LONG = "l";

  /// Name of the [ID][#getPk()] property (e.g. for JSON or XML) in case of a [String].
  ///
  /// @see PkIdString
  String PROPERTY_PK_STRING = "s";

  /// Name of the [ID][#getPk()] property (e.g. for JSON or XML) in case of a [java.util.UUID].
  ///
  /// @see PkIdUuid
  String PROPERTY_PK_UUID = "u";

  /// Name of the [revision][#getRevision()] property (e.g. for JSON or XML) in case of a [Long].
  ///
  /// @see RevisionedIdVersion
  String PROPERTY_REVISION_VERSION = "v";

  /// Name of the [revision][#getRevision()] property (e.g. for JSON or XML) in case of an [java.time.Instant] ("t" for "timestamp").
  ///
  /// @see RevisionedIdInstant
  String PROPERTY_REVISION_INSTANT = "t";

  @Override
  P getPk();

  @Override
  Class<P> getPkClass();

  @Override
  R getRevision();

  @Override
  Class<R> getRevisionType();

  /// @param valueString the [string representation][#toString()] of this [GenericId].
  /// @return the parsed [GenericId].
  default GenericId<E, P, R, ?> create(String valueString) {

    return create(getEntityClass(), valueString);
  }

  @Override
  PkId<E, P, ?> withoutRevision();

  /// @param newRevision the new value of [revision][#getRevision()].
  /// @return a copy of this [Id] with the given [revision][#getRevision()] or this [Id] itself if already satisfying.
  SELF withRevision(R newRevision);

  /// @param newPk the new value of [primary key][#getPk()].
  /// @return a copy of this [Id] with the given [primary key][#getPk()] or this [Id] itself if already satisfying.
  SELF withPk(P newPk);

  /// @param newPk the new value of [primary key][#getPk()].
  /// @param newRevision the new value of [revision][#getRevision()].
  /// @return a copy of this [Id] with the given [primary key][#getPk()] and [revision][#getRevision()] or this [Id] itself if already satisfying.
  SELF withPkAndRevision(P newPk, R newRevision);

  @Override
  <T> GenericId<T, P, R, ?> withEntityTypeGeneric(Class<T> newEntityType);

  @Override
  SELF withEntityType(Class<E> newEntityType);

  /// @param currentRevision the current [revision][#getRevision()].
  /// @return the updated or incremented revision.
  R updateRevision(R currentRevision);

  /// @return a new [GenericId] with an [updated][#updateRevision(Comparable)] [revision][#getRevision()].
  default SELF updateRevision() {

    R newRevision = updateRevision(getRevision());
    return withRevision(newRevision);
  }

  /// @return the property name of the [id][#getPk()] for marshalling.
  /// @see #PROPERTY_PK_LONG
  /// @see #PROPERTY_PK_UUID
  /// @see #PROPERTY_PK_STRING
  String getMarshalPropertyId();

  /// @return the property name of the [revision][#getRevision()] for marshalling.
  /// @see #PROPERTY_REVISION_VERSION
  /// @see #PROPERTY_REVISION_INSTANT
  String getMarshalPropertyRevision();

  @Override
  default void write(StructuredWriter writer) {

    P pk = getPk();
    if (pk == null) {
      writer.writeValueAsNull();
    } else if (hasRevisionField()) {
      R revision = getRevision();
      writer.writeStartObject(this);
      writer.writeName(getMarshalPropertyId());
      assert (pk != null);
      writer.writeValue(pk);
      writer.writeName(getMarshalPropertyRevision());
      writer.writeValue(revision);
      writer.writeEnd();
    } else {
      writer.writeValue(pk);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  default SELF readObject(StructuredReader reader) {

    return (SELF) IdMarshalling.readObject(reader, this, getEntityClass());
  }

  /// @param <E> type of the identified entity.
  /// @param id the [Id].
  /// @return the new [Id] with [updated revision][#updateRevision()].
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
