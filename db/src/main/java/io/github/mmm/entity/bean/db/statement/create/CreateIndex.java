/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.create;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractClause;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.FromClause;
import io.github.mmm.entity.bean.db.statement.StartClause;
import io.github.mmm.entity.bean.db.statement.delete.DeleteStatement;

/**
 * A {@link FromClause}-{@link DbClause} of an SQL {@link DeleteStatement}.
 *
 * @since 1.0.0
 */
public class CreateIndex extends AbstractClause implements StartClause {

  /** Name of {@link CreateIndex} for marshaling. */
  public static final String NAME_CREATE_INDEX = "createIndex";

  private String name;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name of the index}.
   */
  public CreateIndex(String name) {

    super();
    this.name = name;
  }

  /**
   * @return the name of the index.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getName()}.
   */
  public void setName(String name) {

    this.name = name;
  }

  @Override
  protected String getMarshallingName() {

    return NAME_CREATE_INDEX;
  }

  /**
   * @param <E> type of the {@link CreateIndexOn#getEntity() entity}.
   * @param entity the {@link CreateIndexOn#getEntity() entity} (table) to create an index on.
   * @return the {@link CreateIndexOn} for fluent API calls.
   */
  public <E extends EntityBean> CreateIndexOn<E> on(E entity) {

    return new CreateIndexOn<>(this, entity);
  }

  /**
   * @param <E> type of the {@link CreateIndexOn#getEntity() entity}.
   * @param entity the {@link CreateIndexOn#getEntity() entity} (table) to create an index on.
   * @param entityName the {@link CreateIndexOn#getEntityName() entity name} (table name) to create an index on.
   * @return the {@link CreateIndexOn} for fluent API calls.
   */
  public <E extends EntityBean> CreateIndexOn<E> on(E entity, String entityName) {

    return new CreateIndexOn<>(this, entity, entityName);
  }

}
