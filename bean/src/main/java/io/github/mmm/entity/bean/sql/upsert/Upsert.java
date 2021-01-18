/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.upsert;

import io.github.mmm.entity.bean.sql.AbstractClause;
import io.github.mmm.entity.bean.sql.StartClause;

/**
 * {@link StartClause} to insert or if already present update data in the database. Only supported by modern databases.
 *
 * @since 1.0.0
 */
public final class Upsert extends AbstractClause implements StartClause {

  /** Name of {@link Upsert} for marshaling. */
  public static final String NAME_UPSERT = "upsert";

  /**
   * The constructor.
   */
  public Upsert() {

    super();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_UPSERT;
  }
}
