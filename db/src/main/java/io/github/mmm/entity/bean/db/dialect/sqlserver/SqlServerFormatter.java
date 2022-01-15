package io.github.mmm.entity.bean.db.dialect.sqlserver;

import io.github.mmm.entity.bean.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * {@link DbDialectStatementFormatter} for <a href="https://docs.microsoft.com/en-us/sql/">MS SQL Server</a>.
 *
 * @since 1.0.0
 */
public class SqlServerFormatter extends DbDialectStatementFormatter {

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   * @param criteriaFormatter the {@link CriteriaFormatter} used to format criteria fragments to database syntax (SQL).
   */
  public SqlServerFormatter(TypeMapping typeMapping, CriteriaFormatter criteriaFormatter) {

    super(typeMapping, criteriaFormatter);
  }

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   */
  public SqlServerFormatter(TypeMapping typeMapping) {

    super(typeMapping);
  }

}
