/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Implementation of {@link AbstractVersionId} using {@link Long} as {@link #get() primary key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public final class LongVersionId<E> extends AbstractVersionId<E, Long> implements LongId<E, Long> {

  @SuppressWarnings("rawtypes")
  private static final LongVersionId EMPTY = new LongVersionId<>(null, null, null);

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityType() type}.
   * @param id the {@link #get() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  public LongVersionId(Class<E> type, Long id, Long version) {

    super(type, version);
    this.id = id;
  }

  @Override
  public Long get() {

    return this.id;
  }

  @Override
  public <T> GenericId<T, Long, Long> create(Class<T> newEntityType, Long newId, Long newVersion) {

    return new LongVersionId<>(newEntityType, newId, newVersion);
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongVersionId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityType() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  @SuppressWarnings("unchecked")
  public static <E> LongVersionId<E> getEmpty(Class<E> entityType) {

    return (LongVersionId<E>) getEmpty().withEntityType(entityType);
  }

}
