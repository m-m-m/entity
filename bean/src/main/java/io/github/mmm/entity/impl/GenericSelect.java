package io.github.mmm.entity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import io.github.mmm.entity.bean.sql.select.Select;

/**
 * {@link Select} for generic usage like unmarshalling.
 *
 * @param <R> type of the result of the statement.
 * @since 1.0.0
 */
public class GenericSelect<R> extends Select<R> {

  private final List<Supplier<?>> selections;

  /**
   * The constructor.
   */
  public GenericSelect() {

    super(null);
    this.selections = new ArrayList<>();
  }

  @Override
  public List<Supplier<?>> getSelections() {

    return this.selections;
  }

  @Override
  public void setResultBean(R resultBean) {

    super.setResultBean(resultBean);
  }

}
