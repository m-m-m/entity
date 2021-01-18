/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.From;
import io.github.mmm.property.criteria.CriteriaPredicate;

/**
 * A {@link From}-{@link Clause} of an SQL {@link SelectStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class SelectFrom<E extends EntityBean> extends From<E, SelectFrom<E>>
    implements ClauseWithGroupBy<E>, ClauseWithOrderBy<E> {

  private final SelectStatement<E> statement;

  /**
   * The constructor.
   *
   * @param select the {@link Select}.
   * @param entity the {@link #getEntity() entity}.
   */
  public SelectFrom(Select select, E entity) {

    this(select, entity, null);
  }

  /**
   * The constructor.
   *
   * @param select the {@link Select}.
   * @param entity the {@link #getEntity() entity}.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public SelectFrom(Select select, E entity, String entityName) {

    super(entity, entityName);
    this.statement = new SelectStatement<>(select, this);
  }

  @Override
  public SelectWhere<E> where(CriteriaPredicate predicate) {

    SelectWhere<E> where = this.statement.getWhere();
    where.and(predicate);
    return where;
  }

  @Override
  public SelectWhere<E> where(CriteriaPredicate... predicates) {

    SelectWhere<E> where = this.statement.getWhere();
    where.and(predicates);
    return where;
  }

  @Override
  public SelectStatement<E> get() {

    return this.statement;
  }

}
