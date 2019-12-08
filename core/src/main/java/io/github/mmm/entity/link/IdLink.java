/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.Objects;
import java.util.function.Function;

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
public class IdLink<E> extends AbstractLink<E> {

  private final Id<E> id;

  private transient Function<Id<E>, E> resolver;

  private E entity;

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param resolver the {@link Function} to {@link #isResolved() resolve} the {@link #getTarget() link target} (the
   *        entity).
   */
  protected IdLink(Id<E> id, Function<Id<E>, E> resolver) {

    super();
    Objects.requireNonNull(id, "id");
    this.id = id;
    this.resolver = resolver;
  }

  @Override
  public Id<E> getId() {

    return this.id;
  }

  @Override
  public boolean isResolved() {

    return (this.entity != null);
  }

  @Override
  public E getTarget() {

    if (this.entity == null) {
      return resolve();
    }
    return this.entity;
  }

  /**
   * @return
   */
  private synchronized E resolve() {

    if ((this.entity == null) && (this.resolver != null)) {
      this.entity = this.resolver.apply(this.id);
      this.resolver = null;
    }
    return this.entity;
  }

  /**
   * @param type the new value of {@link Id#getType()}. Exact type should actually be {@link Class}{@literal <E>} but
   *        this prevents simple usage.
   * @return a copy of this {@link Link} with the given {@link Id#getType() type} or this {@link Link} itself if already
   *         satisfying.
   * @see Id#withType(Class)
   */
  public IdLink<E> withType(Class<?> type) {

    Id<E> newId = this.id.withType(type);
    if (newId == this.id) {
      return this;
    }
    IdLink<E> newLink = new IdLink<>(newId, this.resolver);
    newLink.entity = this.entity;
    return newLink;
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
