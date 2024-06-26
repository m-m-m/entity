/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/**
 * Implementation of {@link AbstractLink} based on an already resolved {@link Entity}.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} {@link Entity}.
 *
 * @since 1.0.0
 */
public class EntityLink<E extends Entity> extends AbstractIdLink<E> {

  /**
   * The constructor.
   *
   * @param target the {@link #getTarget() target entity}.
   */
  protected EntityLink(E target) {

    this(null, target);
  }

  EntityLink(Id<E> id, E target) {

    super(id, target);
  }

  @Override
  protected AbstractIdLink<E> withId(GenericId<E, ?, ?> newId) {

    // should never happen...
    return new EntityLink<>(newId, getTarget());
  }

}
