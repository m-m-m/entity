/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.Objects;

import io.github.mmm.entity.id.Id;

/**
 * Abstract base implementation of {@link Link}.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} entity.
 *
 * @since 1.0.0
 */
public abstract class AbstractLink<E> implements Link<E> {

  /**
   * The constructor.
   */
  public AbstractLink() {

    super();
  }

  @Override
  public int hashCode() {

    Id<E> id = getId();
    if (id == null) {
      return 0;
    }
    return ~id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    Object id = ((AbstractLink<?>) obj).getId();
    if (!Objects.equals(getId(), id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return getId().toString();
  }

}
