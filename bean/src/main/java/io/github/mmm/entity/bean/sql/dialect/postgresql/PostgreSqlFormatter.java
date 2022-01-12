package io.github.mmm.entity.bean.sql.dialect.postgresql;

import io.github.mmm.entity.bean.sql.dialect.SqlDialectFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaSqlFormatter;

/**
 * {@link SqlDialectFormatter} for PostgreSQL Database.
 *
 * @since 1.0.0
 */
public class PostgreSqlFormatter extends SqlDialectFormatter {

  /**
   * The constructor.
   *
   * @param typeMapper
   * @param criteriaFormatter
   */
  public PostgreSqlFormatter(TypeMapping typeMapper, CriteriaSqlFormatter criteriaFormatter) {

    super(typeMapper, criteriaFormatter);
  }

  /**
   * The constructor.
   *
   * @param typeMapper
   */
  public PostgreSqlFormatter(TypeMapping typeMapper) {

    super(typeMapper);
  }

}
