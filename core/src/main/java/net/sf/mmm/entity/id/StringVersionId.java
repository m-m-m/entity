/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractVersionId} using {@link String} as {@link #getId() primary key}. This is the most
 * generic type of {@link Id}. However {@link LongVersionId} and {@link UuidVersionId} will be more efficient.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class StringVersionId<E> extends AbstractVersionId<E, String> {

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
  public StringVersionId(Class<E> type, String id, Long version) {

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
   * @return the new {@link StringVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringVersionId<E> of(Class<E> type, String id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link StringVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringVersionId<E> of(Class<E> type, String id, Long version) {

    if (id == null) {
      return null;
    }
    return new StringVersionId<>(type, id, version);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory implements IdFactory<String, Long, StringVersionId<?>> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<StringVersionId<?>> getIdClass() {

      return (Class) StringVersionId.class;
    }

    @Override
    public <E> StringVersionId<E> unmarshall(Class<E> type, String id, String version) {

      return create(type, id, Long.valueOf(version));
    }

    @Override
    public <E> StringVersionId<E> create(Class<E> type, String id, Long version) {

      return of(type, id, version);
    }
  }

}
