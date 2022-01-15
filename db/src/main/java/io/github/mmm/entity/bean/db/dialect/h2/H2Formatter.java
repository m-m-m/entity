package io.github.mmm.entity.bean.db.dialect.h2;

import io.github.mmm.entity.bean.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * {@link DbDialectStatementFormatter} for H2 Database.
 *
 * @since 1.0.0
 */
public class H2Formatter extends DbDialectStatementFormatter {

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   * @param criteriaFormatter the {@link CriteriaFormatter} used to format criteria fragments to database syntax (SQL).
   */
  public H2Formatter(TypeMapping typeMapping, CriteriaFormatter criteriaFormatter) {

    super(typeMapping, criteriaFormatter);
  }

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   */
  public H2Formatter(TypeMapping typeMapping) {

    super(typeMapping);
  }

}
