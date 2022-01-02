/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.value.PropertyPath;

/**
 * {@link MainClause} allowing to {@link #groupBy(PropertyPath) begin} a {@link GroupBy}-clause.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public interface ClauseWithGroupBy<R> extends MainClause<R> {

  @Override
  SelectStatement<R> get();

  /**
   * @param path the {@link PropertyPath} to add as {@link GroupBy}-clause.
   * @return the {@link GroupBy}-clause for fluent API calls.
   */
  default GroupBy<R> groupBy(PropertyPath<?> path) {

    GroupBy<R> groupBy = get().getGroupBy();
    if (path != null) {
      groupBy.and(path);
    }
    return groupBy;
  }

  /**
   * @param paths the {@link PropertyPath}s to add as {@link GroupBy}-clause.
   * @return the {@link GroupBy}-clause for fluent API calls.
   */
  default GroupBy<R> groupBy(PropertyPath<?>... paths) {

    GroupBy<R> groupBy = get().getGroupBy();
    groupBy.and(paths);
    return groupBy;
  }

}
