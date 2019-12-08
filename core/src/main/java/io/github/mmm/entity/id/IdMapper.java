/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Interface for mapper that can marshall and unmarshall {@link Id}s.
 *
 * @see IdMarshaller
 * @see IdFactory
 * @since 1.0.0
 */
public interface IdMapper {

  /**
   * @param <E> type of the identified entity.
   * @param type the {@link Id#getType() entity type}.
   * @param id the {@link Id#getIdAsString() primary key as string}.
   * @param version the {@link Id#getVersionAsString() version as string}. May be {@code null}.
   * @return the parsed {@link Id}.
   */
  <E> Id<E> unmarshall(Class<E> type, String id, String version);

}
