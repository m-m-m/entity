package io.github.mmm.entity.bean.db.naming;

import io.github.mmm.base.text.CaseSyntax;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Interface to define the naming strategy to map {@link EntityBean}s to a database.
 *
 * @see #getColumnName(String)
 * @see #getTableName(EntityBean)
 * @since 1.0.0
 */
public interface DbNamingStrategy {

  /**
   * @param rawColumnName the raw column name to map. May be the {@link ReadableProperty#getName() property name} or
   *        {@link io.github.mmm.value.converter.TypeMapper#mapName(String) remapped and decomposed} from it.
   * @return the final column name.
   */
  default String getColumnName(String rawColumnName) {

    return rawColumnName;
  }

  /**
   * @param bean the {@link EntityBean} to map to a database table.
   * @return the physical table name.
   */
  default String getTableName(EntityBean bean) {

    return getTableName(bean.getType().getStableName());
  }

  /**
   * @param rawTableName the raw table name to map. May be the {@link io.github.mmm.bean.BeanType#getStableName() stable
   *        entity name}.
   * @return the database table name.
   */
  default String getTableName(String rawTableName) {

    return rawTableName;
  }

  /**
   * @return the default {@link DbNamingStrategy}.
   */
  static DbNamingStrategy of() {

    return DbNamingStrategyDefault.INSTANCE;
  }

  /**
   * @param caseSyntax the {@link CaseSyntax} to use.
   * @return the {@link DbNamingStrategy} converting to the given {@link CaseSyntax}.
   */
  static DbNamingStrategy of(CaseSyntax caseSyntax) {

    return DbNamingStrategyCaseSyntax.of(caseSyntax);
  }

  /**
   * @return the {@link DbNamingStrategy} for legacy
   *         <a href="https://en.wikipedia.org/wiki/Relational_database">RDBMS</a>.
   */
  static DbNamingStrategy ofRdbms() {

    return of(CaseSyntax.UPPER_SNAKE_CASE);
  }
}
