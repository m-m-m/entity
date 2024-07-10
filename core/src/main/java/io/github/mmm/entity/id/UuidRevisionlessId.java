/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AbstractRevisionlessId} as {@link UuidId}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class UuidRevisionlessId<E> extends AbstractRevisionlessId<E, UUID> implements UuidId<E, NoRevision> {

  @SuppressWarnings("rawtypes")
  private static final UuidRevisionlessId EMPTY = new UuidRevisionlessId<>(null, null);

  private final UUID pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   */
  public UuidRevisionlessId(Class<E> type, UUID pk) {

    super(type);
    this.pk = pk;
  }

  @Override
  public UUID getPk() {

    return this.pk;
  }

  @Override
  public <T> UuidRevisionlessId<T> create(Class<T> newEntityType, UUID newPk, NoRevision newRevision) {

    return new UuidRevisionlessId<>(newEntityType, newPk);
  }

  @Override
  public UuidRevisionlessId<E> withPk(UUID newPk) {

    if (Objects.equals(getPk(), newPk)) {
      return this;
    }
    return create(getEntityClass(), newPk, null);
  }

  @Override
  public UuidRevisionlessId<E> withPkAndRevision(UUID newPk, NoRevision newRevision) {

    assert (newRevision == null);
    return withPk(newPk);
  }

  @Override
  public UuidRevisionlessId<E> withRevision(NoRevision newRevision) {

    assert (newRevision == null);
    return this;
  }

  @Override
  public UuidRevisionlessId<E> withoutRevision() {

    return this;
  }

  @Override
  public <T> UuidRevisionlessId<T> withEntityType(Class<T> newEntityType) {

    return (UuidRevisionlessId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public UuidRevisionlessId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (UuidRevisionlessId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public UuidRevisionlessId<E> updateRevision() {

    return this;
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> UuidRevisionlessId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> UuidRevisionlessId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }
}
