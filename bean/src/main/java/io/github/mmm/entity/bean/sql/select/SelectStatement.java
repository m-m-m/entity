/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.AbstractStatement;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.Statement;

/**
 * {@link Statement} to query data from the database using a {@link Select}.
 *
 * @param <E> type of the {@link SelectFrom#getEntity() entity}.
 * @since 1.0.0
 */
public class SelectStatement<E extends EntityBean> extends AbstractStatement<E> {

  private final Select select;

  private final SelectFrom<E> from;

  private final SelectWhere<E> where;

  private final GroupBy<E> groupBy;

  private final Having<E> having;

  private final OrderBy<E> orderBy;

  /**
   * The constructor.
   *
   * @param select the {@link #getStart() starting} {@link Select SELECT}.
   * @param from the {@link #getFrom() FROM}.
   */
  protected SelectStatement(Select select, SelectFrom<E> from) {

    super();
    select.setStatement(this);
    this.select = select;
    this.from = from;
    this.where = new SelectWhere<>(this);
    this.groupBy = new GroupBy<>(this);
    this.having = new Having<>(this);
    this.orderBy = new OrderBy<>(this);
  }

  /**
   * @deprecated use {@link #getSelect()} to make it more explicit.
   */
  @Deprecated
  @Override
  public Select getStart() {

    return this.select;
  }

  /**
   * @return the opening {@link Select}.
   */
  public Select getSelect() {

    return this.select;
  }

  /**
   * @return the {@link SelectFrom FROM} {@link Clause}.
   */
  public SelectFrom<E> getFrom() {

    return this.from;
  }

  /**
   * @return the {@link SelectWhere Where}-{@link Clause}.
   */
  public SelectWhere<E> getWhere() {

    return this.where;
  }

  /**
   * @return the {@link GroupBy}-{@link Clause}.
   */
  public GroupBy<E> getGroupBy() {

    return this.groupBy;
  }

  /**
   * @return the {@link Having}-{@link Clause}.
   */
  public Having<E> getHaving() {

    return this.having;
  }

  /**
   * @return the {@link OrderBy}-{@link Clause}.
   */
  public OrderBy<E> getOrderBy() {

    return this.orderBy;
  }

  @Override
  protected void addClauses(List<Clause> list) {

    list.add(this.select);
    list.add(this.from);
    list.add(this.where);
    list.add(this.groupBy);
    list.add(this.having);
    list.add(this.orderBy);
  }

}
