/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.EntityBean;

/**
 * A fragment for an additional {@link #getEntity() entity} selection. It is a sub-{@link Clause clause} and shall never
 * be part of {@link Statement#getClauses()}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class EntitySubClause<E extends EntityBean> extends AbstractEntityClause<E, EntitySubClause<E>> {

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public EntitySubClause(E entity) {

    this(entity, null, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public EntitySubClause(E entity, String entityName) {

    this(entity, entityName, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   * @param alias the {@link #getAlias() alias}.
   */
  public EntitySubClause(E entity, String entityName, String alias) {

    super(entity, entityName);
    if (alias != null) {
      as(alias);
    }
  }

  @Override
  protected String getMarshallingName() {

    throw new IllegalStateException("Not a top-level clause!");
  }

}
