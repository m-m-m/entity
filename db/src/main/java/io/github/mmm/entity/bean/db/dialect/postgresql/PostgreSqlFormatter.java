package io.github.mmm.entity.bean.db.dialect.postgresql;

import io.github.mmm.entity.bean.db.dialect.DbDialectFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * {@link DbDialectFormatter} for PostgreSQL Database.
 *
 * @since 1.0.0
 */
public class PostgreSqlFormatter extends DbDialectFormatter {

  /**
   * The constructor.
   *
   * @param typeMapper
   * @param criteriaFormatter
   */
  public PostgreSqlFormatter(TypeMapping typeMapper, CriteriaFormatter criteriaFormatter) {

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
