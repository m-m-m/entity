/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.value.PropertyPath;

/**
 * {@link Select} to query the {@link SelectFrom#getEntity() primary entity}.
 *
 * @param <R> type of the result of the selection.
 * @see Select#entity(EntityBean)
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
    setResultName(VALUE_RESULT_ENTITY);
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
