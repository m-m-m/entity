package io.github.mmm.entity.bean.db.dialect.sqlserver;

import io.github.mmm.entity.bean.db.dialect.AbstractDbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.orm.Orm;
import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;

/**
 * Implementation of {@link DbDialect} for <a href="https://docs.microsoft.com/en-us/sql/">MS SQL Server</a>.
 *
 * @since 1.0.0
 */
public class SqlServerDialect extends AbstractDbDialect<SqlServerDialect> {

  /**
   * The constructor.
   */
  public SqlServerDialect() {

    super(new SqlServerTypeMapper());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected SqlServerDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getName() {

    return "mssql";
  }

  @Override
  protected SqlServerDialect withOrm(Orm newOrm) {

    return new SqlServerDialect(newOrm);
  }

  @Override
  public DbStatementFormatter createFormatter() {

    return new SqlServerFormatter(getOrm());
  }

}
