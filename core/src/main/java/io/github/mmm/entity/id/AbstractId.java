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
 * @param <SELF> type of this class itself for fluent API calls.
 * @since 1.0.0
 */
public abstract class AbstractId<E, P, R extends Comparable<?>, SELF extends AbstractId<E, P, R, SELF>>
    implements GenericId<E, P, R, SELF> {

  /**
   * The constructor.
   */
  protected AbstractId() {

    super();
  }

  /**
   * @return this instance itself as {@code <SELF>}.
   */
  @SuppressWarnings("unchecked")
  protected SELF self() {

    return (SELF) this;
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
    AbstractId<?, ?, ?, ?> other = (AbstractId<?, ?, ?, ?>) obj;
    if (!Objects.equals(getPk(), other.getPk())) {
      return false;
    } else if (!Objects.equals(getRevision(), other.getRevision())) {
      return false;
    }
    Class<E> entityClass = getEntityClass();
    Class<?> otherEntityClass = other.getEntityClass();
    if ((entityClass != null) && (otherEntityClass != null) && !entityClass.equals(otherEntityClass)) {
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
