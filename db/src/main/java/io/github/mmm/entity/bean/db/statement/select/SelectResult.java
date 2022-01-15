package io.github.mmm.entity.bean.db.statement.select;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link Select} to query arbitrary {@link #getSelections() selections} as generic {@link Result}.
 *
 * @see Select#result()
 * @since 1.0.0
 */
public class SelectResult extends Select<Result> {

  /**
   * The constructor.
   */
  public SelectResult() {

    super(null);
    setResultName(VALUE_RESULT_RESULT);
  }

  @Override
  protected <E extends EntityBean> SelectFrom<Result, E> from(E entity) {

    return super.from(entity);
  }

}
