package io.github.mmm.entity.bean.db.dialect.postgresql;

import io.github.mmm.entity.bean.db.dialect.AbstractDbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.statement.StatementFormatter;

/**
 * Implementation of {@link DbDialect} for H2 database.
 */
public class PostgreSqlDialect extends AbstractDbDialect {

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
  public StatementFormatter createFormatter() {

    return new PostgreSqlFormatter(getTypeMapping());
  }

}
