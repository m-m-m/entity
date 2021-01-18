/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.property.criteria.CriteriaOrdering;

/**
 * {@link MainClause} allowing to {@link #orderBy(CriteriaOrdering) begin} a {@link Having}-clause.
 *
 * @param <E> type of the {@link io.github.mmm.entity.bean.sql.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public interface ClauseWithOrderBy<E extends EntityBean> extends MainClause<E> {

  @Override
  SelectStatement<E> get();

  /**
   * @param ordering the {@link CriteriaOrdering} to add as {@link OrderBy}-clause.
   * @return the {@link OrderBy}-clause for fluent API calls.
   */
  default OrderBy<E> orderBy(CriteriaOrdering ordering) {

    OrderBy<E> orderBy = get().getOrderBy();
    orderBy.and(ordering);
    return orderBy;
  }

  /**
   * @param orderings the {@link CriteriaOrdering}s to add as {@link OrderBy}-clause.
   * @return the {@link OrderBy}-clause for fluent API calls.
   */
  default OrderBy<E> orderBy(CriteriaOrdering... orderings) {

    OrderBy<E> orderBy = get().getOrderBy();
    orderBy.and(orderings);
    return orderBy;
  }

}
