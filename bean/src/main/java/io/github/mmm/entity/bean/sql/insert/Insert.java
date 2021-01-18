/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.insert;

import io.github.mmm.entity.bean.sql.AbstractClause;
import io.github.mmm.entity.bean.sql.StartClause;

/**
 * {@link StartClause} to insert data into the database.
 *
 * @since 1.0.0
 */
public final class Insert extends AbstractClause implements StartClause {

  /** Name of {@link Insert} for marshaling. */
  public static final String NAME_INSERT = "insert";

  /**
   * The constructor.
   */
  public Insert() {

    super();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_INSERT;
  }
}
