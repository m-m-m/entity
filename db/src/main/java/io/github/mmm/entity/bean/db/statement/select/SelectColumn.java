/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.StartClause;
import io.github.mmm.value.PropertyPath;

/**
 * {@link StartClause} of a {@link SelectStatement} to query data from the database.
 *
 * @param <R> type of the result of the selection.
 * @see Select#column(PropertyPath)
 * @since 1.0.0
 */
public final class SelectColumn<R> extends SelectSingle<R> {

  private final PropertyPath<R> selection;

  /**
   * The constructor.
   *
   * @param selection the single {@link #getSelection() property} to select.
   */
  public SelectColumn(PropertyPath<R> selection) {

    super();
    this.selection = selection;
    and(selection);
  }

  /**
   * @return the single {@link PropertyPath} of the property in {@link EntityBean} and according column in the database
   *         to select.
   */
  @Override
  public PropertyPath<R> getSelection() {

    return this.selection;
  }

  @Override
  public SelectColumn<R> distinct() {

    super.distinct();
    return this;
  }

}
