/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AbstractInstantId} as {@link UuidId}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class UuidInstantId<E> extends AbstractInstantId<E, UUID> implements UuidId<E, Instant> {

  @SuppressWarnings("rawtypes")
  private static final UuidInstantId EMPTY = new UuidInstantId<>(null, null, null);

  private final UUID pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   * @param revision the {@link #getRevision() revision}.
   */
  public UuidInstantId(Class<E> type, UUID pk, Instant revision) {

    super(type, revision);
    this.pk = pk;
  }

  @Override
  public UUID getPk() {

    return this.pk;
  }

  @Override
  public <T> UuidInstantId<T> create(Class<T> newEntityType, UUID newPk, Instant newRevision) {

    return new UuidInstantId<>(newEntityType, newPk, newRevision);
  }

  @Override
  public UuidInstantId<E> withPk(UUID newPk) {

    if (Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(getEntityClass(), newPk, this.revision);
  }

  @Override
  public UuidInstantId<E> withPkAndRevision(UUID newPk, Instant newRevision) {

    if (Objects.equals(this.revision, newRevision) && Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, newRevision);
  }

  @Override
  public UuidInstantId<E> withRevision(Instant newRevision) {

    if (Objects.equals(this.revision, newRevision)) {
      return this;
    }
    return create(this.entityClass, this.pk, newRevision);
  }

  @Override
  public <T> UuidInstantId<T> withEntityType(Class<T> newEntityType) {

    return (UuidInstantId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public UuidInstantId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (UuidInstantId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public UuidInstantId<E> updateRevision() {

    Instant newRevision = updateRevision(this.revision);
    return withRevision(newRevision);
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> UuidInstantId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> UuidInstantId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }
}
