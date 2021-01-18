/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.EntityBean;

/**
 * Abstract base class implementing {@link TypedClause}.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class AbstractTypedClause<E extends EntityBean, SELF extends AbstractTypedClause<E, SELF>>
    extends AbstractClause implements TypedClause<E> {

  /**
   * The constructor.
   */
  public AbstractTypedClause() {

    super();
  }

  /**
   * @return this instance itself.
   */
  @SuppressWarnings("unchecked")
  protected SELF self() {

    return (SELF) this;
  }

}
