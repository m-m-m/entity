/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractVersionId} using {@link Long} as {@link #getId() primary key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class LongVersionId<E> extends AbstractVersionId<E, Long> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  public LongVersionId(Class<E> type, long id, long version) {

    this(type, Long.valueOf(id), Long.valueOf(version));
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  protected LongVersionId(Class<E> type, Long id, Long version) {

    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public Long getId() {

    return this.id;
  }

  /**
   * @return the {@link #getId() primary key} as primitve {@code long} value.
   */
  public long getIdAsLong() {

    return this.id.longValue();
  }

  @Override
  public Factory getFactory() {

    return FACTORY;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link LongVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongVersionId<E> of(Class<E> type, Long id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link LongVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongVersionId<E> of(Class<E> type, Long id, Long version) {

    if (id == null) {
      return null;
    }
    return new LongVersionId<>(type, id, version);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory implements IdFactory<Long, Long, LongVersionId<?>> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<LongVersionId<?>> getIdClass() {

      return (Class) LongVersionId.class;
    }

    @Override
    public <E> LongVersionId<E> unmarshall(Class<E> type, String id, String version) {

      return create(type, Long.valueOf(id), Long.valueOf(version));
    }

    @Override
    public <E> LongVersionId<E> create(Class<E> type, Long id, Long version) {

      return of(type, id, version);
    }
  }

}
