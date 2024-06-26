/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/**
 * Implementation of {@link Link} that carries the {@link Id} of an entity. It optionally allows lazy loading on the
 * first call of {@link #getTarget()} via an externally given resolver {@link Function}. This approach is easier, less
 * invasive and more lightweight as using proxy objects for entities.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} entity.
 *
 * @since 1.0.0
 */
public class IdLink<E> extends AbstractIdLink<E> {

  transient Function<Id<E>, E> resolver;

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param resolver the {@link Function} to {@link #isResolved() resolve} the {@link #getTarget() link target} (the
   *        entity).
   */
  protected IdLink(Id<E> id, Function<Id<E>, E> resolver) {

    this(id, null, resolver);
  }

  IdLink(Id<E> id, E target, Function<Id<E>, E> resolver) {

    super(id, target);
    this.resolver = resolver;
  }

  @Override
  public E getTarget() {

    E target = super.getTarget();
    if ((target == null) && (this.resolver != null)) {
      target = updateTarget(this.resolver);
      if (target != null) {
        this.resolver = null;
      }
    }
    return target;
  }

  @Override
  protected IdLink<E> withId(GenericId<E, ?, ?> newId) {

    return new IdLink<>(newId, super.getTarget(), this.resolver);
  }

  /**
   * @param resolver the new {@link Function} to {@link #isResolved() resolve} the {@link #getTarget() target}.
   */
  public void setResolver(Function<Id<E>, E> resolver) {

    this.resolver = resolver;
  }

  /**
   * @param <E> type of the {@link #getTarget() linked} {@link io.github.mmm.entity.Entity}.
   * @param id the {@link #getId() id}.
   * @param resolver the {@link Function} to {@link Function#apply(Object) resolve} the {@link Id}.
   * @return the {@link IdLink} instance.
   */
  public static <E> IdLink<E> of(Id<E> id, Function<Id<E>, E> resolver) {

    return new IdLink<>(id, resolver);
  }

}
