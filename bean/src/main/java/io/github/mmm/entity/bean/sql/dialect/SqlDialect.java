package io.github.mmm.entity.bean.sql.dialect;

import io.github.mmm.entity.bean.sql.SqlFormatter;
import io.github.mmm.entity.bean.typemapping.TypeMapping;

/**
 * Interface for an {@link SqlDialect}.
 */
public interface SqlDialect {

  /**
   * @return the name of the {@link SqlDialect} (e.g. "h2", "postgresql", etc.). Should be entirely lower-case to
   *         prevent case mismatching.
   */
  String getName();

  /**
   * @return the {@link TypeMapping} instance.
   */
  TypeMapping getTypeMapper();

  /**
   * @return a new {@link SqlFormatter} using this SQL dialect.
   */
  SqlFormatter createFormatter();

}
