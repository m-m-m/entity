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

  private final String id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityType() type}.
   * @param id the {@link #get() primary key}.
   * @param revision the {@link #getRevision() revision}.
   */
  public StringVersionId(Class<E> type, String id, Long revision) {

    super(type, revision);
    this.id = id;
  }

  @Override
  public String get() {

    return this.id;
  }

  @Override
  public <T> StringVersionId<T> create(Class<T> newEntityType, String newId, Long newRevision) {

    return new StringVersionId<>(newEntityType, newId, newRevision);
  }

  @Override
  public StringVersionId<E> withIdAndRevision(String newId, Long newRevision) {

    if (Objects.equals(getRevision(), newRevision) && Objects.equals(get(), newId)) {
      return this;
    }
    return create(getEntityType(), newId, newRevision);
  }

  @Override
  public StringVersionId<E> withRevision(Long newRevision) {

    if (Objects.equals(getRevision(), newRevision)) {
      return this;
    }
    return create(getEntityType(), get(), newRevision);
  }

  @Override
  public StringVersionId<E> withoutRevision() {

    return withRevision(null);
  }

  @Override
  public StringVersionId<E> withEntityType(Class<?> newEntityType) {

    return (StringVersionId<E>) super.withEntityType(newEntityType);
  }

  @Override
  public StringVersionId<E> updateRevision() {

    Long newRevision = updateRevision(getRevision());
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
   * @param entityType the {@link #getEntityType() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  @SuppressWarnings("unchecked")
  public static <E> StringVersionId<E> getEmpty(Class<E> entityType) {

    return (StringVersionId<E>) getEmpty().withEntityType(entityType);
  }
}
