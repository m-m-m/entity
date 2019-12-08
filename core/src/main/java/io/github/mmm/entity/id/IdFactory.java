/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Factory to #create
 *
 * @param <I> type of the {@link Id#getId() primary key}.
 * @param <V> type of the {@link Id#getVersion() version}.
 * @param <ID> type of {@link Id} implementation.
 *
 * @since 1.0.0
 */
public interface IdFactory<I, V extends Comparable<?>, ID extends AbstractId<?, I, V>> extends IdMapper {

  /**
   * @return the {@link Class} of the managed {@link Id} implementation.
   */
  Class<ID> getIdClass();

  /**
   * @param <E> type of the identified entity.
   * @param type the {@link Id#getType() entity type}.
   * @param id the {@link Id#getId() primary key}.
   * @param version the {@link Id#getVersion() version}. May be {@code null}.
   * @return the {@link Id} for the given values.
   */
  <E> AbstractId<E, I, V> create(Class<E> type, I id, V version);

}
