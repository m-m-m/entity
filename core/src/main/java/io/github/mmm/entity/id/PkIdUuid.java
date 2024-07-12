/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.UUID;

import io.github.mmm.base.uuid.UuidParser;

/**
 * Implementation of {@link PkId} using {@link UUID} as type for the {@link #getPk() primary key}.
 *
 * @param <E> type of the identified entity.
 * @since 1.0.0
 */
public final class PkIdUuid<E> extends PkId<E, UUID, PkIdUuid<E>> {

  @SuppressWarnings("rawtypes")
  private static final PkIdUuid EMPTY = new PkIdUuid<>(null, null);

  private final UUID pk;

  /**
   * The constructor.
   *
   * @param entityClass the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   */
  public PkIdUuid(Class<E> entityClass, UUID pk) {

    super(entityClass);
    this.pk = pk;
  }

  @Override
  public UUID getPk() {

    return this.pk;
  }

  @Override
  protected PkIdUuid<E> newId(Class<E> newEntityClass, UUID newPk) {

    return new PkIdUuid<>(newEntityClass, newPk);
  }

  @Override
  public Class<UUID> getPkClass() {

    return UUID.class;
  }

  @Override
  public UUID parsePk(String idString) {

    if (idString == null) {
      return null;
    }
    UUID uuid = UuidParser.get().parse(idString);
    if (uuid == null) {
      throw new IllegalArgumentException(idString);
    }
    return uuid;
  }

  @Override
  public String getMarshalPropertyId() {

    return PROPERTY_PK_UUID;
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> PkIdUuid<E> getEmpty() {

    return EMPTY;
  }

  /**
   * @param <E> type of the identified entity.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the {@link #isEmpty() empty} template of this class.
   */
  public static <E> PkIdUuid<E> getEmpty(Class<E> entityType) {

    PkIdUuid<E> empty = getEmpty();
    return empty.withEntityType(entityType);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @return the new {@link PkIdUuid}.
   */
  public static <E> PkIdUuid<E> of(UUID pk) {

    return of(pk, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @param type the {@link #getEntityClass() entity type}.
   * @return the new {@link PkIdUuid}.
   */
  public static <E> PkIdUuid<E> of(UUID pk, Class<E> type) {

    if (pk == null) {
      return null;
    }
    return new PkIdUuid<>(type, pk);
  }

}
