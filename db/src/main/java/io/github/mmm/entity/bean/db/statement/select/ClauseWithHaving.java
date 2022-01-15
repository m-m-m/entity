/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.select;

import io.github.mmm.entity.bean.db.statement.MainClause;
import io.github.mmm.property.criteria.CriteriaPredicate;

/**
 * {@link MainClause} allowing to {@link #having(CriteriaPredicate) begin} a {@link Having}-clause.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public interface ClauseWithHaving<R> extends MainClause<R> {

  @Override
  SelectStatement<R> get();

  /**
   * @param predicate the {@link CriteriaPredicate} to add as {@link Having}-clause.
   * @return the {@link Having}-clause for fluent API calls.
   */
  default Having<R> having(CriteriaPredicate predicate) {

    Having<R> having = get().getHaving();
    having.and(predicate);
    return having;
  }

  /**
   * @param predicates the {@link CriteriaPredicate}s to add as {@link Having}-clause. They will be combined with
   *        {@link io.github.mmm.property.criteria.PredicateOperator#AND AND}.
   * @return the {@link Having}-clause for fluent API calls.
   */
  default Having<R> having(CriteriaPredicate... predicates) {

    Having<R> having = get().getHaving();
    having.and(predicates);
    return having;
  }

}
