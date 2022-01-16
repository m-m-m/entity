package io.github.mmm.entity.bean.db.naming;

import io.github.mmm.base.text.CaseSyntax;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Interface to define the naming strategy to map {@link EntityBean}s to a database.
 *
 * @see #getColumnName(ReadableProperty)
 * @see #getTableName(EntityBean)
 * @since 1.0.0
 */
public interface DbNamingStrategy {

  /**
   * @param property the {@link ReadableProperty} to map to a database column.
   * @return the (logical) column name. Please note that the physical column name may still be derived from this result
   *         via {@link io.github.mmm.value.converter.TypeMapper#mapName(String) name mapping} and
   *         {@link io.github.mmm.value.converter.TypeMapper#next() decomposition}.
   */
  default String getColumnName(ReadableProperty<?> property) {

    return getColumnName(property.getName());
  }

  /**
   * @param propertyName the {@link ReadableProperty#getName() property name} to map to a database column.
   * @return the (logical) column name. Please note that the physical column name may still be derived from this result
   *         via {@link io.github.mmm.value.converter.TypeMapper#mapName(String) name mapping} and
   *         {@link io.github.mmm.value.converter.TypeMapper#next() decomposition}.
   */
  default String getColumnName(String propertyName) {

    return propertyName;
  }

  /**
   * @param bean the {@link EntityBean} to map to a database table.
   * @return the physical table name.
   */
  default String getTableName(EntityBean bean) {

    return getTableName(bean.getType().getStableName());
  }

  /**
   * @param beanName the {@link io.github.mmm.bean.BeanType#getStableName() entity name} to map to a database table.
   * @return the physical table name.
   */
  default String getTableName(String beanName) {

    return beanName;
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
