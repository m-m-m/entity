/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.StartClause;

/**
 * {@link StartClause} of a {@link SelectStatement} to query data from the database.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public class SelectEntity<R extends EntityBean> extends Select<R> {

  /**
   * The constructor.
   *
   * @param entity the entity.
   */
  public SelectEntity(R entity) {

    super(entity);
    setResultName(null);
  }

  @Override
  public List<Supplier<?>> getSelections() {

    return Collections.emptyList();
  }

  /**
   * @return the {@link SelectFrom} for fluent API calls.
   */
  public SelectFrom<R, R> from() {

    return new SelectFrom<>(this, getResultBean());
  }

}
