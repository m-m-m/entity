/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.dialect.h2;

import io.github.mmm.entity.bean.db.dialect.AbstractDbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.orm.Orm;
import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;

/**
 * Implementation of {@link DbDialect} for H2 database.
 */
public final class H2Dialect extends AbstractDbDialect<H2Dialect> {

  /**
   * The constructor.
   */
  public H2Dialect() {

    super(new H2TypeMapping());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected H2Dialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getName() {

    return "h2";
  }

  @Override
  protected H2Dialect withOrm(Orm newOrm) {

    return new H2Dialect(newOrm);
  }

  @Override
  public DbStatementFormatter createFormatter() {

    return new H2Formatter(getOrm());
  }

}
