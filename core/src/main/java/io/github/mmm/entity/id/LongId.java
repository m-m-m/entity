/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * {@link Id} using {@link Long} as {@link #get() primary key (ID)}.
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getVersion() version}.
 * @since 1.0.0
 */
public interface LongId<E, V extends Comparable<?>> extends GenericId<E, Long, V> {

  @Override
  Long get();

  @Override
  default Class<Long> getIdType() {

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
   * @param entityType the {@link #getEntityType() entity type}.
   * @return the {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long id, Class<E> entityType) {

    return of(id, entityType, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param id the actual {@link #get() primary key}.
   * @param entityType the {@link #getEntityType() entity type}.
   * @param version the optional {@link #getVersion() version}.
   * @return the new {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long id, Class<E> entityType, Object version) {

    if (id == null) {
      return null;
    }
    if ((version == null) || (version instanceof Long)) {
      return new LongVersionId<>(entityType, id, (Long) version);
    } else if (version instanceof Instant) {
      return new LongInstantId<>(entityType, id, (Instant) version);
    }
    throw new IllegalStateException("Unsupported version type: " + version.getClass().getName());
  }

}
