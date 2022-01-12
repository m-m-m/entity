package io.github.mmm.entity.bean.sql.dialect.h2;

import io.github.mmm.entity.bean.sql.dialect.SqlDialectFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaSqlFormatter;

/**
 * {@link SqlDialectFormatter} for H2 Database.
 *
 * @since 1.0.0
 */
public class H2SqlFormatter extends SqlDialectFormatter {

  /**
   * The constructor.
   *
   * @param typeMapper
   * @param criteriaFormatter
   */
  public H2SqlFormatter(TypeMapping typeMapper, CriteriaSqlFormatter criteriaFormatter) {

    super(typeMapper, criteriaFormatter);
  }

  /**
   * The constructor.
   *
   * @param typeMapper
   */
  public H2SqlFormatter(TypeMapping typeMapper) {

    super(typeMapper);
  }

}
