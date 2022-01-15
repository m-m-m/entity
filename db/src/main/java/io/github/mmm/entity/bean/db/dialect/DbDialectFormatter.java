package io.github.mmm.entity.bean.db.dialect;

import io.github.mmm.entity.bean.db.statement.StatementFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaFormatter;

/**
 * Abstract base class of {@link StatementFormatter} for any real {@link DbDialect database dialect}.
 */
public abstract class DbDialectFormatter extends StatementFormatter {

  /**
   * The constructor.
   *
   * @param typeMapper the {@link TypeMapping}.
   */
  public DbDialectFormatter(TypeMapping typeMapper) {

    super(typeMapper, CriteriaFormatter.ofIndexedParameters());
  }

  /**
   * The constructor.
   *
   * @param typeMapper the {@link TypeMapping}.
   * @param criteriaFormatter the {@link CriteriaFormatter} used to format criteria fragments to database syntax (e.g.
   *        SQL).
   */
  public DbDialectFormatter(TypeMapping typeMapper, CriteriaFormatter criteriaFormatter) {

    super(typeMapper, criteriaFormatter);
  }

}
