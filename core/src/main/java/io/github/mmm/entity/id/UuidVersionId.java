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

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param id the {@link #get() primary key}.
   * @param revision the {@link #getRevision() revision}.
   */
  public UuidVersionId(Class<E> type, UUID id, Long revision) {

    super(type, revision);
    this.id = id;
  }

  @Override
  public UUID get() {

    return this.id;
  }

  @Override
  public <T> UuidVersionId<T> create(Class<T> newEntityType, UUID newId, Long newRevision) {

    return new UuidVersionId<>(newEntityType, newId, newRevision);
  }

  @Override
  public UuidVersionId<E> withIdAndRevision(UUID newId, Long newRevision) {

    if (Objects.equals(getRevision(), newRevision) && Objects.equals(get(), newId)) {
      return this;
    }
    return create(getEntityClass(), newId, newRevision);
  }

  @Override
  public UuidVersionId<E> withRevision(Long newRevision) {

    if (Objects.equals(getRevision(), newRevision)) {
      return this;
    }
    return create(getEntityClass(), get(), newRevision);
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

    Long newRevision = updateRevision(getRevision());
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
