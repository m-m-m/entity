/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.EntityBean;

/**
 * A {@link From}-{@link Clause} of an SQL {@link Statement} such as {@link io.github.mmm.entity.bean.sql.select.Select}
 * or {@link io.github.mmm.entity.bean.sql.delete.Delete}.
 *
 * @param <R> type of the result. Only different from {@literal <E>} for complex selects.
 * @param <E> type of the {@link #getEntity() entity}.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class From<R, E extends EntityBean, SELF extends From<R, E, SELF>>
    extends AbstractEntitiesClause<R, E, SELF> implements MainClause<R>, TypedClauseWithWhere<R> {

  /** Name of {@link From} for marshaling. */
  public static final String NAME_FROM = "from";

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  protected From(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  protected From(E entity, String entityName) {

    super(entity, entityName);
  }

  @Override
  protected String getMarshallingName() {

    return NAME_FROM;
  }

}
