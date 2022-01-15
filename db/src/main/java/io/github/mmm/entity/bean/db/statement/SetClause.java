/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link SetClause}-{@link DbClause} containing {@link #getAssignments() assignments} to set or update.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class SetClause<E extends EntityBean, SELF extends SetClause<E, SELF>> extends AssignmentClause<E, SELF>
    implements TypedClauseWithWhere<E> {

  /** Name of {@link SetClause} for marshaling. */
  public static final String NAME_SET = "set";

  /**
   * The constructor.
   */
  public SetClause() {

    super();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_SET;
  }

}
