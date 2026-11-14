/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/// Factory to #create
///
/// @param <P> type of the [primary key][Id#getPk()].
/// @param <R> type of the [revision][Id#getRevision()].
///
/// @since 1.0.0
public interface IdFactory<P, R extends Comparable<?>> {

  /// @param <E> type of the identified entity.
  /// @param entityType the [entity type][Id#getEntityClass()].
  /// @param pk the [primary key][Id#getPk()].
  /// @param revision the [revision][Id#getRevision()]. May be `null`.
  /// @return the [Id] for the given values.
  <E> GenericId<E, P, R, ?> create(Class<E> entityType, P pk, R revision);

  /// @param <E> type of the identified entity.
  /// @param entityType the [entity type][Id#getEntityClass()].
  /// @param pkString the [primary key as string][Id#getAsString()].
  /// @return the parsed [Id].
  default <E> GenericId<E, P, R, ?> create(Class<E> entityType, String pkString) {

    String id = null;
    String revision = null;
    if ((pkString != null) && !pkString.isEmpty()) {
      int i = pkString.indexOf(Id.REVISION_SEPARATOR);
      if (i > 0) {
        id = pkString.substring(0, i);
        revision = pkString.substring(i + 1);
      } else {
        id = pkString;
      }
    }
    return createGeneric(entityType, id, revision);
  }

  /// @param <E> type of the identified entity.
  /// @param entityType the [entity type][Id#getEntityClass()].
  /// @param pk the [primary key][Id#getPk()].
  /// @param revision the [revision][Id#getRevision()]. May be `null`.
  /// @return the new [AbstractId] for the given values.
  @SuppressWarnings("unchecked")
  default <E> GenericId<E, P, R, ?> createGeneric(Class<E> entityType, Object pk, Object revision) {

    if (pk instanceof String) {
      pk = parsePk((String) pk);
    }
    if (revision instanceof String) {
      revision = parseRevision((String) revision);
    }
    return create(entityType, (P) pk, (R) revision);
  }

  /// @param idString the [ID as string][Id#getAsString()].
  /// @return the parsed [ID][Id#getPk()].
  P parsePk(String idString);

  /// @param revisionString the [revision as string][Id#getRevisionAsString()].
  /// @return the parsed [revision][Id#getRevision()].
  R parseRevision(String revisionString);

  /// @param <E> type of the identified [entity][io.github.mmm.entity.Entity].
  /// @param <ID> type of `idClass`.
  /// @param entityType the [entity type][Id#getEntityClass()]. May be `null`.
  /// @param pkClass [Class] reflecting the [Id] implementation. the May be `null` or abstract.
  /// @return the [empty][Id#isEmpty()] [GenericId] instance.
  @SuppressWarnings("rawtypes")
  default <E, ID extends Id> GenericId<E, ?, ?, ?> createEmpty(Class<E> entityType, Class<ID> pkClass) {

    return GenericIdFactory.empty(entityType, pkClass);
  }

  /// @return a generic instance of [IdFactory].
  static IdFactory<Object, Comparable<?>> get() {

    return GenericIdFactory.INSTANCE;
  }

}
