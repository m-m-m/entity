package io.github.mmm.entity.bean.db;

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

  default String getColumnName(ReadableProperty<?> property) {

    return property.getName();
  }

  default String getTableName(EntityBean bean) {

    return bean.getType().getStableName();
  }

}
