/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractVersionId} as {@link StringId}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class StringVersionId<E> extends AbstractVersionId<E, String> implements StringId<E, Long> {

  @SuppressWarnings("rawtypes")
  private static final StringVersionId EMPTY = new StringVersionId<>(null, null, null);

  private final String pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   * @param revision the {@link #getRevision() revision}.
   */
  public StringVersionId(Class<E> type, String pk, Long revision) {

    super(type, revision);
    this.pk = pk;
  }

  @Override
  public String getPk() {

    return this.pk;
  }

  @Override
  public <T> StringVersionId<T> create(Class<T> newEntityType, String newPk, Long newRevision) {

    return new StringVersionId<>(newEntityType, newPk, newRevision);
  }

  @Override
  public StringVersionId<E> withPk(String newPk) {

    if (Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, this.revision);
  }

  @Override
  public StringVersionId<E> withPkAndRevision(String newPk, Long newRevision) {

    if (Objects.equals(this.revision, newRevision) && Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, newRevision);
  }

  @Override
  public StringVersionId<E> withRevision(Long newRevision) {

    if (Objects.equals(this.revision, newRevision)) {
      return this;
    }
    return create(this.entityClass, this.pk, newRevision);
  }

  @Override
  public <T> StringVersionId<T> withEntityType(Class<T> newEntityType) {

    return (StringVersionId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public StringVersionId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (StringVersionId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public StringVersionId<E> updateRevision() {

    Long newRevision = updateRevision(this.revision);
    return withRevision(newRevision);
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> StringVersionId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> StringVersionId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }
}
