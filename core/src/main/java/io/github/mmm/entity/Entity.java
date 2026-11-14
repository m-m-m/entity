/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity;

import io.github.mmm.entity.id.Id;

/// This is the interface for an entity that can be loaded from or saved to a database. It can be identified uniquely by
/// its [primary key][#getId()]. Typically you may want to use `EntityBean` but for legacy technologies such
/// as JPA you have to use standard Java Beans.
///
/// @since 1.0.0
public interface Entity {

  /// The property name of [ID][#getId()].
  String PROPERTY_NAME_ID = "Id";

  /// Please prefer the usage of [#getId(Entity)] or [Id#from(Entity)] instead of using this method directly.
  ///
  /// @return the unique ID (primary key) of this entity. This method should never return `null`. A transient
  ///         entity should return an [empty][Id#isEmpty()] [Id].
  /// @see #getId(Entity)
  Id<?/* extends Entity */> getId();

  /// Sets the [ID][#getId()] to the given new [Id].
  /// **ATTENTION:**
  /// This method is reserved for framework code and should not be used by regular API users.
  ///
  /// @param id the new [ID][#getId()].
  void setId(Id<?> id);

  /// @return the Java [Class] of this entity. In the most simple case the same as [Object#getClass()] but in
  ///         case of proxy objects [Object#getClass()] may return an artificial [Class] object while this
  ///         method will typically return what you expect.
  default Class<?> getJavaClass() {

    return getClass();
  }

  /// Type-safe variant of [#getId()].
  ///
  /// @param <E> type of [Entity].
  /// @param entity the [Entity] instance. May be `null`.
  /// @return the [Id] of the given [Entity]. May be `null` if given [Entity] was `null` or
  ///         its [Id][#getId()] is `null`.
  @SuppressWarnings("unchecked")
  static <E extends Entity> Id<E> getId(E entity) {

    if (entity == null) {
      return null;
    }
    return (Id<E>) entity.getId();
  }

  /// Type-safe variant of [#getJavaClass()].
  ///
  /// @param <E> type of [Entity].
  /// @param entity the [Entity] instance. May be `null`.
  /// @return the [Java class][#getJavaClass()] of the given [Entity]. May be `null` if given
  ///         [Entity] was `null`.
  @SuppressWarnings("unchecked")
  static <E extends Entity> Class<E> getJavaClass(E entity) {

    if (entity == null) {
      return null;
    }
    return (Class<E>) entity.getJavaClass();
  }

}
