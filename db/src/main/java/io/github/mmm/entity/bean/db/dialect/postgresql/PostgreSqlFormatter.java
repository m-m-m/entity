/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.dialect.postgresql;

import io.github.mmm.entity.bean.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.entity.bean.db.orm.Orm;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * {@link DbDialectStatementFormatter} for PostgreSQL Database.
 *
 * @since 1.0.0
 */
public class PostgreSqlFormatter extends DbDialectStatementFormatter {

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   * @param criteriaFormatter the {@link CriteriaFormatter} used to format criteria fragments to database syntax (SQL).
   */
  public PostgreSqlFormatter(Orm orm, CriteriaFormatter criteriaFormatter) {

    super(orm, criteriaFormatter);
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  public PostgreSqlFormatter(Orm orm) {

    super(orm);
  }

}
