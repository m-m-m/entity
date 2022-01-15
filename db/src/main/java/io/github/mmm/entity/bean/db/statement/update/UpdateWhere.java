/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.update;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.WhereClause;
import io.github.mmm.entity.bean.db.statement.select.SelectFrom;

/**
 * A {@link WhereClause}-{@link DbClause} of an SQL {@link UpdateStatement}.
 *
 * @param <E> type of the {@link SelectFrom#getEntity() entity}.
 * @since 1.0.0
 */
public class UpdateWhere<E extends EntityBean> extends WhereClause<E, UpdateWhere<E>> {

  /** @see #get() */
  private final UpdateStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link UpdateStatement}.
   */
  public UpdateWhere(UpdateStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  public UpdateStatement<E> get() {

    return this.statement;
  }

}
