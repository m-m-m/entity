/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * Implementation of {@link AbstractInstantId} as {@link StringId}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public final class StringInstantId<E> extends AbstractInstantId<E, String> implements StringId<E, Instant> {

  @SuppressWarnings("rawtypes")
  private static final StringInstantId EMPTY = new StringInstantId<>(null, null, null);

  private final String id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityType() type}.
   * @param id the {@link #get() primary key}.
   * @param revision the {@link #getRevision() revision}.
   */
  public StringInstantId(Class<E> type, String id, Instant revision) {

    super(type, revision);
    this.id = id;
  }

  @Override
  public String get() {

    return this.id;
  }

  @Override
  public <T> StringInstantId<T> create(Class<T> newEntityType, String newId, Instant newRevision) {

    return new StringInstantId<>(newEntityType, newId, newRevision);
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> StringInstantId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityType() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  @SuppressWarnings("unchecked")
  public static <E> StringInstantId<E> getEmpty(Class<E> entityType) {

    return (StringInstantId<E>) getEmpty().withEntityType(entityType);
  }
}
