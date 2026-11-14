/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;

/// Extends [AbstractLink] with the [Id] property.
///
/// @param <E> the generic type of the [linked][#getEntity()] entity.
///
/// @since 1.0.0
public abstract class AbstractIdLink<E> extends AbstractLink<E> {

  private /* final */ GenericId<E, ?, ?, ?> id;

  /// @see #getEntity()
  private E entity;

  /// The constructor.
  ///
  /// @param id - see [#getId()].
  /// @param entity - see [#getEntity()].
  @SuppressWarnings("unchecked")
  protected AbstractIdLink(Id<E> id, E entity) {

    super();
    GenericId<E, ?, ?, ?> gid = (GenericId<E, ?, ?, ?>) id;
    if (entity == null) {
      if (gid == null) {
        throw new IllegalArgumentException("Cannot create link with neither ID nor target entity!");
      }
    } else if (gid == null) {
      gid = (GenericId<E, ?, ?, ?>) Id.from((Entity) this.entity);
    }
    if ((gid != null) && isRemoveRevision()) {
      gid = gid.withoutRevision();
    }
    this.id = gid;
    this.entity = entity;
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
      GenericId<E, ?, ?, ?> gid = (GenericId<E, ?, ?, ?>) Id.from((Entity) this.entity);
      if ((gid != null) && isRemoveRevision()) {
        gid = gid.withoutRevision();
      }
      this.id = gid;
    }
    return this.id;
  }

  /// @return `true` if the [revision][Id#getRevision()] should be [removed][GenericId#withoutRevision()]
  ///         from the [Id], `false` otherwise.
  protected boolean isRemoveRevision() {

    return false;
  }

  @Override
  public boolean isResolved() {

    return (this.entity != null);
  }

  @Override
  public E getEntity() {

    return this.entity;
  }

  /// @param resolver the resolver [Function].
  /// @return the [target entity][#getEntity()].
  protected synchronized E updateTarget(Function<Id<E>, E> resolver) {

    if ((this.entity == null) && (resolver != null)) {
      this.entity = resolver.apply(getId());
    }
    return this.entity;

  }

  /// @param type the new value of [Id#getEntityClass()]. Exact type should actually be [Class]`<E>`
  ///        but this prevents simple usage.
  /// @return a copy of this [Link] with the given [type][Id#getEntityClass()] or this [Link] itself if
  ///         already satisfying.
  /// @see GenericId#withEntityType(Class)
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public AbstractIdLink<E> withType(Class<?> type) {

    GenericId newId = this.id.withEntityTypeGeneric(type);
    if (newId == this.id) {
      return this;
    }
    return withId(newId);
  }

  /// @param newId the new [#getId()].
  /// @return a copy of this link with the given [Id].
  protected abstract AbstractIdLink<E> withId(GenericId<E, ?, ?, ?> newId);
}
