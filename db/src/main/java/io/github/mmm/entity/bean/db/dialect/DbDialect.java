package io.github.mmm.entity.bean.db.dialect;

import io.github.mmm.entity.bean.db.statement.DbStatementFormatter;

/**
 * Interface for an database dialect. It abstracts from the concrete syntax (e.g. specific SQL) of a database.
 */
public interface DbDialect {

  /**
   * @return the name of the {@link DbDialect} (e.g. "h2", "postgresql", etc.). Should be entirely lower-case to prevent
   *         case mismatching.
   */
  String getName();

  /**
   * @return a new {@link DbStatementFormatter} using this SQL dialect.
   */
  DbStatementFormatter createFormatter();

}
