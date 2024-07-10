/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractVersionId} as {@link LongId}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public final class LongVersionId<E> extends AbstractVersionId<E, Long> implements LongId<E, Long> {

  @SuppressWarnings("rawtypes")
  private static final LongVersionId EMPTY = new LongVersionId<>(null, null, null);

  private final Long pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}. See {@link #getPkAsLong()}.
   * @param revision the {@link #getRevision() revision}.
   */
  public LongVersionId(Class<E> type, Long pk, Long revision) {

    super(type, revision);
    this.pk = pk;
  }

  @Override
  public Long getPk() {

    return this.pk;
  }

  @Override
  public <T> LongVersionId<T> create(Class<T> newEntityClass, Long newPk, Long newRevision) {

    return new LongVersionId<>(newEntityClass, newPk, newRevision);
  }

  @Override
  public LongVersionId<E> withPk(Long newPk) {

    if (Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, this.revision);
  }

  @Override
  public LongVersionId<E> withPkAndRevision(Long newPk, Long newRevision) {

    if (Objects.equals(this.revision, newRevision) && Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, newRevision);
  }

  @Override
  public LongVersionId<E> withRevision(Long newRevision) {

    if (Objects.equals(this.revision, newRevision)) {
      return this;
    }
    return create(this.entityClass, this.pk, newRevision);
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

    Long newRevision = updateRevision(this.revision);
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
   * @param type the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongVersionId<E> getEmpty(Class<E> type) {

    return getEmpty().withEntityType(type);
  }

  /**
   * @param <E> type of the identified entity.
   * @param id the {@link #getPk() primary key}.
   * @param revision the {@link #getRevision() revision}.
   * @param type the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongVersionId<E> of(Long id, Long revision, Class<E> type) {

    return new LongVersionId<>(type, id, revision);
  }

}
