/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.merge;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractDbStatement;
import io.github.mmm.entity.bean.db.statement.AliasMap;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.DbStatement;
import io.github.mmm.entity.bean.db.statement.upsert.UpsertInto;
import io.github.mmm.entity.bean.db.statement.upsert.UpsertValues;

/**
 * {@link DbStatement} to {@link Merge} data into the database.
 *
 * @param <E> type of the {@link UpsertInto#getEntity() entity}.
 * @since 1.0.0
 */
public class MergeStatement<E extends EntityBean> extends AbstractDbStatement<E> {

  private final Merge merge;

  private final MergeInto<E> into;

  private final MergeValues<E> values;

  /**
   * The constructor.
   *
   * @param merge the {@link #getMerge() MERGE}.
   * @param into the {@link #getInto() INTO}.
   */
  public MergeStatement(Merge merge, MergeInto<E> into) {

    super();
    this.merge = merge;
    this.into = into;
    this.values = new MergeValues<>(this);
  }

  /**
   * @deprecated use {@link #getMerge()} to make it more explicit.
   */
  @Deprecated
  @Override
  public Merge getStart() {

    return this.merge;
  }

  /**
   * @return the opening {@link Merge}-{@link DbClause clause}.
   */
  public Merge getMerge() {

    return this.merge;
  }

  /**
   * @return the {@link MergeInto INTO}-{@link DbClause clause}.
   */
  public MergeInto<E> getInto() {

    return this.into;
  }

  /**
   * @return the {@link UpsertValues VALUES}-{@link DbClause clause} or {@code null} if none was added.
   */
  public MergeValues<E> getValues() {

    return this.values;
  }

  @Override
  protected void addClauses(List<DbClause> list) {

    list.add(this.merge);
    list.add(this.into);
    list.add(this.values);
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.into.getAliasMap();
  }

}
