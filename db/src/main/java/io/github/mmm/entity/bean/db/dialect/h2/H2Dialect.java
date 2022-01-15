package io.github.mmm.entity.bean.db.dialect.h2;

import io.github.mmm.entity.bean.db.dialect.AbstractDbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;

/**
 * Implementation of {@link DbDialect} for H2 database.
 */
public class H2Dialect extends AbstractDbDialect {

  /**
   * The constructor.
   */
  public H2Dialect() {

    super(new H2TypeMapping());
  }

  @Override
  public String getName() {

    return "h2";
  }

  @Override
  public DbStatementFormatter createFormatter() {

    return new H2Formatter(getTypeMapping());
  }

}
