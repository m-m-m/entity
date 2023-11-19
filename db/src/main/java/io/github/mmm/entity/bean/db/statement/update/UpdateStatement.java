/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.update;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractDbClause;
import io.github.mmm.entity.bean.db.statement.AbstractDbStatement;
import io.github.mmm.entity.bean.db.statement.AliasMap;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.DbStatement;
import io.github.mmm.entity.bean.db.statement.insert.Insert;
import io.github.mmm.entity.bean.db.statement.insert.InsertInto;

/**
 * {@link DbStatement} to {@link Insert insert} data into the database.
 *
 * @param <E> type of the {@link InsertInto#getEntity() entity}.
 * @since 1.0.0
 */
public class UpdateStatement<E extends EntityBean> extends AbstractDbStatement<E> {

  private final Update<E> update;

  private final UpdateSet<E> set;

  private final UpdateWhere<E> where;

  /**
   * The constructor.
   *
   * @param update the {@link #getUpdate() update}.
   */
  public UpdateStatement(Update<E> update) {

    super();
    this.update = update;
    this.set = new UpdateSet<>(this);
    this.where = new UpdateWhere<>(this);
  }

  /**
   * @deprecated use {@link #getUpdate()} to make it more explicit.
   */
  @Deprecated
  @Override
  public Update<E> getStart() {

    return this.update;
  }

  /**
   * @return the opening {@link Update}-{@link DbClause}.
   */
  public Update<E> getUpdate() {

    return this.update;
  }

  /**
   * @return the {@link UpdateSet Set}-{@link DbClause}.
   */
  public UpdateSet<E> getSet() {

    return this.set;
  }

  /**
   * @return the {@link UpdateWhere Where}-{@link DbClause}.
   */
  public UpdateWhere<E> getWhere() {

    return this.where;
  }

  @Override
  protected void addClauses(List<AbstractDbClause> list) {

    list.add(this.update);
    list.add(this.set);
    list.add(this.where);
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.update.getAliasMap();
  }

}
