/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.property.criteria.CriteriaPredicate;

/**
 * {@link MainClause} allowing to {@link #having(CriteriaPredicate) begin} a {@link Having}-clause.
 *
 * @param <E> type of the {@link io.github.mmm.entity.bean.sql.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public interface ClauseWithHaving<E extends EntityBean> extends MainClause<E> {

  @Override
  SelectStatement<E> get();

  /**
   * @param predicate the {@link CriteriaPredicate} to add as {@link Having}-clause.
   * @return the {@link Having}-clause for fluent API calls.
   */
  default Having<E> having(CriteriaPredicate predicate) {

    Having<E> having = get().getHaving();
    having.and(predicate);
    return having;
  }

  /**
   * @param predicates the {@link CriteriaPredicate}s to add as {@link Having}-clause. They will be combined with
   *        {@link io.github.mmm.property.criteria.PredicateOperator#AND AND}.
   * @return the {@link Having}-clause for fluent API calls.
   */
  default Having<E> having(CriteriaPredicate... predicates) {

    Having<E> having = get().getHaving();
    having.and(predicates);
    return having;
  }

}
