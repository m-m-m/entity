/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.delete;

import io.github.mmm.entity.bean.sql.AbstractClause;
import io.github.mmm.entity.bean.sql.StartClause;

/**
 * {@link StartClause} to delete data from the database.
 *
 * @since 1.0.0
 */
public final class Delete extends AbstractClause implements StartClause {

  /** Name of {@link Delete} for marshaling. */
  public static final String NAME_DELETE = "delete";

  /**
   * The constructor.
   */
  public Delete() {

    super();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_DELETE;
  }

}
