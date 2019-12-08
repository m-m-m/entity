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
public class StringInstantId<E> extends AbstractInstantId<E, String> {

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
  public Factory getFactory() {

    return FACTORY;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link StringInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringInstantId<E> of(Class<E> type, String id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link StringInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringInstantId<E> of(Class<E> type, String id, Instant version) {

    if (id == null) {
      return null;
    }
    return new StringInstantId<>(type, id, version);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory implements IdFactory<String, Instant, StringInstantId<?>> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<StringInstantId<?>> getIdClass() {

      return (Class) StringInstantId.class;
    }

    @Override
    public <E> StringInstantId<E> unmarshall(Class<E> type, String id, String version) {

      return create(type, id, Instant.parse(version));
    }

    @Override
    public <E> StringInstantId<E> create(Class<E> type, String id, Instant version) {

      return of(type, id, version);
    }
  }

}
