/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.dialect;

import io.github.mmm.entity.bean.db.orm.Orm;
import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * Abstract base class of {@link DbStatementFormatter} for any real {@link DbDialect database dialect}.
 */
public abstract class DbDialectStatementFormatter extends DbStatementFormatter {

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  public DbDialectStatementFormatter(Orm orm) {

    super(orm, CriteriaFormatter.ofIndexedParameters());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   * @param criteriaFormatter the {@link CriteriaFormatter} used to format criteria fragments to database syntax (e.g.
   *        SQL).
   */
  public DbDialectStatementFormatter(Orm orm, CriteriaFormatter criteriaFormatter) {

    super(orm, criteriaFormatter);
  }

}
