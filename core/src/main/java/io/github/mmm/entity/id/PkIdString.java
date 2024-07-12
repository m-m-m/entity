/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Implementation of {@link PkId} using {@link #getPk() primary key}s of type {@link String}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public final class PkIdString<E> extends PkId<E, String, PkIdString<E>> {

  @SuppressWarnings("rawtypes")
  private static final PkIdString EMPTY = new PkIdString<>(null, null);

  private final String pk;

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   */
  public PkIdString(Class<E> type, String pk) {

    super(type);
    this.pk = pk;
  }

  @Override
  public String getPk() {

    return this.pk;
  }

  @Override
  protected PkIdString<E> newId(Class<E> newEntityClass, String newPk) {

    return new PkIdString<>(newEntityClass, newPk);
  }

  @Override
  public Class<String> getPkClass() {

    return String.class;
  }

  @Override
  public String parsePk(String idString) {

    return idString;
  }

  @Override
  public String getMarshalPropertyId() {

    return PROPERTY_PK_STRING;
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> PkIdString<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> PkIdString<E> getEmpty(Class<E> entityType) {

    PkIdString<E> empty = getEmpty();
    return empty.withEntityType(entityType);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @return the new {@link PkIdString}.
   */
  static <E> PkIdString<E> of(String pk) {

    return of(pk, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @param entityClass the {@link #getEntityClass() entity class}.
   * @return the new {@link PkIdString}.
   */
  public static <E> PkIdString<E> of(String pk, Class<E> entityClass) {

    if (pk == null) {
      return null;
    }
    return new PkIdString<>(entityClass, pk);
  }

}
