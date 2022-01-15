/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.create;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractEntityClause;
import io.github.mmm.entity.bean.db.statement.AbstractStatement;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.StartClause;
import io.github.mmm.entity.bean.db.statement.DbStatement;
import io.github.mmm.entity.bean.db.statement.delete.Delete;

/**
 * {@link DbStatement} to {@link Delete}
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class CreateTableStatement<E extends EntityBean> extends AbstractStatement<E> {

  private final CreateTable<E> createTable;

  private final CreateTableColumns<E> columns;

  /**
   * The constructor.
   *
   * @param createTable the {@link #getCreateTable() create table}.
   */
  public CreateTableStatement(CreateTable<E> createTable) {

    super();
    this.createTable = createTable;
    this.columns = new CreateTableColumns<>(this);
  }

  /**
   * @deprecated use {@link #getCreateTable()} to make it more explicit.
   */
  @Deprecated
  @Override
  public StartClause getStart() {

    return this.createTable;
  }

  /**
   * @return the opening {@link CreateTable}-{@link DbClause}.
   */
  public CreateTable<E> getCreateTable() {

    return this.createTable;
  }

  /**
   * @return columns
   */
  public CreateTableColumns<E> getColumns() {

    return this.columns;
  }

  @Override
  protected void addClauses(List<DbClause> list) {

    list.add(this.createTable);
    list.add(this.columns);
  }

}
