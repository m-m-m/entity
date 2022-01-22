/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.create;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractEntityClause;
import io.github.mmm.entity.bean.db.statement.AliasMap;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.StartClause;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link CreateTable}-{@link DbClause} of an SQL {@link CreateTableStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class CreateTable<E extends EntityBean> extends AbstractEntityClause<E, E, CreateTable<E>>
    implements StartClause {

  /** Name of {@link CreateTable} for marshaling. */
  public static final String NAME_CREATE_TABLE = "createTable";

  private final CreateTableStatement<E> statement;

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public CreateTable(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public CreateTable(E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new CreateTableStatement<>(this);
  }

  @Override
  protected String getMarshallingName() {

    return NAME_CREATE_TABLE;
  }

  /**
   * @param property the {@link PropertyPath property} to add as column.
   * @return the {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> column(PropertyPath<?> property) {

    CreateTableColumns<E> columns = this.statement.getColumns();
    columns.and(property);
    return columns;
  }

  /**
   * @param property the {@link PropertyPath property} to add as column with
   *        {@link io.github.mmm.entity.bean.db.constraint.NotNullConstraint}.
   * @return the {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> columnNotNull(PropertyPath<?> property) {

    CreateTableColumns<E> columns = this.statement.getColumns();
    columns.andNotNull(property);
    return columns;
  }

  /**
   * @param property the {@link PropertyPath property} to add as column with
   *        {@link io.github.mmm.entity.bean.db.constraint.UniqueConstraint}.
   * @return the {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> columnUnique(PropertyPath<?> property) {

    CreateTableColumns<E> columns = this.statement.getColumns();
    columns.andUnique(property);
    return columns;
  }

  /**
   * @param properties the {@link PropertyPath properties} to add as columns.
   * @return the {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> column(PropertyPath<?>... properties) {

    CreateTableColumns<E> columns = this.statement.getColumns();
    for (PropertyPath<?> property : properties) {
      columns.and(property);
    }
    return columns;
  }

  /**
   * Creates {@link #column(PropertyPath) columns} for all {@link EntityBean#getProperties() properties} of the
   * {@link #getEntity() entity}.
   *
   * @return the {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> columns() {

    CreateTableColumns<E> columns = this.statement.getColumns();
    for (PropertyPath<?> property : getEntity().getProperties()) {
      columns.and(property);
    }
    return columns;
  }

  @Override
  // make visible
  protected AliasMap getAliasMap() {

    return super.getAliasMap();
  }

}
