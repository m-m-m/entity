package io.github.mmm.entity.bean.sql.dialect;

import io.github.mmm.entity.bean.sql.SqlFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaSqlFormatter;

/**
 * Abstract base class of {@link SqlFormatter} for any real SQL dialect.
 */
public abstract class SqlDialectFormatter extends SqlFormatter {

  /**
   * The constructor.
   *
   * @param typeMapper the {@link TypeMapping}.
   */
  public SqlDialectFormatter(TypeMapping typeMapper) {

    super(typeMapper, CriteriaSqlFormatter.ofIndexedParameters());
  }

  /**
   * The constructor.
   *
   * @param typeMapper the {@link TypeMapping}.
   * @param criteriaFormatter the {@link CriteriaSqlFormatter} used to format criteria fragments to SQL.
   */
  public SqlDialectFormatter(TypeMapping typeMapper, CriteriaSqlFormatter criteriaFormatter) {

    super(typeMapper, criteriaFormatter);
  }

}
