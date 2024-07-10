/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.Objects;

/**
 * Implementation of {@link AbstractInstantId} as {@link LongId}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class LongInstantId<E> extends AbstractInstantId<E, Long> implements LongId<E, Instant> {

  @SuppressWarnings("rawtypes")
  private static final LongInstantId EMPTY = new LongInstantId<>(null, null, null);

  private final Long pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}. See {@link #getPkAsLong()}.
   * @param revision the {@link #getRevision() revision}.
   */
  public LongInstantId(Class<E> type, Long pk, Instant revision) {

    super(type, revision);
    this.pk = pk;
  }

  @Override
  public Long getPk() {

    return this.pk;
  }

  @Override
  public <T> LongInstantId<T> create(Class<T> newEntityType, Long newId, Instant newRevision) {

    return new LongInstantId<>(newEntityType, newId, newRevision);
  }

  @Override
  public LongInstantId<E> withPk(Long newPk) {

    if (Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, this.revision);
  }

  @Override
  public LongInstantId<E> withPkAndRevision(Long newPk, Instant newRevision) {

    if (Objects.equals(this.revision, newRevision) && Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, newRevision);
  }

  @Override
  public LongInstantId<E> withRevision(Instant newRevision) {

    if (Objects.equals(this.revision, newRevision)) {
      return this;
    }
    return create(this.entityClass, this.pk, newRevision);
  }

  @Override
  public <T> LongInstantId<T> withEntityType(Class<T> newEntityType) {

    return (LongInstantId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public LongInstantId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (LongInstantId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public LongInstantId<E> updateRevision() {

    Instant newRevision = updateRevision(this.revision);
    return withRevision(newRevision);
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongInstantId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongInstantId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }

}
