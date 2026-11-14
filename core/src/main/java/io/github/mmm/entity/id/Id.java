/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.function.Supplier;

import io.github.mmm.entity.Entity;

/// This is the interface for an ID that uniquely identifies a [persistent entity][Entity] of a particular
/// [type][#getEntityClass()] (`<E>`). An [Id] has the following properties:
///
/// - [primary-key][#getPk()] - identifies the entity and is unique for a specific [entity class][#getEntityClass()]. As
///   a best practice it is recommended to make the primary-key even unique for all entities of a database. We provide
///   out-of-the-box support for the types [Long], [java.util.UUID], and [String] as [primary-key][#getPk()].
/// - [revision][#getRevision()] - optional revision of the entity. We support out-of-the-box support for the types
///   [Long] (sequential modification counter) and [Instant] (modification timestamp).
/// - [entity class][#getEntityClass()] - reflects the identified entity.
///
/// Just like the [primary key][#getPk()] the [entity class][#getEntityClass()] of an object does not change. If an
/// [Entity] is updated its [revision][#getRevision()] may change. In this case a new [Id] instance is created and
/// assigned since [Id] is designed as an immutable datatype. To allow using [Id] as globally unique identifier for its
/// corresponding [Entity], regular methods (e.g. `EntityRepository.findById` will ignore the
/// [revision][#getRevision()]). Unlike regular JPA design this [Id] approach gives you a lot of simplicity without
/// loosing any flexibility. So your `EntityBean` and `EntityRepository` does not need any generic type for the primary
/// key and refactoring the type of the primary key or revision will have minimal impact on your entire code-base. An
/// [Id] has a compact [string representation][#toString()]. However, for structured representation and marshaling use
/// [GenericId] and convert to JSON, XML, or other structured format via
/// [readObject][GenericId#readObject(io.github.mmm.marshall.StructuredReader)] and
/// [write][GenericId#write(io.github.mmm.marshall.StructuredWriter)] methods. [Id] is designed for ultimate flexibility
/// and also acts as factory. Therefore an [Entity] instance shall never return `null` for [Entity#getId()]. It will hold
/// a [empty][#isEmpty()] ID that is created at construction time. This way we can support custom [Id] types easily. The
/// only requirement is that all instances of [Id] are proper implementations of [AbstractId]. The class
/// [AbstractId] contains additional complexity and generic types for frameworks. Regular API users only need to use this
/// [Id] interface that hides some complexity. In case you need to create an [Id] from its primary key e.g. in test-code,
/// you may want to use static factory methods such as [#of(Class, Object)].
///
/// @param <E> the [entity type][#getEntityClass()].
///
/// @see AbstractId
/// @since 1.0.0
public interface Id<E> extends Supplier<Object> {

  /// Marshalling property name of the [primary-key][#getPk()].
  String PROPERTY_PK = "pk";

  /// Marshalling property name of the [revision][#getRevision()].
  String PROPERTY_REVISION = "rev";

  /// Column name of the [revision][#getRevision()].
  String COLUMN_REVISION = "REV";

  /// The value used as [revision][#getRevision()] if it unspecified. If you are using an [Id] as link to an
  /// [Entity] you will use this value to point to the recent revision of the [io.github.mmm.entity.Entity].
  Comparable<?> REVISION_LATEST = null;

  /// The separator for the [revision][#getRevision()].
  char REVISION_SEPARATOR = '@';

  /// @see PkIdLong
  /// @see PkIdUuid
  /// @see PkIdString
  ///
  /// @return the _primary key_ of the identified [Entity] as [Object] value. It may only be unique for a particular
  ///         [entity class][#getEntityClass()]. However for [java.util.UUID] it should always be globally unique.
  Object getPk();

  @Override
  default Object get() {

    return getPk();
  }

  /// @return the [primary key][#getPk()] as [String] for marshalling.
  default String getAsString() {

    Object id = getPk();
    if (id == null) {
      return null;
    }
    return id.toString();
  }

