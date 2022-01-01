package io.github.mmm.entity.bean.sql;

/**
 * Interface for an {@link SqlDialect}.
 */
public interface SqlDialect {

  /**
   * @return a new {@link SqlFormatter} using this SQL dialect.
   */
  SqlFormatter createFormatter();

}
