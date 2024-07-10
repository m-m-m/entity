/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AbstractVersionId} as {@link UuidId}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class UuidVersionId<E> extends AbstractVersionId<E, UUID> implements UuidId<E, Long> {

  @SuppressWarnings("rawtypes")
  private static final UuidVersionId EMPTY = new UuidVersionId<>(null, null, null);

  private final UUID pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   * @param revision the {@link #getRevision() revision}.
   */
  public UuidVersionId(Class<E> type, UUID pk, Long revision) {

    super(type, revision);
    this.pk = pk;
  }

  @Override
  public UUID getPk() {

    return this.pk;
  }

  @Override
  public <T> UuidVersionId<T> create(Class<T> newEntityType, UUID newPk, Long newRevision) {

    return new UuidVersionId<>(newEntityType, newPk, newRevision);
  }

  @Override
  public UuidVersionId<E> withPk(UUID newPk) {

    if (Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, this.revision);
  }

  @Override
  public UuidVersionId<E> withPkAndRevision(UUID newPk, Long newRevision) {

    if (Objects.equals(this.revision, newRevision) && Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, newRevision);
  }

  @Override
  public UuidVersionId<E> withRevision(Long newRevision) {

    if (Objects.equals(this.revision, newRevision)) {
      return this;
    }
    return create(this.entityClass, this.pk, newRevision);
  }

  @Override
  public <T> UuidVersionId<T> withEntityType(Class<T> newEntityType) {

    return (UuidVersionId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public UuidVersionId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (UuidVersionId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public UuidVersionId<E> updateRevision() {

    Long newRevision = updateRevision(this.revision);
    return withRevision(newRevision);
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> UuidVersionId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> UuidVersionId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }
}
