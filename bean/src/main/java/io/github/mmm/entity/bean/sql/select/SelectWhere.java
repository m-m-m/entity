/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.Where;

/**
 * A {@link Where}-{@link Clause} of an SQL {@link SelectStatement}.
 *
 * @param <E> type of the {@link SelectFrom#getEntity() entity}.
 * @since 1.0.0
 */
public class SelectWhere<E extends EntityBean> extends Where<E, SelectWhere<E>>
    implements ClauseWithGroupBy<E>, ClauseWithOrderBy<E> {

  /** @see #get() */
  private final SelectStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link SelectStatement}.
   */
  public SelectWhere(SelectStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  public SelectStatement<E> get() {

    return this.statement;
  }

}
