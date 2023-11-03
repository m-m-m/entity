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
public final class LongVersionId<E> extends AbstractVersionId<E, Long> implements LongId<E, Long> {

  @SuppressWarnings("rawtypes")
  private static final LongVersionId EMPTY = new LongVersionId<>(null, null, null);

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param id the {@link #get() primary key}. See {@link #getIdAsLong()}.
   * @param revision the {@link #getRevision() revision}.
   */
  public LongVersionId(Class<E> type, Long id, Long revision) {

    super(type, revision);
    this.id = id;
  }

  @Override
  public Long get() {

    return this.id;
  }

  @Override
  public <T> LongVersionId<T> create(Class<T> newEntityType, Long newId, Long newRevision) {

    return new LongVersionId<>(newEntityType, newId, newRevision);
  }

  @Override
  public LongVersionId<E> withIdAndRevision(Long newId, Long newRevision) {

    if (Objects.equals(getRevision(), newRevision) && Objects.equals(get(), newId)) {
      return this;
    }
    return create(getEntityClass(), newId, newRevision);
  }

  @Override
  public LongVersionId<E> withRevision(Long newRevision) {

    if (Objects.equals(getRevision(), newRevision)) {
      return this;
    }
    return create(getEntityClass(), get(), newRevision);
  }

  @Override
  public LongVersionId<E> withoutRevision() {

    return withRevision(null);
  }

  @Override
  public <T> LongVersionId<T> withEntityType(Class<T> newEntityType) {

    return (LongVersionId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public LongVersionId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (LongVersionId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public LongVersionId<E> updateRevision() {

    Long newRevision = updateRevision(getRevision());
    return withRevision(newRevision);
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
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongVersionId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }

}
