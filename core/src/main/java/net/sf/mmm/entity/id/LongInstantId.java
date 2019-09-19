/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.id;

import java.time.Instant;
import java.util.Objects;

/**
 * Implementation of {@link AbstractInstantId} using {@link Long} as {@link #getId() primary key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class LongInstantId<E> extends AbstractInstantId<E, Long> {

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
  public LongInstantId(Class<E> type, long id, Instant version) {

    this(type, Long.valueOf(id), version);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  protected LongInstantId(Class<E> type, Long id, Instant version) {

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
   * @return the new {@link LongInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongInstantId<E> of(Class<E> type, Long id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link LongInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongInstantId<E> of(Class<E> type, Long id, Instant version) {

    if (id == null) {
      return null;
    }
    return new LongInstantId<>(type, id, version);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static final class Factory implements IdFactory<Long, Instant, LongInstantId<?>> {

    private Factory() {

      super();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<LongInstantId<?>> getIdClass() {

      return (Class) LongInstantId.class;
    }

    @Override
    public <E> LongInstantId<E> unmarshall(Class<E> type, String id, String version) {

      return create(type, Long.valueOf(id), Instant.parse(version));
    }

    @Override
    public <E> LongInstantId<E> create(Class<E> type, Long id, Instant version) {

      return of(type, id, version);
    }
  }

}
