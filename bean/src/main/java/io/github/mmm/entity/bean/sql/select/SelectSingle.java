/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import java.util.function.Supplier;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link Select} to query a single {@link #getSelection() selection}.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public abstract class SelectSingle<R> extends Select<R> {

  /**
   * The constructor.
   */
  public SelectSingle() {

    super(null);
    setResultName(VALUE_RESULT_SINLGE);
  }

  /**
   * @return the single selection to select.
   */
  public abstract Supplier<R> getSelection();

  @Override
  public <E extends EntityBean> SelectFrom<R, E> from(E entity) {

    return super.from(entity);
  }

}
