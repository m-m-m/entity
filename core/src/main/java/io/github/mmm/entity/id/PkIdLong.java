/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/// Implementation of [PkId] using [Long] as type for the [primary key][#getPk()].
///
/// @param <E> type of the identified entity.
/// @since 1.0.0
public final class PkIdLong<E> extends PkId<E, Long, PkIdLong<E>> {

  @SuppressWarnings("rawtypes")
  private static final PkIdLong EMPTY = new PkIdLong<>(null, null);

  private final Long pk;

  /// The constructor.
  ///
  /// @param type the [type][#getEntityClass()].
  /// @param pk the [primary key][#getPk()]. See [#getPkAsLong()].
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

  /// @return the [primary key][#getPk()] as primitive `long` value.
  public long getPkAsLong() {

    if (this.pk == null) {
      return -1;
    }
    return this.pk.longValue();
  }

  @Override
  public boolean isTransient() {

    if (this.pk != null) {
      return false;
    }
    return super.isTransient();
  }

  @Override
  public Long parsePk(String idString) {

    return Long.valueOf(idString);
  }

  @Override
  public String getMarshalPropertyId() {

    return PROPERTY_PK_LONG;
  }

  /// @param <E> type of the identified entity.
  /// @return the [empty][#isEmpty()] template of this class.
  public static <E> PkIdLong<E> getEmpty() {

    return EMPTY;
  }

  /// @param <E> type of the identified entity.
  /// @param entityType the [entity type][#getEntityClass()].
  /// @return the [empty][#isEmpty()] template of this class.
  public static <E> PkIdLong<E> getEmpty(Class<E> entityType) {

    PkIdLong<E> empty = getEmpty();
    return empty.withEntityType(entityType);
  }

  /// @param <E> type of the referenced entity.
  /// @param pk the actual [primary key][#getPk()].
  /// @return the [PkIdLong].
  static <E> PkIdLong<E> of(Long pk) {

    return of(pk, null);
  }

  /// @param <E> type of the referenced entity.
  /// @param pk the actual [primary key][#getPk()].
  /// @param entityClass the [entity class][#getEntityClass()].
  /// @return the [PkIdLong].
  public static <E> PkIdLong<E> of(Long pk, Class<E> entityClass) {

    if (pk == null) {
      return null;
    }
    return new PkIdLong<>(entityClass, pk);
  }

}
