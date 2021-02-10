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
public class StringLatestId<E> extends AbstractLatestId<E, String> implements StringId<E> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final String id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   */
  public StringLatestId(Class<E> type, String id) {

    super(type);
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
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   * @return the new {@link StringLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringLatestId<E> of(Class<E> type, String id) {

    if (id == null) {
      return null;
    }
    return new StringLatestId<>(type, id);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory extends StringIdFactory<LatestVersion> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<? extends Id<?>> getIdClass() {

      return (Class) StringLatestId.class;
    }

    @Override
    public boolean hasVersion() {

      return false;
    }

    @Override
    public <E> StringLatestId<E> parse(Class<E> type, String id, String version) {

      assert (version == null);
      return create(type, id, null);
    }

    @Override
    public <E> StringLatestId<E> create(Class<E> type, String id, LatestVersion version) {

      assert (version == null);
      return of(type, id);
    }
  }

}
