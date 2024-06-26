/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.Objects;
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
public class IdLink<E> extends AbstractLink<E> {

  private final GenericId<E, ?, ?> id;

  private transient Function<Id<E>, E> resolver;

  private E entity;

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param resolver the {@link Function} to {@link #isResolved() resolve} the {@link #getTarget() link target} (the
   *        entity).
   */
  @SuppressWarnings({ "rawtypes" })
  protected IdLink(Id<E> id, Function<Id<E>, E> resolver) {

    super();
    Objects.requireNonNull(id, "id");
    if (id.get() == null) {
      throw new IllegalArgumentException("Cannot create link for empty ID - primary key must be present!");
    }
    this.id = ((GenericId) id).withoutRevision();
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

  private synchronized E resolve() {

    if ((this.entity == null) && (this.resolver != null)) {
      this.entity = this.resolver.apply(this.id);
      this.resolver = null;
    }
    return this.entity;
  }

  /**
   * @param type the new value of {@link Id#getEntityClass()}. Exact type should actually be {@link Class}{@literal <E>}
   *        but this prevents simple usage.
   * @return a copy of this {@link Link} with the given {@link Id#getEntityClass() type} or this {@link Link} itself if
   *         already satisfying.
   * @see GenericId#withEntityType(Class)
   */
  public IdLink<E> withType(Class<?> type) {

    Id<E> newId = this.id.withEntityTypeGeneric(type);
    if (newId == this.id) {
      return this;
    }
    IdLink<E> newLink = new IdLink<>(newId, this.resolver);
    newLink.entity = this.entity;
    return newLink;
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
