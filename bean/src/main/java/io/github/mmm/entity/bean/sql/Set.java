/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link Set}-{@link Clause} containing {@link #getAssignments() assignments} to set or update.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class Set<E extends EntityBean, SELF extends Set<E, SELF>> extends AssignmentClause<E, SELF> {

  /** Name of {@link Set} for marshaling. */
  public static final String NAME_SET = "set";

  /**
   * The constructor.
   */
  public Set() {

    super();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_SET;
  }

}
