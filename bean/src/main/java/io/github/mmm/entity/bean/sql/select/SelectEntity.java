/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.value.PropertyPath;

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

  /**
   * @return the {@link SelectFrom} for fluent API calls.
   */
  public SelectFrom<R, R> from() {

    return new SelectFrom<>(this, getResultBean());
  }

  @Override
  public SelectEntity<R> and(PropertyPath<?> property) {

    super.and(property);
    return this;
  }

  @Override
  public SelectEntity<R> and(PropertyPath<?>... properties) {

    super.and(properties);
    return this;
  }

}