  /// @return the [Class] reflecting the [primary key][#getPk()].
  Class<?> getPkClass();

  /// @return `true` if both [primary key][#getPk()] and [revision][#getRevision()] are empty (such [Id] object is used
  ///         as placeholder and factory), `false` otherwise (e.g. if persistent).
  default boolean isEmpty() {

    return (getPk() == null) && (getRevision() == null);
  }

  /// @return the [Class] reflecting the _type_ of the referenced _[entity][io.github.mmm.entity.Entity]_. May be `null`
  ///         if not available.
  Class<E> getEntityClass();

  /// @return a copy of this [Id] without a [revision][#getRevision()] (`null`) e.g. to use the [Id] as regular _foreign
  ///         key_ (pointing to the latest revision and not a historic revision) or this [Id] itself if already
  ///         satisfying. **ATTENTION**: This method may return a different class than it was invoked on. To prevent this
  ///         use [withRevision(null)][GenericId#withRevision(Comparable)].
  Id<E> withoutRevision();

  /// @return the `revision` of this entity. Whenever the [io.github.mmm.entity.Entity] gets updated (a modification is
  ///         saved and the transaction is committed), this revision is increased. It then acts as a modification
  ///         sequence for [optimistic locking][OptimisicLockException]. On each update it will be verified that the
  ///         revision has not been increased already by another transaction.
  ///
  ///         By default the revision is a [Long] starting with [0][RevisionedIdVersion#INSERT_REVISION] for a
  ///         new [io.github.mmm.entity.Entity] and is increased whenever a modification is committed. However, it
  ///         may also be an [java.time.Instant] set to the [current timestamp][Instant#now()] on insert or
  ///         update.
  ///
  ///         When linking an [io.github.mmm.entity.Entity] ([Id] used as foreign key) the revision can act
  ///         as version identifier for auditing. If it is `null` it points to the latest revision of the
  ///         [io.github.mmm.entity.Entity]. Otherwise it points to a specific historic revision of the
  ///         [io.github.mmm.entity.Entity].
  Comparable<?> getRevision();

  /// @return the [Class] reflecting the [revision][#getRevision()].
  Class<? extends Comparable<?>> getRevisionType();

  /// @return the [revision][#getRevision()] as [String] for marshalling.
  default String getRevisionAsString() {

    Object revision = getRevision();
    if (revision == null) {
      return null;
    }
    return revision.toString();
  }

  /// **ATTENTION**: This method is designed to ensure and verify the expected [entity class][#getEntityClass()]. It
  /// will fail if a different type is already assigned.
  ///
  /// @param entityClass the new value of [#getEntityClass()].
  /// @return a copy of this [Id] with the given [entity class][#getEntityClass()] or this [Id] itself if already
  ///         satisfying.
  /// @throws IllegalArgumentException if this [Id] already has a different [entity class][#getEntityClass()] assigned.
  Id<E> withEntityType(Class<E> entityClass);

  /// **ATTENTION**: This method is designed to ensure and verify the expected [type][#getEntityClass()]. It will fail
  /// if a different type is already assigned. It shall be used to cast from `Id<?>` to an [Id] with a properly typed
  /// generic as illustrated by the following example:
  ///
  /// ```
  /// [Id]`<?>` id = entity.getId();
  /// [Class]`<E>` entityType = getEntityType();
  /// [Id]`<E>` typedId = id.withEntityType(entityType);
  /// ```
  ///
  /// @param <T> type
  /// @param newEntityType the new value of [#getEntityClass()]. Exact type should actually be [Class]`<E>` but this
  ///        prevents simple generic usage. As the [type][#getEntityClass()] can not actually be changed with this
  ///        method, this should be fine.
  /// @return a copy of this [Id] with the given [type][#getEntityClass()] or this [Id] itself if already satisfying.
  /// @throws IllegalArgumentException if this [Id] already has a different [type][#getEntityClass()] assigned.
  <T> Id<T> withEntityTypeGeneric(Class<T> newEntityType);

