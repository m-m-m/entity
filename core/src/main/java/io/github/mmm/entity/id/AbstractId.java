/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * This is the abstract base implementation of {@link Id}.
 *
 * @param <E> type of the identified entity.
 * @param <P> type of the {@link #getPk() primary key}.
 * @param <R> type of the {@link #getRevision() revision}.
 * @since 1.0.0
 */
public abstract class AbstractId<E, P, R extends Comparable<?>> implements GenericId<E, P, R> {

  /** @see #getEntityClass() */
  protected final Class<E> entityClass;

  /**
   * The constructor.
   *
   * @param entityType - see {@link #getEntityClass()}.
   */
  public AbstractId(Class<E> entityType) {

    super();
    this.entityClass = entityType;
  }

  @Override
  public final Class<E> getEntityClass() {

    return this.entityClass;
  }

  @Override
  public final int hashCode() {

    return ~getPk().hashCode();
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
    if (!Objects.equals(getPk(), other.getPk())) {
      return false;
    }
    if ((this.entityClass != null) && (other.entityClass != null) && !this.entityClass.equals(other.entityClass)) {
      return false;
    }
    if (!Objects.equals(getRevision(), other.getRevision())) {
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

    P id = getPk();
    if (id == null) {
      return;
    }
    buffer.append(id);
    R revision = getRevision();
    if (revision != null) {
      buffer.append(REVISION_SEPARATOR);
      buffer.append(revision);
    }
  }

}
