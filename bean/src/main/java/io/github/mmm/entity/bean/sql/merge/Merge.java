/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.merge;

import io.github.mmm.entity.bean.sql.AbstractClause;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.StartClause;

/**
 * {@link Merge}-{@link Clause} to merge data in the database.
 *
 * @since 1.0.0
 */
public final class Merge extends AbstractClause implements StartClause {

  /** Name of {@link Merge} for marshaling. */
  public static final String NAME_MERGE = "merge";

  /**
   * The constructor.
   */
  public Merge() {

    super();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_MERGE;
  }
}
