/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/**
 * Extends {@link AbstractLink} with the {@link Id} property.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} entity.
 *
 * @since 1.0.0
 */
public abstract class AbstractIdLink<E> extends AbstractLink<E> {

  private /* final */ GenericId<E, ?, ?> id;

  /** @see #getTarget() */
  private E target;

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param target - see {@link #getTarget()}.
   */
  @SuppressWarnings("unchecked")
  protected AbstractIdLink(Id<E> id, E target) {

    super();
    GenericId<E, ?, ?> gid = (GenericId<E, ?, ?>) id;
    if (target == null) {
      if (gid == null) {
        throw new IllegalArgumentException("Cannot create link with neither ID nor target entity!");
      }
      if (gid.getPk() == null) {
        throw new IllegalArgumentException("Cannot create link for empty ID - primary key must be present!");
      }
    } else if (gid == null) {
      gid = (GenericId<E, ?, ?>) Id.from((Entity) this.target);
    }
    if ((gid != null) && isRemoveRevision()) {
      gid = gid.withoutRevision();
    }
    this.id = (GenericId<E, ?, ?>) id;
    this.target = target;
  }

  @Override
  public Id<E> getId() {

    if (this.id == null) {
      return updateId(); // can only happen if link was created from transient entity that was later persisted.
    }
    return this.id;
  }

  @SuppressWarnings("unchecked")
  private synchronized Id<E> updateId() {

    if (this.id == null) {
      GenericId<E, ?, ?> gid = (GenericId<E, ?, ?>) Id.from((Entity) this.target);
      if ((gid != null) && isRemoveRevision()) {
        gid = gid.withoutRevision();
      }
      this.id = gid;
    }
    return this.id;
  }

  /**
   * @return {@code true} if the {@link Id#getRevision() revision} should be {@link GenericId#withoutRevision() removed}
   *         from the {@link Id}, {@code false} otherwise.
   */
  protected boolean isRemoveRevision() {

    return false;
  }

  @Override
  public boolean isResolved() {

    return (this.target != null);
  }

  @Override
  public E getTarget() {

    return this.target;
  }

  /**
   * @param resolver the resolver {@link Function}.
   * @return the {@link #getTarget() target entity}.
   */
  protected synchronized E updateTarget(Function<Id<E>, E> resolver) {

    if ((this.target == null) && (resolver != null)) {
      this.target = resolver.apply(getId());
    }
    return this.target;

  }

  /**
   * @param type the new value of {@link Id#getEntityClass()}. Exact type should actually be {@link Class}{@literal <E>}
   *        but this prevents simple usage.
   * @return a copy of this {@link Link} with the given {@link Id#getEntityClass() type} or this {@link Link} itself if
   *         already satisfying.
   * @see GenericId#withEntityType(Class)
   */
  public AbstractIdLink<E> withType(Class<?> type) {

    GenericId<E, ?, ?> newId = this.id.withEntityTypeGeneric(type);
    if (newId == this.id) {
      return this;
    }
    return withId(newId);
  }

  /**
   * @param newId the new {@link #getId()}.
   * @return a copy of this link with the given {@link Id}.
   */
  protected abstract AbstractIdLink<E> withId(GenericId<E, ?, ?> newId);
}
