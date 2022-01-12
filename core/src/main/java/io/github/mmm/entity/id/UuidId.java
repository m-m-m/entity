/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

import io.github.mmm.base.uuid.UuidParser;

/**
 * {@link Id} using {@link UUID} as {@link #get() primary key (ID)}. (e.g. apache cassandra supports this).
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getVersion() version}.
 * @since 1.0.0
 */
public interface UuidId<E, V extends Comparable<?>> extends GenericId<E, UUID, V> {

  @Override
  UUID get();

  @Override
  default Class<UUID> getIdType() {

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
   * @param entityType the {@link #getEntityType() entity type}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID id, Class<E> entityType) {

    return of(id, entityType, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getEntityType() type}.
   * @param id the {@link #get() primary key}.
   * @param version the optional {@link #getVersion() version}.
   * @return the new {@link UuidId}.
   */
  static <E> UuidId<E, ?> of(UUID id, Class<E> type, Object version) {

    if (id == null) {
      return null;
    }
    if ((version == null) || (version instanceof Long)) {
      return new UuidVersionId<>(type, id, (Long) version);
    } else if (version instanceof Instant) {
      return new UuidInstantId<>(type, id, (Instant) version);
    }
    throw new IllegalStateException("Unsupported version type: " + version.getClass().getName());
  }

}
