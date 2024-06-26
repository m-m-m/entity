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
public class EntityLink<E extends Entity> extends AbstractLink<E> {

  private final E entity;

  /**
   * The constructor.
   *
   * @param entity the {@link #getTarget() target entity}.
   */
  protected EntityLink(E entity) {

    super();
    this.entity = entity;
  }

  @Override
  public boolean isResolved() {

    return true;
  }

  @Override
  public Id<E> getId() {

    Id<E> id = Id.from(this.entity);
    if (id instanceof GenericId g) {
      id = g.withoutRevision();
    }
    return id;
  }

  @Override
  public E getTarget() {

    return this.entity;
  }

}
