/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * {@link GenericId} using {@link String} as {@link #get() primary key (ID)}. {@link String} is obviously the most
 * generic type of {@link #get() primary key}. However {@link LongId} or {@link UuidId} will be much more efficient.
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 */
public interface StringId<E, V extends Comparable<?>> extends GenericId<E, String, V> {

  @Override
  String get();

  @Override
  default Class<String> getType() {

    return String.class;
  }

  @Override
  default String parseId(String idString) {

    return idString;
  }

  @Override
  default String getMarshalPropertyId() {

    return PROPERTY_STRING_ID;
  }

  @Override
  default <T> StringId<T, V> withEntityType(Class<T> newEntityType) {

    return (StringId<T, V>) GenericId.super.withEntityType(newEntityType);
  }

  @Override
  default StringId<E, V> withEntityTypeGeneric(Class<?> newEntityType) {

    return (StringId<E, V>) GenericId.super.withEntityTypeGeneric(newEntityType);
  }

  @Override
  default StringId<E, V> withIdAndRevision(String newId, V newRevision) {

    return (StringId<E, V>) GenericId.super.withIdAndRevision(newId, newRevision);
  }

  @Override
  default StringId<E, V> withRevision(V newRevision) {

    return (StringId<E, V>) GenericId.super.withRevision(newRevision);
  }

  @SuppressWarnings("unchecked")
  @Override
  default StringId<E, V> withoutRevision() {

    return (StringId<E, V>) GenericId.super.withoutRevision();
  }

  @Override
  default StringId<E, V> updateRevision() {

    return (StringId<E, V>) GenericId.super.updateRevision();
  }

  /**
   * @param <E> type of the referenced entity.
   * @param id the actual {@link #get() primary key}.
   * @return the new {@link StringId}.
   */
  static <E> StringId<E, ?> of(String id) {

    return of(id, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param id the actual {@link #get() primary key}.
   * @param entityType the {@link #getEntityClass() entity type}.
   * @return the new {@link StringId}.
   */
  static <E> StringId<E, ?> of(String id, Class<E> entityType) {

    return of(id, entityType, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getEntityClass() type}.
   * @param id the {@link #get() primary key}.
   * @param revision the optional {@link #getRevision() revision}.
   * @return the new {@link StringId}.
   */
  static <E> StringId<E, ?> of(String id, Class<E> type, Object revision) {

    if (id == null) {
      return null;
    }
    if ((revision == null) || (revision instanceof Long)) {
      return new StringVersionId<>(type, id, (Long) revision);
    } else if (revision instanceof Instant) {
      return new StringInstantId<>(type, id, (Instant) revision);
    }
    throw new IllegalStateException("Unsupported revision type: " + revision.getClass().getName());
  }
}
