/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.merge;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.MainDbClause;
import io.github.mmm.entity.bean.db.statement.ValuesClause;

/**
 * {@link ValuesClause} of an {@link MergeStatement}.
 *
 * @param <E> type of the {@link MergeInto#getEntity() entity}.
 * @since 1.0.0
 */
public class MergeValues<E extends EntityBean> extends ValuesClause<E, MergeValues<E>> implements MainDbClause<E> {

  private final MergeStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link MergeStatement}.
   */
  public MergeValues(MergeStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected E getEntity() {

    return this.statement.getInto().getEntity();
  }

  @Override
  public MergeStatement<E> get() {

    return this.statement;
  }

}
