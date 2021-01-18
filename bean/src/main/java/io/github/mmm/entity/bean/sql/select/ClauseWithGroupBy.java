/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.value.PropertyPath;

/**
 * {@link MainClause} allowing to {@link #groupBy(PropertyPath) begin} a {@link GroupBy}-clause.
 *
 * @param <E> type of the {@link io.github.mmm.entity.bean.sql.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public interface ClauseWithGroupBy<E extends EntityBean> extends MainClause<E> {

  @Override
  SelectStatement<E> get();

  /**
   * @param path the {@link PropertyPath} to add as {@link GroupBy}-clause.
   * @return the {@link GroupBy}-clause for fluent API calls.
   */
  default GroupBy<E> groupBy(PropertyPath<?> path) {

    GroupBy<E> groupBy = get().getGroupBy();
    if (path != null) {
      groupBy.and(path);
    }
    return groupBy;
  }

  /**
   * @param paths the {@link PropertyPath}s to add as {@link GroupBy}-clause.
   * @return the {@link GroupBy}-clause for fluent API calls.
   */
  default GroupBy<E> groupBy(PropertyPath<?>... paths) {

    GroupBy<E> groupBy = get().getGroupBy();
    groupBy.and(paths);
    return groupBy;
  }

}
