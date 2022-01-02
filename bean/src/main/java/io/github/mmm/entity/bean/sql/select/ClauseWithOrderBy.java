/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.property.criteria.CriteriaOrdering;

/**
 * {@link MainClause} allowing to {@link #orderBy(CriteriaOrdering) begin} a {@link Having}-clause.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public interface ClauseWithOrderBy<R> extends MainClause<R> {

  @Override
  SelectStatement<R> get();

  /**
   * @param ordering the {@link CriteriaOrdering} to add as {@link OrderBy}-clause.
   * @return the {@link OrderBy}-clause for fluent API calls.
   */
  default OrderBy<R> orderBy(CriteriaOrdering ordering) {

    OrderBy<R> orderBy = get().getOrderBy();
    orderBy.and(ordering);
    return orderBy;
  }

  /**
   * @param orderings the {@link CriteriaOrdering}s to add as {@link OrderBy}-clause.
   * @return the {@link OrderBy}-clause for fluent API calls.
   */
  default OrderBy<R> orderBy(CriteriaOrdering... orderings) {

    OrderBy<R> orderBy = get().getOrderBy();
    orderBy.and(orderings);
    return orderBy;
  }

}
