/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractInstantId} using {@link Long} as {@link #getId() primary key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class LongLatestId<E> extends AbstractLatestId<E, Long> implements LongId<E> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   */
  public LongLatestId(Class<E> type, long id) {

    this(type, Long.valueOf(id));
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   */
  protected LongLatestId(Class<E> type, Long id) {

    super(type);
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
  @Override
  public long getIdAsLong() {

    return this.id.longValue();
  }

  @Override
  protected String getMarshalPropertyId() {

    return PROPERTY_LONG_ID;
  }

  @Override
  public Factory getFactory() {

    return FACTORY;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link LongLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongLatestId<E> of(Class<E> type, Long id) {

    if (id == null) {
      return null;
    }
    return new LongLatestId<>(type, id);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static final class Factory extends LongIdFactory<LatestVersion> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<LongLatestId<?>> getIdClass() {

      return (Class) LongLatestId.class;
    }

    @Override
    public boolean hasVersion() {

      return false;
    }

    @Override
    public <E> LongLatestId<E> parse(Class<E> type, String id, String version) {

      assert (version == null);
      return create(type, Long.valueOf(id), null);
    }

    @Override
    public <E> LongLatestId<E> create(Class<E> type, Long id, LatestVersion version) {

      assert (version == null);
      return of(type, id);
    }
  }

}
