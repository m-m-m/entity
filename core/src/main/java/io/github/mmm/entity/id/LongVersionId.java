/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractVersionId} using {@link Long} as {@link #get() primary key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class LongVersionId<E> extends AbstractVersionId<E, Long> implements LongId<E> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  public LongVersionId(Class<E> type, long id, long version) {

    this(type, Long.valueOf(id), Long.valueOf(version));
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  protected LongVersionId(Class<E> type, Long id, Long version) {

    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public Long get() {

    return this.id;
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
   * {@link IdFactory} implementation.
   */
  public static class Factory extends LongIdFactory<Long> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<? extends Id<?>> getIdClass() {

      return (Class) LongVersionId.class;
    }

    @Override
    public <E> LongId<E> parse(Class<E> type, String id, String version) {

      Long longVersion = null;
      if (version != null) {
        longVersion = Long.valueOf(version);
      }
      return create(type, Long.valueOf(id), longVersion);
    }
  }

}
