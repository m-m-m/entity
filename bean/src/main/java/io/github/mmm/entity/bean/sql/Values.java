/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link Values}-{@link Clause} containing {@link #getAssignments() assignments} for values to insert.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class Values<E extends EntityBean, SELF extends Values<E, SELF>> extends AssignmentClause<E, SELF> {

  /** Name of {@link Values} for marshaling. */
  public static final String NAME_VALUES = "values";

  /**
   * The constructor.
   */
  public Values() {

    super();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_VALUES;
  }

}
