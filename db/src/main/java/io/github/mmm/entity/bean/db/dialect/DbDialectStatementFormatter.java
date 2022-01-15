package io.github.mmm.entity.bean.db.dialect;

import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * Abstract base class of {@link DbStatementFormatter} for any real {@link DbDialect database dialect}.
 */
public abstract class DbDialectStatementFormatter extends DbStatementFormatter {

  /**
   * The constructor.
   *
   * @param typeMapper the {@link TypeMapping}.
   */
  public DbDialectStatementFormatter(TypeMapping typeMapper) {

    super(typeMapper, CriteriaFormatter.ofIndexedParameters());
  }

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   * @param criteriaFormatter the {@link CriteriaFormatter} used to format criteria fragments to database syntax (e.g.
   *        SQL).
   */
  public DbDialectStatementFormatter(TypeMapping typeMapping, CriteriaFormatter criteriaFormatter) {

    super(typeMapping, criteriaFormatter);
  }

}
