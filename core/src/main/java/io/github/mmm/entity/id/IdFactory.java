/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Factory to #create
 *
 * @param <I> type of the {@link Id#get() primary key}.
 * @param <V> type of the {@link Id#getVersion() version}.
 *
 * @since 1.0.0
 */
public interface IdFactory<I, V extends Comparable<?>> {

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityType() entity type}.
   * @param id the {@link Id#get() primary key}.
   * @param version the {@link Id#getVersion() version}. May be {@code null}.
   * @return the {@link Id} for the given values.
   */
  <E> GenericId<E, I, V> create(Class<E> entityType, I id, V version);

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityType() entity type}.
   * @param idString the {@link Id#getIdAsString() primary key as string}.
   * @param version the {@link Id#getVersionAsString() version as string}. May be {@code null}.
   * @return the parsed {@link Id}.
   */
  default <E> GenericId<E, I, V> create(Class<E> entityType, String idString) {

    String id = null;
    String version = null;
    if ((idString != null) && !idString.isEmpty()) {
      int i = idString.indexOf(Id.VERSION_SEPARATOR);
      if (i > 0) {
        id = idString.substring(0, i);
        version = idString.substring(i + 1);
      } else {
        id = idString;
      }
    }
    return createGeneric(entityType, id, version);
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link Id#getEntityType() entity type}.
   * @param id the {@link Id#get() primary key}.
   * @param version the {@link Id#getVersion() version}. May be {@code null}.
   * @return the new {@link AbstractId} for the given values.
   */
  @SuppressWarnings("unchecked")
  default <E> GenericId<E, I, V> createGeneric(Class<E> entityType, Object id, Object version) {

    if (id instanceof String) {
      id = parseId((String) id);
    }
    if (version instanceof String) {
      version = parseVersion((String) version);
    }
    return create(entityType, (I) id, (V) version);
  }

  /**
   * @param idString the {@link Id#getIdAsString() ID as string}.
   * @return the parsed {@link Id#get() ID}.
   */
  I parseId(String idString);

  /**
   * @param versionString the {@link Id#getVersionAsString() version as string}.
   * @return the parsed {@link Id#getVersion() version}.
   */
  V parseVersion(String versionString);

  /**
   * @return a generic instance of {@link IdFactory}.
   */
  static IdFactory<Object, Comparable<?>> get() {

    return GenericIdFactory.INSTANCE;
  }

}
