package io.github.mmm.entity.impl;

import io.github.mmm.entity.bean.sql.select.Select;

/**
 * {@link Select} for generic usage like unmarshalling.
 *
 * @param <R> type of the result of the statement.
 * @since 1.0.0
 */
public class GenericSelect<R> extends Select<R> {

  /**
   * The constructor.
   */
  public GenericSelect() {

    super(null);
  }

  @Override
  public void setResultBean(R resultBean) {

    super.setResultBean(resultBean);
  }

}
