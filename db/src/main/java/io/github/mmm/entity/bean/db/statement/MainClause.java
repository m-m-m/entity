/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

/**
 * {@link TypedClause} that can be an ending {@link DbClause} of an SQL {@link DbStatement} and allows to {@link #get() get}
 * the resulting {@link DbStatement}.
 *
 * @param <E> type of this {@link DbClause} (typically the {@link AbstractEntityClause#getEntity() entity}).
 * @since 1.0.0
 */
public interface MainClause<E> extends TypedClause<E> {

  /**
   * @return the actual {@link DbStatement} containing all {@link DbClause}s and representing your entire SQL.
   */
  DbStatement<E> get();

}
