/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * Implementation of {@link AbstractRevisionlessId} as {@link StringId}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public final class StringRevisionlessId<E> extends AbstractRevisionlessId<E, String>
    implements StringId<E, NoRevision> {

  @SuppressWarnings("rawtypes")
  private static final StringRevisionlessId EMPTY = new StringRevisionlessId<>(null, null);

  private final String pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   */
  public StringRevisionlessId(Class<E> type, String pk) {

    super(type);
    this.pk = pk;
  }

  @Override
  public String getPk() {

    return this.pk;
  }

  @Override
  public <T> StringRevisionlessId<T> create(Class<T> newEntityType, String newPk, NoRevision newRevision) {

    return new StringRevisionlessId<>(newEntityType, newPk);
  }

  @Override
  public StringRevisionlessId<E> withPk(String newPk) {

    if (Objects.equals(this.pk, newPk)) {
      return this;
    }
    return create(this.entityClass, newPk, null);
  }

  @Override
  public StringRevisionlessId<E> withPkAndRevision(String newPk, NoRevision newRevision) {

    assert (newRevision == null);
    return withPk(newPk);
  }

  @Override
  public StringRevisionlessId<E> withRevision(NoRevision newRevision) {

    assert (newRevision == null);
    return this;
  }

  @Override
  public StringRevisionlessId<E> withoutRevision() {

    return this;
  }

  @Override
  public <T> StringRevisionlessId<T> withEntityType(Class<T> newEntityType) {

    return (StringRevisionlessId<T>) super.withEntityType(newEntityType);
  }

  @Override
  public StringRevisionlessId<E> withEntityTypeGeneric(Class<?> newEntityType) {

    return (StringRevisionlessId<E>) super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  public StringRevisionlessId<E> updateRevision() {

    return this;
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> StringRevisionlessId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> StringRevisionlessId<E> getEmpty(Class<E> entityType) {

    return getEmpty().withEntityType(entityType);
  }
}
