package io.github.mmm.entity.bean.sql.dialect.h2;

import io.github.mmm.entity.bean.sql.SqlFormatter;
import io.github.mmm.entity.bean.sql.dialect.AbstractSqlDialect;
import io.github.mmm.entity.bean.sql.dialect.SqlDialect;

/**
 * Implementation of {@link SqlDialect} for H2 database.
 */
public class H2Dialect extends AbstractSqlDialect {

  /**
   * The constructor.
   */
  public H2Dialect() {

    super(new H2TypeMapper());
  }

  @Override
  public String getName() {

    return "h2";
  }

  @Override
  public SqlFormatter createFormatter() {

    return new H2SqlFormatter(getTypeMapper());
  }

}
