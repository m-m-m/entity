/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/**
 * A fragment for an additional {@link #getEntity() entity} selection. It is a sub-{@link DbClause clause} and shall never
 * be part of {@link DbStatement#getClauses()}.
 *
 * @param <R> type of the result. Only different from {@literal <E>} for complex selects.
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class EntitySubClause<R, E extends EntityBean> extends AbstractEntityClause<R, E, EntitySubClause<R, E>> {

  /**
   * The constructor.
   *
   * @param aliasMap the {@link AliasMap}.
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public EntitySubClause(AliasMap aliasMap, E entity) {

    this(aliasMap, entity, null, null);
  }

  /**
   * The constructor.
   *
   * @param aliasMap the {@link AliasMap}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public EntitySubClause(AliasMap aliasMap, E entity, String entityName) {

    this(aliasMap, entity, entityName, null);
  }

  /**
   * The constructor.
   *
   * @param aliasMap the {@link AliasMap}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   * @param alias the {@link #getAlias() alias}.
   */
  public EntitySubClause(AliasMap aliasMap, E entity, String entityName, String alias) {

    super(aliasMap, entity, entityName);
    if (alias != null) {
      as(alias);
    }
  }

  @Override
  protected String getMarshallingName() {

    throw new IllegalStateException("Not a top-level clause!");
  }

}
