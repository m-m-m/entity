/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/**
 * {@link IdLink} but {@link IdLink#isRemoveRevision() without revision}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} entity.
 *
 * @since 1.0.0
 */
final class IdLinkWithoutRevision<E> extends IdLink<E> {

  /**
   * The constructor.
   *
   * @param id - see {@link Link#getId()}.
   * @param resolver the resolver {@link Function}.
   */
  protected IdLinkWithoutRevision(Id<E> id, Function<Id<E>, E> resolver) {

    this(id, null, resolver);
  }

  private IdLinkWithoutRevision(Id<E> id, E entity, Function<Id<E>, E> resolver) {

    super(id, entity, resolver);
  }

  @Override
  protected boolean isRemoveRevision() {

    return true;
  }

  @Override
  protected IdLinkWithoutRevision<E> withId(GenericId<E, ?, ?> newId) {

    return new IdLinkWithoutRevision<>(newId, super.getTarget(), this.resolver);
  }

}
