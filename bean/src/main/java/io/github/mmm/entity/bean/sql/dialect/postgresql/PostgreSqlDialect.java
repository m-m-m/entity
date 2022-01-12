package io.github.mmm.entity.bean.sql.dialect.postgresql;

import io.github.mmm.entity.bean.sql.SqlFormatter;
import io.github.mmm.entity.bean.sql.dialect.AbstractSqlDialect;
import io.github.mmm.entity.bean.sql.dialect.SqlDialect;

/**
 * Implementation of {@link SqlDialect} for H2 database.
 */
public class PostgreSqlDialect extends AbstractSqlDialect {

  /**
   * The constructor.
   */
  public PostgreSqlDialect() {

    super(new PostgreSqlTypeMapper());
  }

  @Override
  public String getName() {

    return "postgresql";
  }

  @Override
  public SqlFormatter createFormatter() {

    return new PostgreSqlFormatter(getTypeMapper());
  }

}
