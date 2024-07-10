/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

import io.github.mmm.base.uuid.UuidParser;

/**
 * {@link Id} using {@link UUID} as {@link #getPk() primary key (ID)}. (e.g. apache cassandra supports this natively).
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 */
public interface UuidId<E, V extends Comparable<?>> extends GenericId<E, UUID, V> {

  @Override
  UUID getPk();

  @Override
  default Class<UUID> getPkClass() {

    return UUID.class;
  }

  @Override
  default UUID parsePk(String idString) {

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
  default String getMarshalPropertyId() {

    return PROPERTY_UUID;
  }

  @Override
  default <T> UuidId<T, V> withEntityType(Class<T> newEntityType) {

    return (UuidId<T, V>) GenericId.super.withEntityType(newEntityType);
  }

  @Override
  default UuidId<E, V> withEntityTypeGeneric(Class<?> newEntityType) {

    return (UuidId<E, V>) GenericId.super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  default UuidId<E, V> withPkAndRevision(UUID newId, V newRevision) {

    return (UuidId<E, V>) GenericId.super.withPkAndRevision(newId, newRevision);
  }

  @Override
  default UuidId<E, V> withRevision(V newRevision) {

    return (UuidId<E, V>) GenericId.super.withRevision(newRevision);
  }

  @Override
  default UuidId<E, ?> withoutRevision() {

    return new UuidRevisionlessId<>(getEntityClass(), getPk());
  }

  @Override
  default UuidId<E, V> updateRevision() {

    return (UuidId<E, V>) GenericId.super.updateRevision();
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID pk) {

    return of(pk, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @param type the {@link #getEntityClass() entity type}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID pk, Class<E> type) {

    if (pk == null) {
      return null;
    }
    return new UuidRevisionlessId<>(type, pk);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   * @param revision the optional {@link #getRevision() revision}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID pk, Class<E> type, Object revision) {

    if (pk == null) {
      return null;
    }
    if (revision == null) {
      return new UuidRevisionlessId<>(type, pk);
    } else if (revision instanceof Long l) {
      return new UuidVersionId<>(type, pk, l);
    } else if (revision instanceof Instant i) {
      return new UuidInstantId<>(type, pk, i);
    }
    throw new IllegalStateException("Unsupported revision type: " + revision.getClass().getName());
  }

}
