/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

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
   * @param version the {@link #getVersion() version}.
   */
  public StringVersionId(Class<E> type, String id, Long version) {

    super(type, version);
    this.id = id;
  }

  @Override
  public String get() {

    return this.id;
  }

  @Override
  public <T> StringVersionId<T> create(Class<T> newEntityType, String newId, Long newVersion) {

    return new StringVersionId<>(newEntityType, newId, newVersion);
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
