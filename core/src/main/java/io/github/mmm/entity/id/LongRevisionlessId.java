/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractRevisionlessId} using {@link Long} as {@link #getPk() primary key}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class LongRevisionlessId<E> extends AbstractRevisionlessId<E, Long> implements LongId<E, NoRevision> {

  @SuppressWarnings("rawtypes")
  private static final LongRevisionlessId EMPTY = new LongRevisionlessId<>(null, null);

  private final Long pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}. See {@link #getPkAsLong()}.
   */
  public LongRevisionlessId(Class<E> type, Long pk) {

    super(type);
    this.pk = pk;
  }

  @Override
  public Long getPk() {

    return this.pk;
  }

  @Override
  public <T> LongRevisionlessId<T> create(Class<T> newEntityType, Long newId, NoRevision newRevision) {

    return new LongRevisionlessId<>(newEntityType, newId);
  }

  @Override
  public LongRevisionlessId<E> withPk(Long newPk) {

    if (Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, null);
  }

  @Override
  public LongRevisionlessId<E> withPkAndRevision(Long newPk, NoRevision newRevision) {

    assert (newRevision == null);
    return withPk(newPk);
  }

  @Override
  public LongRevisionlessId<E> withRevision(NoRevision newRevision) {

    assert (newRevision == null);
    return this;
  }

  @Override
  public LongRevisionlessId<E> withoutRevision() {

    return this;
  }

  @Override
  public <T> LongRevisionlessId<T> withEntityType(Class<T> newEntityType) {

    return (LongRevisionlessId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public LongRevisionlessId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (LongRevisionlessId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public LongRevisionlessId<E> updateRevision() {

    return this;
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongRevisionlessId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongRevisionlessId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }

}
