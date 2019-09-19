/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity;

import net.sf.mmm.entity.id.Id;

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

}
