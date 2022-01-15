package io.github.mmm.entity.bean.db.dialect.h2;

import io.github.mmm.entity.bean.db.dialect.AbstractDbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.statement.StatementFormatter;

/**
 * Implementation of {@link DbDialect} for H2 database.
 */
public class H2Dialect extends AbstractDbDialect {

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
  public StatementFormatter createFormatter() {

    return new H2SqlFormatter(getTypeMapping());
  }

}
