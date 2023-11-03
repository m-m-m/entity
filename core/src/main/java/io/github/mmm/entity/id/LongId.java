/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * {@link Id} using {@link Long} as {@link #get() primary key (ID)}.
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 */
public interface LongId<E, V extends Comparable<?>> extends GenericId<E, Long, V> {

  @Override
  Long get();

  @Override
  default Class<Long> getType() {

    return Long.class;
  }

  /**
   * @return the {@link #get() primary key} as primitve {@code long} value.
   */
  default long getIdAsLong() {

    Long id = get();
    if (id == null) {
      return -1;
    }
    return id.longValue();
  }

  @Override
  default Long parseId(String idString) {

    return Long.valueOf(idString);
  }

  @Override
  default String getMarshalPropertyId() {

    return PROPERTY_LONG_ID;
  }

  @Override
  default <T> LongId<T, V> withEntityType(Class<T> newEntityType) {

    return (LongId<T, V>) GenericId.super.withEntityType(newEntityType);
  }

  @Override
  default LongId<E, V> withEntityTypeGeneric(Class<?> newEntityType) {

    return (LongId<E, V>) GenericId.super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  default LongId<E, V> withIdAndRevision(Long newId, V newRevision) {

    return (LongId<E, V>) GenericId.super.withIdAndRevision(newId, newRevision);
  }

  @Override
  default LongId<E, V> withRevision(V newRevision) {

    return (LongId<E, V>) GenericId.super.withRevision(newRevision);
  }

  @SuppressWarnings("unchecked")
  @Override
  default LongId<E, V> withoutRevision() {

    return (LongId<E, V>) GenericId.super.withoutRevision();
  }

  @Override
  default LongId<E, V> updateRevision() {

    return (LongId<E, V>) GenericId.super.updateRevision();
  }

  /**
   * @param <E> type of the referenced entity.
   * @param id the actual {@link #get() primary key}.
   * @return the {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long id) {

    return of(id, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param id the actual {@link #get() primary key}.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long id, Class<E> entityType) {

    return of(id, entityType, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param id the actual {@link #get() primary key}.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @param revision the optional {@link #getRevision() revision}.
   * @return the new {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long id, Class<E> entityType, Object revision) {

    if (id == null) {
      return null;
    }
    if ((revision == null) || (revision instanceof Long)) {
      return new LongVersionId<>(entityType, id, (Long) revision);
    } else if (revision instanceof Instant) {
      return new LongInstantId<>(entityType, id, (Instant) revision);
    }
    throw new IllegalStateException("Unsupported revision type: " + revision.getClass().getName());
  }

}
