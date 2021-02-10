/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.update;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.entity.bean.sql.Set;
import io.github.mmm.entity.bean.sql.Values;
import io.github.mmm.entity.bean.sql.insert.InsertInto;
import io.github.mmm.entity.bean.sql.insert.InsertStatement;

/**
 * {@link Values}-{@link Clause} of an {@link InsertStatement}.
 *
 * @param <E> type of the {@link InsertInto#getEntity() entity}.
 * @since 1.0.0
 */
public class UpdateSet<E extends EntityBean> extends Set<E, UpdateSet<E>> implements MainClause<E> {

  private final UpdateStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link UpdateStatement}.
   */
  public UpdateSet(UpdateStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected E getEntity() {

    return this.statement.getUpdate().getEntity();
  }

  @Override
  public UpdateStatement<E> get() {

    return this.statement;
  }

}
