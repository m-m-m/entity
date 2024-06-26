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

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param id the {@link #get() primary key}.
   */
  public UuidRevisionlessId(Class<E> type, UUID id) {

    super(type);
    this.id = id;
  }

  @Override
  public UUID get() {

    return this.id;
  }

  @Override
  public <T> UuidRevisionlessId<T> create(Class<T> newEntityType, UUID newId, NoRevision newRevision) {

    return new UuidRevisionlessId<>(newEntityType, newId);
  }

  @Override
  public UuidRevisionlessId<E> withIdAndRevision(UUID newId, NoRevision newRevision) {

    if (Objects.equals(get(), newId)) {
      return this;
    }
    return create(getEntityClass(), newId, newRevision);
  }

  @Override
  public UuidRevisionlessId<E> withRevision(NoRevision newRevision) {

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
