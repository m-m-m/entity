/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.update;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.MainDbClause;
import io.github.mmm.entity.bean.db.statement.SetClause;
import io.github.mmm.entity.bean.db.statement.ValuesClause;
import io.github.mmm.entity.bean.db.statement.insert.InsertInto;
import io.github.mmm.entity.bean.db.statement.insert.InsertStatement;
import io.github.mmm.property.criteria.CriteriaPredicate;

/**
 * {@link ValuesClause}-{@link DbClause} of an {@link InsertStatement}.
 *
 * @param <E> type of the {@link InsertInto#getEntity() entity}.
 * @since 1.0.0
 */
public class UpdateSet<E extends EntityBean> extends SetClause<E, UpdateSet<E>> implements MainDbClause<E> {

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
  public UpdateWhere<E> where(CriteriaPredicate predicate) {

    UpdateWhere<E> where = this.statement.getWhere();
    where.and(predicate);
    return where;
  }

  @Override
  public UpdateWhere<E> where(CriteriaPredicate... predicates) {

    UpdateWhere<E> where = this.statement.getWhere();
    where.and(predicates);
    return where;
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