  /// @return `true` if this [Id] is transient if used as [primary key][Entity#getId()], `false` otherwise. Here
  ///         transient means that the [owning][Entity#getId()] [Entity] has never been saved to a persistent store yet.
  ///         Otherwise the [Entity] is persistent and was received from a persistent store. Please note that the
  ///         existence of its [primary key][#getPk()] is not sufficient as it may be assigned already in transient state
  ///         (e.g. for [java.util.UUID]).
  default boolean isTransient() {

    return (getRevision() == null);
  }

  /// @return the [String] representation of this [Id]. Will consist of
  ///         [object-id][#getPk()], [type][#getEntityClass()] and [revision][#getRevision()] separated with a specific
  ///         separator. Segments that are `null` will typically be omitted in the [String] representation.
  @Override
  String toString();

  /// Type-safe and `null`-safe variant of [Entity#getId()].
  ///
  /// @param <E> type of [Entity].
  /// @param entity the [Entity].
  /// @return the [Id] [of][Entity#getId()] the given [Entity]. Will be `null` if the given [Entity] was `null` or
  ///         [it's ID][Entity#getId()] is `null`.
  /// @see Entity#getId()
  @SuppressWarnings({ "unchecked", "rawtypes" })
  static <E extends Entity> Id<E> from(E entity) {

    if (entity == null) {
      return null;
    }
    Id<E> id = (Id<E>) entity.getId();
    if (id.getEntityClass() == null) {
      id = (Id) id.withEntityTypeGeneric(entity.getJavaClass());
    }
    return id;
  }

  /// This is a generic convenience method to create a [revision-less][Id#withoutRevision()] [Id] back from its
  /// [primary key][#getPk()].
  ///
  /// @param <E> type of [Entity].
  /// @param type the [entityClass][#getEntityClass()]
  /// @param pk the [primary key][#getPk()].
  /// @return the [revision-less][Id#withoutRevision()] [Id] for the given arguments.
  /// @see PkId#of(Class, Object)
  static <E> Id<E> of(Class<E> type, Object pk) {

    return PkId.of(type, pk);
  }

  /// @param <E> type of [Entity].
  /// @param type the [entityClass][#getEntityClass()]
  /// @param pk the [primary key][#getPk()].
  /// @param revision
  /// @return the [Id] for the given arguments.
  /// @see IdFactory#create(Class, Object, Comparable)
  static <E> Id<E> of(Class<E> type, Object pk, Comparable<?> revision) {

    return IdFactory.get().create(type, pk, revision);
  }

  /// @param <E> type of [Entity].
  /// @param type the [entityClass][#getEntityClass()]
  /// @param pkType the [type][#getPkClass()] of the [primary key][#getPk()] as short [String] ("long", "uuid", or
  ///        "string").
  /// @param revisionType the [type][#getRevisionType()] of the [revision][#getRevision()] as short [String] ("long" or
  ///        "instant").
  /// @return the [Id] for the given arguments.
  static <E> Id<E> ofEmpty(Class<E> type, String pkType, String revisionType) {

    PkId<E, ?, ?> pkId = switch (pkType) {
      case null -> PkIdLong.getEmpty();
      case "long" -> PkIdLong.getEmpty();
      case "uuid" -> PkIdUuid.getEmpty();
      case "string" -> PkIdString.getEmpty();
      default -> throw new IllegalStateException(pkType);
    };
    pkId = pkId.withEntityType(type);
    return switch (revisionType) {
      case null -> new RevisionedIdVersion<>(pkId, null);
      case "none" -> pkId;
      case "long" -> new RevisionedIdVersion<>(pkId, null);
      case "instant" -> new RevisionedIdInstant<>(pkId, null);
      default -> throw new IllegalStateException(revisionType);
    };
  }

}
