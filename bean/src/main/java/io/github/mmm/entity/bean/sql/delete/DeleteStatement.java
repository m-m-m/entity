/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.delete;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.AbstractEntityClause;
import io.github.mmm.entity.bean.sql.AbstractStatement;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.entity.bean.sql.Statement;

/**
 * {@link Statement} to {@link Delete}
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class DeleteStatement<E extends EntityBean> extends AbstractStatement<E> {

  private final Delete delete;

  private final DeleteFrom<E> from;

  private final DeleteWhere<E> where;

  /**
   * The constructor.
   *
   * @param delete the {@link #getDelete() delete}.
   * @param from the #getFrom
   */
  public DeleteStatement(Delete delete, DeleteFrom<E> from) {

    super();
    this.delete = delete;
    this.from = from;
    this.where = new DeleteWhere<>(this);
  }

  /**
   * @deprecated use {@link #getDelete()} to make it more explicit.
   */
  @Deprecated
  @Override
  public StartClause getStart() {

    return this.delete;
  }

  /**
   * @return the opening {@link Delete}-{@link Clause}.
   */
  public Delete getDelete() {

    return this.delete;
  }

  /**
   * @return the {@link DeleteFrom From}-{@link Clause}.
   */
  public DeleteFrom<E> getFrom() {

    return this.from;
  }

  /**
   * @return the {@link DeleteWhere Where}-{@link Clause}.
   */
  public DeleteWhere<E> getWhere() {

    return this.where;
  }

  @Override
  protected void addClauses(List<Clause> list) {

    list.add(this.delete);
    list.add(this.from);
    list.add(this.where);
  }

}
