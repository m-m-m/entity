package io.github.mmm.entity.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.entity.bean.sql.dialect.SqlDialect;
import io.github.mmm.entity.bean.sql.dialect.SqlDialectProvider;

/**
 * Implementation of {@link SqlDialectProvider}.
 *
 * @since 1.0.0
 */
public class SqlDialectProviderImpl implements SqlDialectProvider {

  /** The singleton instance. */
  public static final SqlDialectProvider INSTANCE = new SqlDialectProviderImpl();

  private static final Logger LOG = LoggerFactory.getLogger(SqlDialectProviderImpl.class);

  private final Map<String, SqlDialect> dialects;

  /**
   * The constructor.
   */
  public SqlDialectProviderImpl() {

    super();
    this.dialects = new HashMap<>();
    ServiceLoader<SqlDialect> loader = ServiceLoader.load(SqlDialect.class);
    for (SqlDialect dialect : loader) {
      SqlDialect duplicate = this.dialects.put(dialect.getName(), dialect);
      if (duplicate != null) {
        LOG.info("Overriding dialect " + dialect.getName() + " from " + duplicate.getClass().getName() + " to "
            + dialect.getClass().getName());
      }
    }
  }

  @Override
  public SqlDialect get(String name) {

    SqlDialect dialect = this.dialects.get(name);
    if (dialect == null) {
      throw new ObjectNotFoundException("SqlDialect", name);
    }
    return dialect;
  }

  @Override
  public boolean has(String name) {

    return this.dialects.containsKey(name);
  }

}
