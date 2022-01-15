/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.select;

import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.PredicateClause;

/**
 * A {@link Having}-{@link DbClause} of an SQL {@link SelectStatement}.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public class Having<R> extends PredicateClause<R, Having<R>> implements ClauseWithOrderBy<R> {

  /** Name of {@link Having} for marshaling. */
  public static final String NAME_HAVING = "having";

  private final SelectStatement<R> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link SelectStatement}.
   */
  public Having(SelectStatement<R> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected String getMarshallingName() {

    return NAME_HAVING;
  }

  @Override
  public SelectStatement<R> get() {

    return this.statement;
  }

}
