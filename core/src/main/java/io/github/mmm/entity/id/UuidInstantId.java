/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
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

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityType() type}.
   * @param id the {@link #get() primary key}.
   * @param version the {@link #getVersion() version}.
   */
  public UuidInstantId(Class<E> type, UUID id, Instant version) {

    super(type, version);
    this.id = id;
  }

  @Override
  public UUID get() {

    return this.id;
  }

  @Override
  public <T> UuidInstantId<T> create(Class<T> newEntityType, UUID newId, Instant newVersion) {

    return new UuidInstantId<>(newEntityType, newId, newVersion);
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
   * @param entityType the {@link #getEntityType() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  @SuppressWarnings("unchecked")
  public static <E> UuidInstantId<E> getEmpty(Class<E> entityType) {

    return (UuidInstantId<E>) getEmpty().withEntityType(entityType);
  }
}
