/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Factory to #create
 *
 * @param <P> type of the {@link Id#getPk() primary key}.
 * @param <R> type of the {@link Id#getRevision() revision}.
 *
 * @since 1.0.0
 */
public interface IdFactory<P, R extends Comparable<?>> {

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityClass() entity type}.
   * @param pk the {@link Id#getPk() primary key}.
   * @param revision the {@link Id#getRevision() revision}. May be {@code null}.
   * @return the {@link Id} for the given values.
   */
  <E> GenericId<E, P, R, ?> create(Class<E> entityType, P pk, R revision);

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityClass() entity type}.
   * @param pkString the {@link Id#getAsString() primary key as string}.
   * @return the parsed {@link Id}.
   */
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

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityClass() entity type}.
   * @param pk the {@link Id#getPk() primary key}.
   * @param revision the {@link Id#getRevision() revision}. May be {@code null}.
   * @return the new {@link AbstractId} for the given values.
   */
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

  /**
   * @param idString the {@link Id#getAsString() ID as string}.
   * @return the parsed {@link Id#getPk() ID}.
   */
  P parsePk(String idString);

  /**
   * @param revisionString the {@link Id#getRevisionAsString() revision as string}.
   * @return the parsed {@link Id#getRevision() revision}.
   */
  R parseRevision(String revisionString);

  /**
   * @param <E> type of the identified {@link io.github.mmm.entity.Entity entity}.
   * @param <ID> type of {@code idClass}.
   * @param entityType the {@link Id#getEntityClass() entity type}. May be {@code null}.
   * @param pkClass {@link Class} reflecting the {@link Id} implementation. the May be {@code null} or abstract.
   * @return the {@link Id#isEmpty() empty} {@link GenericId} instance.
   */
  @SuppressWarnings("rawtypes")
  default <E, ID extends Id> GenericId<E, ?, ?, ?> createEmpty(Class<E> entityType, Class<ID> pkClass) {

    return GenericIdFactory.empty(entityType, pkClass);
  }

  /**
   * @return a generic instance of {@link IdFactory}.
   */
  static IdFactory<Object, Comparable<?>> get() {

    return GenericIdFactory.INSTANCE;
  }

}
