/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.Objects;

/**
 * Implementation of {@link AbstractInstantId} using {@link String} as {@link #getId() primary key}. This is the most
 * generic type of {@link Id}. However {@link LongVersionId} and {@link UuidVersionId} will be more efficient.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class StringInstantId<E> extends AbstractInstantId<E, String> implements StringId<E> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final String id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   */
  public StringInstantId(Class<E> type, String id, Instant version) {

    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public String getId() {

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
  public static class Factory extends StringIdFactory<Instant> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<StringInstantId<?>> getIdClass() {

      return (Class) StringInstantId.class;
    }

    @Override
    public <E> StringId<E> parse(Class<E> type, String id, String version) {

      Instant instant = null;
      if (version != null) {
        instant = Instant.parse(version);
      }
      return create(type, id, instant);
    }
  }

}
