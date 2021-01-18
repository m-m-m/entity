/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.PredicateClause;

/**
 * A {@link Having}-{@link Clause} of an SQL {@link SelectStatement}.
 *
 * @param <E> type of the {@link io.github.mmm.entity.bean.sql.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class Having<E extends EntityBean> extends PredicateClause<E, Having<E>> implements ClauseWithOrderBy<E> {

  /** Name of {@link Having} for marshaling. */
  public static final String NAME_HAVING = "having";

  private final SelectStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the owning {@link SelectStatement}.
   */
  public Having(SelectStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected String getMarshallingName() {

    return NAME_HAVING;
  }

  @Override
  public SelectStatement<E> get() {

    return this.statement;
  }

}
