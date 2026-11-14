/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/// Implementation of [Link] that carries the [Id] of an entity. It optionally allows lazy loading on the
/// first call of [#getEntity()] via an externally given resolver [Function]. This approach is easier, less
/// invasive and more lightweight as using proxy objects for entities.
///
/// @param <E> the generic type of the [linked entity][#getEntity()].
///
/// @since 1.0.0
public class IdLink<E> extends AbstractIdLink<E> {

  transient Function<Id<E>, E> resolver;

  /// The constructor.
  ///
  /// @param id - see [#getId()].
  /// @param resolver the [Function] to [resolve][#isResolved()] the [entity][#getEntity()].
  protected IdLink(Id<E> id, Function<Id<E>, E> resolver) {

    this(id, null, resolver);
  }

  IdLink(Id<E> id, E entity, Function<Id<E>, E> resolver) {

    super(id, entity);
    this.resolver = resolver;
  }

  @Override
  public E getEntity() {

    E entity = super.getEntity();
    if ((entity == null) && (this.resolver != null)) {
      entity = updateTarget(this.resolver);
      if (entity != null) {
        this.resolver = null;
      }
    }
    return entity;
  }

  @Override
  protected IdLink<E> withId(GenericId<E, ?, ?, ?> newId) {

    return new IdLink<>(newId, super.getEntity(), this.resolver);
  }

  /// @param resolver the new [Function] to [resolve][#isResolved()] the [target][#getEntity()].
  public void setResolver(Function<Id<E>, E> resolver) {

    this.resolver = resolver;
  }

  /// @param <E> type of the [linked][#getEntity()] [io.github.mmm.entity.Entity].
  /// @param id the [id][#getId()].
  /// @param resolver the [Function] to [resolve][Function#apply(Object)] the [Id].
  /// @return the [IdLink] instance.
  public static <E> IdLink<E> of(Id<E> id, Function<Id<E>, E> resolver) {

    return new IdLink<>(id, resolver);
  }

}
