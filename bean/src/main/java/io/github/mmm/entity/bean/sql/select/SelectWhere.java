/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.Where;

/**
 * A {@link Where}-{@link Clause} of an SQL {@link SelectStatement}.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public class SelectWhere<R> extends Where<R, SelectWhere<R>> implements ClauseWithGroupBy<R>, ClauseWithOrderBy<R> {

  /** @see #get() */
  private final SelectStatement<R> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link SelectStatement}.
   */
  public SelectWhere(SelectStatement<R> statement) {

    super();
    this.statement = statement;
  }

  @Override
  public SelectStatement<R> get() {

    return this.statement;
  }

}
