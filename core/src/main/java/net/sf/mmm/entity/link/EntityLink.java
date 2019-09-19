/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.link;

import net.sf.mmm.entity.Entity;
import net.sf.mmm.entity.id.Id;

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

    return Id.of(this.entity);
  }

  @Override
  public E getTarget() {

    return this.entity;
  }

}
