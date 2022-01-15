/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.select;

import io.github.mmm.property.criteria.CriteriaAggregation;
import io.github.mmm.property.criteria.CriteriaExpression;

/**
 * {@link Select} to query a single {@link CriteriaExpression} (e.g. a {@link CriteriaAggregation aggregate function}).
 *
 * @param <R> type of the result of the selection.
 * @see Select#expression(CriteriaExpression)
 * @since 1.0.0
 */
public final class SelectExpression<R> extends SelectSingle<R> {

  private final CriteriaExpression<R> selection;

  /**
   * The constructor.
   *
   * @param expression the single {@link #getSelection() expression to select}.
   */
  public SelectExpression(CriteriaExpression<R> expression) {

    super();
    this.selection = expression;
    and(expression);
  }

  /**
   * @return the {@link CriteriaAggregation} to select.
   */
  @Override
  public CriteriaExpression<R> getSelection() {

    return this.selection;
  }

  @Override
  public SelectExpression<R> distinct() {

    super.distinct();
    return this;
  }

}
