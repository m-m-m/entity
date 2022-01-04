/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.delete;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.AliasMap;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.From;
import io.github.mmm.property.criteria.CriteriaPredicate;

/**
 * A {@link From}-{@link Clause} of an SQL {@link DeleteStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class DeleteFrom<E extends EntityBean> extends From<E, E, DeleteFrom<E>> {

  private final DeleteStatement<E> statement;

  /**
   * The constructor.
   *
   * @param delete the opening {@link Delete}.
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public DeleteFrom(Delete delete, E entity) {

    this(delete, entity, null);
  }

  /**
   * The constructor.
   *
   * @param delete the opening {@link Delete}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public DeleteFrom(Delete delete, E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new DeleteStatement<>(delete, this);
  }

  @Override
  public DeleteWhere<E> where(CriteriaPredicate predicate) {

    DeleteWhere<E> where = this.statement.getWhere();
    where.and(predicate);
    return where;
  }

  @Override
  public DeleteWhere<E> where(CriteriaPredicate... predicates) {

    DeleteWhere<E> where = this.statement.getWhere();
    where.and(predicates);
    return where;
  }

  @Override
  public DeleteStatement<E> get() {

    return this.statement;
  }

}
