/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.value.PropertyPath;

/**
 * {@link StartClause} of a {@link SelectStatement} to query data from the database.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public final class SelectColumn<R> extends Select<R> {

  private final PropertyPath<R> property;

  /**
   * The constructor.
   *
   * @param property the single {@link #getProperty() property to select}.
   */
  public SelectColumn(PropertyPath<R> property) {

    super(null);
    this.property = property;
  }

  /**
   * @return the property to select.
   */
  public PropertyPath<R> getProperty() {

    return this.property;
  }

  @Override
  public List<Supplier<?>> getSelections() {

    return Collections.singletonList(this.property);
  }

  @Override
  public SelectColumn<R> distinct() {

    super.distinct();
    return this;
  }

  @Override
  public <E extends EntityBean> SelectFrom<R, E> from(E entity) {

    return super.from(entity);
  }

}
