package io.github.mmm.entity.bean.db.dialect.postgresql;

import io.github.mmm.entity.bean.db.dialect.AbstractDbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.orm.Orm;
import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;

/**
 * Implementation of {@link DbDialect} for H2 database.
 */
public class PostgreSqlDialect extends AbstractDbDialect<PostgreSqlDialect> {

  /**
   * The constructor.
   */
  public PostgreSqlDialect() {

    super(new PostgreSqlTypeMapper());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected PostgreSqlDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getName() {

    return "postgresql";
  }

  @Override
  protected PostgreSqlDialect withOrm(Orm newOrm) {

    return new PostgreSqlDialect(newOrm);
  }

  @Override
  public DbStatementFormatter createFormatter() {

    return new PostgreSqlFormatter(getOrm());
  }

}
