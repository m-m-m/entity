/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/// [IdLink] but [without revision][IdLink#isRemoveRevision()].
///
/// @param <E> the generic type of the [linked entity][Link#getEntity()].
///
/// @since 1.0.0
final class IdLinkWithoutRevision<E> extends IdLink<E> {

  /// The constructor.
  ///
  /// @param id - see [Link#getId()].
  /// @param resolver the resolver [Function].
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
  protected IdLinkWithoutRevision<E> withId(GenericId<E, ?, ?, ?> newId) {

    return new IdLinkWithoutRevision<>(newId, super.getEntity(), this.resolver);
  }

}
