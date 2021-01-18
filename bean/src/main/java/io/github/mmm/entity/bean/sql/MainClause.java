/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link TypedClause} that can be an ending {@link Clause} of an SQL {@link Statement} and allows to {@link #get() get}
 * the resulting {@link Statement}.
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public interface MainClause<E extends EntityBean> extends TypedClause<E> {

  /**
   * @return the actual {@link Statement} containing all {@link Clause}s and representing your entire SQL.
   */
  Statement<E> get();

}
