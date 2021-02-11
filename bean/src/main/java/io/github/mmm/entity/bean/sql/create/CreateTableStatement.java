/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.create;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.AbstractEntityClause;
import io.github.mmm.entity.bean.sql.AbstractStatement;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.entity.bean.sql.Statement;
import io.github.mmm.entity.bean.sql.delete.Delete;

/**
 * {@link Statement} to {@link Delete}
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
   * @return the opening {@link CreateTable}-{@link Clause}.
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
  protected void addClauses(List<Clause> list) {

    list.add(this.createTable);
    list.add(this.columns);
  }

}
