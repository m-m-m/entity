/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.select;

import java.util.Objects;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.criteria.CriteriaAggregation;
import io.github.mmm.value.CriteriaObject;
import io.github.mmm.value.PropertyPath;

/**
 * {@link Select} to query a single {@link #getSelection() selection}.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public class SelectSingle<R> extends Select<R> {

  private final CriteriaObject<R> selection;

  /**
   * The constructor.
   *
   * @param selection the {@link CriteriaObject} to select. Typically a {@link PropertyPath property} but may also be
   *        a {@link CriteriaAggregation}, etc.
   */
  public SelectSingle(CriteriaObject<R> selection) {

    super(null);
    Objects.requireNonNull(selection);
    this.selection = selection;
    setResultName(VALUE_RESULT_SINLGE);
    add(selection);
  }

  /**
   * @return the single selection to select.
   */
  public CriteriaObject<R> getSelection() {

    return this.selection;
  }

  @Override
  public SelectSingle<R> distinct() {

    super.distinct();
    return this;
  }

  @Override
  public <E extends EntityBean> SelectFrom<R, E> from(E entity) {

    return super.from(entity);
  }

  @Override
  public boolean isSelectEntity() {

    return false;
  }

  @Override
  public boolean isSelectResult() {

    return false;
  }

  @Override
  public boolean isSelectSingle() {

    return true;
  }
}
