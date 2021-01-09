/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * {@link Id} using {@link Long} as {@link #getId() primary key (ID)}.
 *
 * @param <E> type of the identified entity.
 *
 * @since 1.0.0
 */
public interface LongId<E> extends Id<E> {

  @Override
  Long getId();

  /**
   * @return the {@link #getId() primary key} as primitve {@code long} value.
   */
  default long getIdAsLong() {

    return getId().longValue();
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link LongLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  static <E> LongId<E> of(Class<E> type, Long id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the optional {@link #getVersion() version}.
   * @return the new {@link LongLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  static <E> LongId<E> of(Class<E> type, Long id, Object version) {

    if (id == null) {
      return null;
    }
    if (version == null) {
      return new LongLatestId<>(type, id);
    } else if (version instanceof Long) {
      return new LongVersionId<>(type, id, (Long) version);
    } else if (version instanceof Instant) {
      return new LongInstantId<>(type, id, (Instant) version);
    }
    throw new IllegalStateException("Unsupported version type: " + version.getClass().getName());
  }

  /**
   * Abstract base implementation of {@link IdFactory}.
   *
   * @param <V> type of the {@link Id#getVersion() version}.
   */
  abstract class LongIdFactory<V extends Comparable<?>> implements IdFactory<Long, V> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<? extends Id<?>> getIdInterface() {

      return (Class) LongId.class;
    }

    @Override
    public <E> LongId<E> create(Class<E> type, Long id, V version) {

      return LongId.of(type, id, version);
    }
  }
}
