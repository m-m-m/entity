/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

/**
 * {@link Id} using {@link UUID} as {@link #getId() primary key (ID)}.
 *
 * @param <E> type of the identified entity.
 *
 * @since 1.0.0
 */
public interface UuidId<E> extends Id<E> {

  @Override
  UUID getId();

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link LongLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  static <E> UuidId<E> of(Class<E> type, UUID id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the optional {@link #getVersion() version}.
   * @return the new {@link LongLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  static <E> UuidId<E> of(Class<E> type, UUID id, Object version) {

    if (id == null) {
      return null;
    }
    if (version == null) {
      return new UuidLatestId<>(type, id);
    } else if (version instanceof Long) {
      return new UuidVersionId<>(type, id, (Long) version);
    } else if (version instanceof Instant) {
      return new UuidInstantId<>(type, id, (Instant) version);
    }
    throw new IllegalStateException("Unsupported version type: " + version.getClass().getName());
  }

  /**
   * Abstract base implementation of {@link IdFactory}.
   *
   * @param <V> type of the {@link Id#getVersion() version}.
   */
  abstract class UuidIdFactory<V extends Comparable<?>> implements IdFactory<UUID, V> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<? extends Id<?>> getIdInterface() {

      return (Class) UuidId.class;
    }

    @Override
    public <E> UuidId<E> create(Class<E> type, UUID id, V version) {

      return UuidId.of(type, id, version);
    }
  }
}
