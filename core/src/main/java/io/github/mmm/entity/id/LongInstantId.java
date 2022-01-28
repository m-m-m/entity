/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * Implementation of {@link AbstractInstantId} using {@link Long} as {@link #get() primary key}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class LongInstantId<E> extends AbstractInstantId<E, Long> implements LongId<E, Instant> {

  @SuppressWarnings("rawtypes")
  private static final LongInstantId EMPTY = new LongInstantId<>(null, null, null);

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityType() type}.
   * @param id the {@link #get() primary key}. See {@link #getIdAsLong()}.
   * @param revision the {@link #getRevision() revision}.
   */
  public LongInstantId(Class<E> type, Long id, Instant revision) {

    super(type, revision);
    this.id = id;
  }

  @Override
  public Long get() {

    return this.id;
  }

  @Override
  public <T> GenericId<T, Long, Instant> create(Class<T> newEntityType, Long newId, Instant newRevision) {

    return new LongInstantId<>(newEntityType, newId, newRevision);
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> LongInstantId<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityType() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  @SuppressWarnings("unchecked")
  public static <E> LongInstantId<E> getEmpty(Class<E> entityType) {

    return (LongInstantId<E>) getEmpty().withEntityType(entityType);
  }

}
