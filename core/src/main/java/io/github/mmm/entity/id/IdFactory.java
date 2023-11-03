/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Factory to #create
 *
 * @param <I> type of the {@link Id#get() primary key}.
 * @param <V> type of the {@link Id#getRevision() revision}.
 *
 * @since 1.0.0
 */
public interface IdFactory<I, V extends Comparable<?>> {

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityClass() entity type}.
   * @param id the {@link Id#get() primary key}.
   * @param revision the {@link Id#getRevision() revision}. May be {@code null}.
   * @return the {@link Id} for the given values.
   */
  <E> GenericId<E, I, V> create(Class<E> entityType, I id, V revision);

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityClass() entity type}.
   * @param idString the {@link Id#getAsString() primary key as string}.
   * @return the parsed {@link Id}.
   */
  default <E> GenericId<E, I, V> create(Class<E> entityType, String idString) {

    String id = null;
    String revision = null;
    if ((idString != null) && !idString.isEmpty()) {
      int i = idString.indexOf(Id.REVISION_SEPARATOR);
      if (i > 0) {
        id = idString.substring(0, i);
        revision = idString.substring(i + 1);
      } else {
        id = idString;
      }
    }
    return createGeneric(entityType, id, revision);
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityClass() entity type}.
   * @param id the {@link Id#get() primary key}.
   * @param revision the {@link Id#getRevision() revision}. May be {@code null}.
   * @return the new {@link AbstractId} for the given values.
   */
  @SuppressWarnings("unchecked")
  default <E> GenericId<E, I, V> createGeneric(Class<E> entityType, Object id, Object revision) {

    if (id instanceof String) {
      id = parseId((String) id);
    }
    if (revision instanceof String) {
      revision = parseRevision((String) revision);
    }
    return create(entityType, (I) id, (V) revision);
  }

  /**
   * @param idString the {@link Id#getAsString() ID as string}.
   * @return the parsed {@link Id#get() ID}.
   */
  I parseId(String idString);

  /**
   * @param revisionString the {@link Id#getRevisionAsString() revision as string}.
   * @return the parsed {@link Id#getRevision() revision}.
   */
  V parseRevision(String revisionString);

  /**
   * @param <E> type of the identified {@link io.github.mmm.entity.Entity entity}.
   * @param <ID> type of {@code idClass}.
   * @param entityType the {@link Id#getEntityClass() entity type}. May be {@code null}.
   * @param idClass {@link Class} reflecting the {@link Id} implementation. the May be {@code null} or abstract.
   * @return the {@link Id#isEmpty() empty} {@link GenericId} instance.
   */
  @SuppressWarnings("rawtypes")
  default <E, ID extends Id> GenericId<E, ?, ?> createEmpty(Class<E> entityType, Class<ID> idClass) {

    return GenericIdFactory.empty(entityType, idClass);
  }

  /**
   * @return a generic instance of {@link IdFactory}.
   */
  static IdFactory<Object, Comparable<?>> get() {

    return GenericIdFactory.INSTANCE;
  }

}
