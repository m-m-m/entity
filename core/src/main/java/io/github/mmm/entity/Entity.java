/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity;

import io.github.mmm.entity.id.Id;

/**
 * This is the interface for an entity that can be loaded from or saved to a database. It can be identified uniquely by
 * its {@link #getId() primary key}. Typically you may want to use {@code EntityBean} but for legacy technologies such
 * as JPA you have to use standard Java Beans.
 *
 * @since 1.0.0
 */
public interface Entity {

  /** The property name of {@link #getId() ID}. */
  String PROPERTY_NAME_ID = "Id";

  /**
   * @return the unique ID (primary key) of this entity or {@code null} if not available (e.g. entity is not
   *         persistent).
   */
  Id<? extends Entity> getId();

  /**
   * @param id the new {@link #getId() ID}.
   */
  void setId(Id<? extends Entity> id);

  /**
   * @param <B> type of {@link Entity}.
   * @param bean the {@link Entity} instance. May be {@code null}.
   * @return the {@link Id} of the given {@link Entity}. May be {@code null} if given {@link Entity} was {@code null} or
   *         its {@link #getId() Id} is {@code null}.
   */
  @SuppressWarnings("unchecked")
  static <B extends Entity> Id<B> getId(B bean) {

    if (bean == null) {
      return null;
    }
    return (Id<B>) bean.getId();
  }

}
