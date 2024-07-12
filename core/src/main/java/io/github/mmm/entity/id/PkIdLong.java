/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Implementation of {@link PkId} using {@link Long} as type for the {@link #getPk() primary key}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class PkIdLong<E> extends PkId<E, Long, PkIdLong<E>> {

  @SuppressWarnings("rawtypes")
  private static final PkIdLong EMPTY = new PkIdLong<>(null, null);

  private final Long pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}. See {@link #getPkAsLong()}.
   */
  public PkIdLong(Class<E> type, Long pk) {

    super(type);
    this.pk = pk;
  }

  @Override
  public Long getPk() {

    return this.pk;
  }

  @Override
  protected PkIdLong<E> newId(Class<E> newEntityClass, Long newPk) {

    return new PkIdLong<>(newEntityClass, newPk);
  }

  @Override
  public Class<Long> getPkClass() {

    return Long.class;
  }

  /**
   * @return the {@link #getPk() primary key} as primitve {@code long} value.
   */
  public long getPkAsLong() {

    Long id = getPk();
    if (id == null) {
      return -1;
    }
    return id.longValue();
  }

  @Override
  public Long parsePk(String idString) {

    return Long.valueOf(idString);
  }

  @Override
  public String getMarshalPropertyId() {

    return PROPERTY_PK_LONG;
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> PkIdLong<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> PkIdLong<E> getEmpty(Class<E> entityType) {

    PkIdLong<E> empty = getEmpty();
    return empty.withEntityType(entityType);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @return the {@link PkIdLong}.
   */
  static <E> PkIdLong<E> of(Long pk) {

    return of(pk, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @param entityClass the {@link #getEntityClass() entity class}.
   * @return the {@link PkIdLong}.
   */
  public static <E> PkIdLong<E> of(Long pk, Class<E> entityClass) {

    if (pk == null) {
      return null;
    }
    return new PkIdLong<>(entityClass, pk);
  }

}
