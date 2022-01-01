package io.github.mmm.entity.bean.sql.dialect;

import io.github.mmm.entity.bean.sql.SqlDialect;
import io.github.mmm.entity.bean.sql.SqlFormatter;

/**
 * Implementation of {@link SqlDialect} for H2 database.
 */
public class H2Dialect implements SqlDialect {

  @Override
  public SqlFormatter createFormatter() {

    return new H2SqlFormatter();
  }

}
