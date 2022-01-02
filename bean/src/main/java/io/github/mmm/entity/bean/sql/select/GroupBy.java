/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.PropertyClause;

/**
 * A {@link GroupBy}-{@link Clause} of an SQL {@link SelectStatement}.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public class GroupBy<R> extends PropertyClause<R, GroupBy<R>> implements ClauseWithHaving<R>, ClauseWithOrderBy<R> {

  /** Name of {@link GroupBy} for marshaling. */
  public static final String NAME_GROUP_BY = "groupBy";

  /** @see #get() */
  private final SelectStatement<R> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link SelectStatement}.
   */
  public GroupBy(SelectStatement<R> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected String getMarshallingName() {

    return NAME_GROUP_BY;
  }

  @Override
  public SelectStatement<R> get() {

    return this.statement;
  }

}
