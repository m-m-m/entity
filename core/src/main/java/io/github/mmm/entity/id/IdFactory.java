/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Factory to #create
 *
 * @param <I> type of the {@link Id#getId() primary key}.
 * @param <V> type of the {@link Id#getVersion() version}.
 *
 * @since 1.0.0
 */
public interface IdFactory<I, V extends Comparable<?>> {

  /**
   * @return the {@link Class} of the managed {@link Id} implementation. <b>ATTENTION:</b> If {@link Id#getVersion()
   *         version} is {@code null} a factory might created an {@link Id} that is not an
   *         {@link Class#isInstance(Object) instance} of the returned class.
   */
  Class<? extends Id<?>> getIdClass();

  /**
   * @return the {@link Class} of the managed {@link Id} interface.
   * @see LongId
   * @see StringId
   * @see UuidId
   */
  Class<? extends Id<?>> getIdInterface();

  /**
   * @return {@code true} if the {@link #getIdClass() id class} can carry a {@link Id#getVersion() version},
   *         {@code false} otherwise (see {@link AbstractLatestId}).
   */
  default boolean hasVersion() {

    return true;
  }

  /**
   * @param id the {@link Id} to test.
   * @return {@code true} if the given {@link Id} is accepted by this factory (could be created with this factory),
   *         {@code false} otherwise.
   */
  default boolean accept(Id<?> id) {

    if (id == null) {
      return true;
    } else if (getIdClass().isInstance(id)) {
      return true;
    } else if ((id.getVersion() == null) && getIdInterface().isInstance(id)) {
      return true;
    }
    return false;
  }

  /**
   * @param <E> type of the identified entity.
   * @param type the {@link Id#getType() entity type}.
   * @param id the {@link Id#getId() primary key}.
   * @param version the {@link Id#getVersion() version}. May be {@code null}.
   * @return the {@link Id} for the given values.
   */
  <E> Id<E> create(Class<E> type, I id, V version);

  /**
   * @param <E> type of the identified entity.
   * @param type the {@link Id#getType() entity type}.
   * @param id the {@link Id#getIdAsString() primary key as string}.
   * @param version the {@link Id#getVersionAsString() version as string}. May be {@code null}.
   * @return the parsed {@link Id}.
   */
  <E> Id<E> parse(Class<E> type, String id, String version);

  /**
   * @param factory the optional {@link IdFactory}.
   * @return the most specific {@link Class} that instances of {@link Id} will implement when created by the given
   *         {@link IdFactory}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  static Class<? extends Id<?>> getIdClass(IdFactory<?, ?> factory) {

    if (factory != null) {
      if (factory.hasVersion()) {
        return factory.getIdInterface();
      }
      return factory.getIdClass();
    }
    return (Class) Id.class;
  }

}
