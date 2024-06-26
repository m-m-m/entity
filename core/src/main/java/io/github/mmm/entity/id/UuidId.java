/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

import io.github.mmm.base.uuid.UuidParser;

/**
 * {@link Id} using {@link UUID} as {@link #get() primary key (ID)}. (e.g. apache cassandra supports this natively).
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 */
public interface UuidId<E, V extends Comparable<?>> extends GenericId<E, UUID, V> {

  @Override
  UUID get();

  @Override
  default Class<UUID> getType() {

    return UUID.class;
  }

  @Override
  default UUID parseId(String idString) {

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
  default UuidId<E, V> withIdAndRevision(UUID newId, V newRevision) {

    return (UuidId<E, V>) GenericId.super.withIdAndRevision(newId, newRevision);
  }

  @Override
  default UuidId<E, V> withRevision(V newRevision) {

    return (UuidId<E, V>) GenericId.super.withRevision(newRevision);
  }

  @Override
  default UuidId<E, ?> withoutRevision() {

    return new UuidRevisionlessId<>(getEntityClass(), get());
  }

  @Override
  default UuidId<E, V> updateRevision() {

    return (UuidId<E, V>) GenericId.super.updateRevision();
  }

  /**
   * @param <E> type of the referenced entity.
   * @param id the actual {@link #get() primary key}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID id) {

    return of(id, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param id the actual {@link #get() primary key}.
   * @param type the {@link #getEntityClass() entity type}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID id, Class<E> type) {

    if (id == null) {
      return null;
    }
    return new UuidRevisionlessId<>(type, id);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getEntityClass() type}.
   * @param id the {@link #get() primary key}.
   * @param revision the optional {@link #getRevision() revision}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID id, Class<E> type, Object revision) {

    if (id == null) {
      return null;
    }
    if (revision == null) {
      return new UuidRevisionlessId<>(type, id);
    } else if (revision instanceof Long l) {
      return new UuidVersionId<>(type, id, l);
    } else if (revision instanceof Instant i) {
      return new UuidInstantId<>(type, id, i);
    }
    throw new IllegalStateException("Unsupported revision type: " + revision.getClass().getName());
  }

}
