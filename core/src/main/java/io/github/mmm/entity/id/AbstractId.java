/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * This is the abstract base implementation of {@link Id}.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #get() ID}.
 * @param <V> type of the {@link #getVersion() version}.
 * @since 1.0.0
 */
public abstract class AbstractId<E, I, V extends Comparable<?>> implements GenericId<E, I, V> {

  private final Class<E> entityType;

  /**
   * The constructor.
   *
   * @param entityType - see {@link #getEntityType()}.
   */
  public AbstractId(Class<E> entityType) {

    super();
    this.entityType = entityType;
  }

  @Override
  public final Class<E> getEntityType() {

    return this.entityType;
  }

  @Override
  public final int hashCode() {

    return ~get().hashCode();
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj == null) || !(obj instanceof AbstractId)) {
      return false;
    }
    AbstractId<?, ?, ?> other = (AbstractId<?, ?, ?>) obj;
    if (!Objects.equals(get(), other.get())) {
      return false;
    }
    if ((this.entityType != null) && (other.entityType != null) && !this.entityType.equals(other.entityType)) {
      return false;
    }
    if (!Objects.equals(getVersion(), other.getVersion())) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(32);
    toString(buffer);
    return buffer.toString();
  }

  /**
   * @see #toString()
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(CharSequence) append} the string
   *        representation to.
   */
  protected void toString(StringBuilder buffer) {

    I id = get();
    if (id == null) {
      return;
    }
    buffer.append(id);
    V version = getVersion();
    if (version != null) {
      buffer.append(VERSION_SEPARATOR);
      buffer.append(version);
    }
  }

}
