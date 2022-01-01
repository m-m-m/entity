package io.github.mmm.entity.bean.sql.dialect;

import io.github.mmm.entity.bean.sql.SqlFormatter;
import io.github.mmm.property.criteria.CriteriaSqlFormatter;

/**
 * Abstract base class of {@link SqlFormatter} for any real SQL dialect.
 */
public abstract class SqlDialectFormatter extends SqlFormatter {

  /**
   * The constructor.
   */
  public SqlDialectFormatter() {

    super(CriteriaSqlFormatter.ofIndexedParameters());
  }

  /**
   * The constructor.
   *
   * @param criteriaFormatter the {@link CriteriaSqlFormatter} used to format criteria fragments to SQL.
   */
  public SqlDialectFormatter(CriteriaSqlFormatter criteriaFormatter) {

    super(criteriaFormatter);
  }

}
