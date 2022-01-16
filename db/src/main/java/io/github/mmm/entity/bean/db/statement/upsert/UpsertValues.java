/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.upsert;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.MainClause;
import io.github.mmm.entity.bean.db.statement.ValuesClause;

/**
 * {@link ValuesClause} of an {@link UpsertStatement}.
 *
 * @param <E> type of the {@link UpsertInto#getEntity() entity}.
 * @since 1.0.0
 */
public class UpsertValues<E extends EntityBean> extends ValuesClause<E, UpsertValues<E>> implements MainClause<E> {

  private final UpsertStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link UpsertStatement}.
   */
  public UpsertValues(UpsertStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected E getEntity() {

    return this.statement.getInto().getEntity();
  }

  @Override
  public UpsertStatement<E> get() {

    return this.statement;
  }

}
