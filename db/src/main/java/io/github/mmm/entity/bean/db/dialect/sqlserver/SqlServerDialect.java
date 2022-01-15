package io.github.mmm.entity.bean.db.dialect.sqlserver;

import io.github.mmm.entity.bean.db.dialect.AbstractDbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;

/**
 * Implementation of {@link DbDialect} for <a href="https://docs.microsoft.com/en-us/sql/">MS SQL Server</a>.
 *
 * @since 1.0.0
 */
public class SqlServerDialect extends AbstractDbDialect {

  /**
   * The constructor.
   */
  public SqlServerDialect() {

    super(new SqlServerTypeMapper());
  }

  @Override
  public String getName() {

    return "mssql";
  }

  @Override
  public DbStatementFormatter createFormatter() {

    return new SqlServerFormatter(getTypeMapping());
  }

}
