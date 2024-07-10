/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * {@link Id} using {@link Long} as {@link #getPk() primary key (ID)}.
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 */
public interface LongId<E, V extends Comparable<?>> extends GenericId<E, Long, V> {

  @Override
  Long getPk();

  @Override
  default Class<Long> getPkClass() {

    return Long.class;
  }

  /**
   * @return the {@link #getPk() primary key} as primitve {@code long} value.
   */
  default long getPkAsLong() {

    Long id = getPk();
    if (id == null) {
      return -1;
    }
    return id.longValue();
  }

  @Override
  default Long parsePk(String idString) {

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
  default LongId<E, V> withPkAndRevision(Long newId, V newRevision) {

    return (LongId<E, V>) GenericId.super.withPkAndRevision(newId, newRevision);
  }

  @Override
  default LongId<E, V> withRevision(V newRevision) {

    return (LongId<E, V>) GenericId.super.withRevision(newRevision);
  }

  @Override
  default LongId<E, ?> withoutRevision() {

    return new LongRevisionlessId<>(getEntityClass(), getPk());
  }

  @Override
  default LongId<E, V> updateRevision() {

    return (LongId<E, V>) GenericId.super.updateRevision();
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @return the {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long pk) {

    return of(pk, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @param type the {@link #getEntityClass() entity type}.
   * @return the {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long pk, Class<E> type) {

    if (pk == null) {
      return null;
    }
    return new LongRevisionlessId<>(type, pk);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @param type the {@link #getEntityClass() entity type}.
   * @param revision the optional {@link #getRevision() revision}.
   * @return the new {@link LongId}.
   */
  static <E> LongId<E, ?> of(Long pk, Class<E> type, Object revision) {

    if (pk == null) {
      return null;
    }
    if (revision == null) {
      return new LongRevisionlessId<>(type, pk);
    } else if (revision instanceof Long l) {
      return new LongVersionId<>(type, pk, l);
    } else if (revision instanceof Instant i) {
      return new LongInstantId<>(type, pk, i);
    }
    throw new IllegalStateException("Unsupported revision type: " + revision.getClass().getName());
  }

}
