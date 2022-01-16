package io.github.mmm.entity.bean.db.statement.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.result.DbResultObject;

/**
 * {@link Select} to query arbitrary {@link #getSelections() selections} as generic {@link DbResultObject}.
 *
 * @see Select#result()
 * @since 1.0.0
 */
public class SelectResult extends Select<DbResultObject> {

  /**
   * The constructor.
   */
  public SelectResult() {

    super(null);
    setResultName(VALUE_RESULT_RESULT);
  }

  @Override
  protected <E extends EntityBean> SelectFrom<DbResultObject, E> from(E entity) {

    return super.from(entity);
  }

}
