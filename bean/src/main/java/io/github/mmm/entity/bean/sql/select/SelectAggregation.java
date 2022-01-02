/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.property.criteria.CriteriaAggregation;

/**
 * {@link StartClause} of a {@link SelectStatement} to query data from the database.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public final class SelectAggregation<R> extends Select<R> {

  private final CriteriaAggregation<R> aggregation;

  /**
   * The constructor.
   *
   * @param aggregation the single {@link #getAggregation() aggregation to select}.
   */
  public SelectAggregation(CriteriaAggregation<R> aggregation) {

    super(null);
    this.aggregation = aggregation;
    and(aggregation);
  }

  /**
   * @return the {@link CriteriaAggregation} to select.
   */
  public CriteriaAggregation<R> getAggregation() {

    return this.aggregation;
  }

  @Override
  public SelectAggregation<R> distinct() {

    super.distinct();
    return this;
  }

  @Override
  public <E extends EntityBean> SelectFrom<R, E> from(E entity) {

    return super.from(entity);
  }

}
