/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * {@link Id} using {@link String} as {@link #get() primary key (ID)}.
 *
 * @param <E> type of the identified entity.
 *
 * @since 1.0.0
 */
public interface StringId<E> extends Id<E> {

  @Override
  String get();

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   * @return the new {@link LongLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  static <E> StringId<E> of(Class<E> type, String id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   * @param version the optional {@link #getVersion() version}.
   * @return the new {@link LongLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  static <E> StringId<E> of(Class<E> type, String id, Object version) {

    if (id == null) {
      return null;
    }
    if (version == null) {
      return new StringLatestId<>(type, id);
    } else if (version instanceof Long) {
      return new StringVersionId<>(type, id, (Long) version);
    } else if (version instanceof Instant) {
      return new StringInstantId<>(type, id, (Instant) version);
    }
    throw new IllegalStateException("Unsupported version type: " + version.getClass().getName());
  }

  /**
   * Abstract base implementation of {@link IdFactory}.
   *
   * @param <V> type of the {@link Id#getVersion() version}.
   */
  abstract class StringIdFactory<V extends Comparable<?>> implements IdFactory<String, V> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<? extends Id<?>> getIdInterface() {

      return (Class) StringId.class;
    }

    @Override
    public <E> StringId<E> create(Class<E> type, String id, V version) {

      return StringId.of(type, id, version);
    }
  }

}
