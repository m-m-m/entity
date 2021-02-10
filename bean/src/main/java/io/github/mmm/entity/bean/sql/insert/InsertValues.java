/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.insert;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.entity.bean.sql.Values;

/**
 * {@link Values}-{@link Clause} of an {@link InsertStatement}.
 *
 * @param <E> type of the {@link InsertInto#getEntity() entity}.
 * @since 1.0.0
 */
public class InsertValues<E extends EntityBean> extends Values<E, InsertValues<E>> implements MainClause<E> {

  private final InsertStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link InsertStatement}.
   */
  public InsertValues(InsertStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected E getEntity() {

    return this.statement.getInto().getEntity();
  }

  @Override
  public InsertStatement<E> get() {

    return this.statement;
  }

}
