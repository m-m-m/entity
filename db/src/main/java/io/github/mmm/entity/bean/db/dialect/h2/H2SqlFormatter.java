package io.github.mmm.entity.bean.db.dialect.h2;

import io.github.mmm.entity.bean.db.dialect.DbDialectFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * {@link DbDialectFormatter} for H2 Database.
 *
 * @since 1.0.0
 */
public class H2SqlFormatter extends DbDialectFormatter {

  /**
   * The constructor.
   *
   * @param typeMapper
   * @param criteriaFormatter
   */
  public H2SqlFormatter(TypeMapping typeMapper, CriteriaFormatter criteriaFormatter) {

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
