/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import java.time.Instant;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.property.booleans.BooleanProperty;
import io.github.mmm.property.temporal.instant.InstantProperty;

/**
 * {@link EntityBean} that shall not be deleted physically from the underlying store (database). Instead on deletion, it
 * will be updated
 *
 * @since 1.0.0
 */
@AbstractInterface
public interface SoftDeletableEntity extends EntityBean {

  /**
   * @return {@code true} if logically deleted, {@code false} otherwise.
   */
  BooleanProperty Deleted();

  /**
   * @return {@link Instant} when this entity has been logically {@link #Deleted() deleted} or {@code null} if not
   *         deleted.
   */
  InstantProperty DeletedAt();

}
