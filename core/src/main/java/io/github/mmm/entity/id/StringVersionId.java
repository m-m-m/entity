/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractVersionId} using {@link String} as {@link #get() primary key}. This is the most
 * generic type of {@link Id}. However {@link LongVersionId} and {@link UuidVersionId} will be more efficient.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class StringVersionId<E> extends AbstractVersionId<E, String> implements StringId<E> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final String id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   */
  public StringVersionId(Class<E> type, String id) {

    this(type, id, Long.valueOf(0));
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   * @param version the {@link #getVersion() version}.
   */
  public StringVersionId(Class<E> type, String id, Long version) {

    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public String get() {

    return this.id;
  }

  @Override
  protected String getMarshalPropertyId() {

    return PROPERTY_STRING_ID;
  }

  @Override
  public Factory getFactory() {

    return FACTORY;
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory extends StringIdFactory<Long> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<? extends Id<?>> getIdClass() {

      return (Class) StringVersionId.class;
    }

    @Override
    public <E> StringId<E> parse(Class<E> type, String id, String version) {

      Long longVersion = null;
      if (version != null) {
        longVersion = Long.valueOf(version);
      }
      return create(type, id, longVersion);
    }
  }

}
