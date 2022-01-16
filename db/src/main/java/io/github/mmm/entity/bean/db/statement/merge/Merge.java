/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.merge;

import io.github.mmm.entity.bean.db.statement.AbstractDbClause;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.StartClause;

/**
 * {@link Merge}-{@link DbClause} to merge data in the database.
 *
 * @since 1.0.0
 */
public final class Merge extends AbstractDbClause implements StartClause {

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
