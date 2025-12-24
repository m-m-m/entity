/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/**
 * {@link EntityLink} but {@link EntityLink#isRemoveRevision() without revision}.
 *
 * @param <E> the generic type of the {@link Link#getEntity() linked entity}.
 *
 * @since 1.0.0
 */
final class EntityLinkWithoutRevision<E extends Entity> extends EntityLink<E> {

  /**
   * The constructor.
   *
   * @param entity the {@link Link#getEntity() linked entity}.
   */
  public EntityLinkWithoutRevision(E entity) {

    this(null, entity);
  }

  private EntityLinkWithoutRevision(Id<E> id, E entity) {

    super(id, entity);
  }

  @Override
  protected boolean isRemoveRevision() {

    return true;
  }

  @Override
  protected AbstractIdLink<E> withId(GenericId<E, ?, ?, ?> newId) {

    // should never happen...
    return new EntityLinkWithoutRevision<>(newId, getEntity());
  }

}
