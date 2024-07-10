/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * {@link GenericId} using {@link String} as {@link #getPk() primary key (ID)}. {@link String} is obviously the most
 * generic type of {@link #getPk() primary key}. However {@link LongId} or {@link UuidId} will be much more efficient.
 *
 * @param <E> type of the identified entity.
 * @param <V> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 */
public interface StringId<E, V extends Comparable<?>> extends GenericId<E, String, V> {

  @Override
  String getPk();

  @Override
  default Class<String> getPkClass() {

    return String.class;
  }

  @Override
  default String parsePk(String idString) {

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
  default StringId<E, V> withPkAndRevision(String newId, V newRevision) {

    return (StringId<E, V>) GenericId.super.withPkAndRevision(newId, newRevision);
  }

  @Override
  default StringId<E, V> withRevision(V newRevision) {

    return (StringId<E, V>) GenericId.super.withRevision(newRevision);
  }

  @Override
  default StringId<E, ?> withoutRevision() {

    return new StringRevisionlessId<>(getEntityClass(), getPk());
  }

  @Override
  default StringId<E, V> updateRevision() {

    return (StringId<E, V>) GenericId.super.updateRevision();
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @return the new {@link StringId}.
   */
  static <E> StringId<E, ?> of(String pk) {

    return of(pk, null);
  }

  /**
   * @param <E> type of the referenced entity.
   * @param pk the actual {@link #getPk() primary key}.
   * @param type the {@link #getEntityClass() entity type}.
   * @return the new {@link StringId}.
   */
  static <E> StringId<E, ?> of(String pk, Class<E> type) {

    if (pk == null) {
      return null;
    }
    return new StringRevisionlessId<>(type, pk);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getEntityClass() type}.
   * @param pk the {@link #getPk() primary key}.
   * @param revision the optional {@link #getRevision() revision}.
   * @return the new {@link StringId}.
   */
  static <E> StringId<E, ?> of(String pk, Class<E> type, Object revision) {

    if (pk == null) {
      return null;
    }
    if (revision == null) {
      return new StringRevisionlessId<>(type, pk);
    } else if (revision instanceof Long l) {
      return new StringVersionId<>(type, pk, l);
    } else if (revision instanceof Instant i) {
      return new StringInstantId<>(type, pk, i);
    }
    throw new IllegalStateException("Unsupported revision type: " + revision.getClass().getName());
  }
}
