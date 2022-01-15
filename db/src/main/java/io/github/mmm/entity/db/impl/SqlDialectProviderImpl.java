package io.github.mmm.entity.db.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.db.dialect.DbDialectProvider;

/**
 * Implementation of {@link DbDialectProvider}.
 *
 * @since 1.0.0
 */
public class SqlDialectProviderImpl implements DbDialectProvider {

  /** The singleton instance. */
  public static final DbDialectProvider INSTANCE = new SqlDialectProviderImpl();

  private static final Logger LOG = LoggerFactory.getLogger(SqlDialectProviderImpl.class);

  private final Map<String, DbDialect> dialects;

  /**
   * The constructor.
   */
  public SqlDialectProviderImpl() {

    super();
    this.dialects = new HashMap<>();
    ServiceLoader<DbDialect> loader = ServiceLoader.load(DbDialect.class);
    for (DbDialect dialect : loader) {
      DbDialect duplicate = this.dialects.put(dialect.getName(), dialect);
      if (duplicate != null) {
        LOG.info("Overriding dialect " + dialect.getName() + " from " + duplicate.getClass().getName() + " to "
            + dialect.getClass().getName());
      }
    }
  }

  @Override
  public DbDialect get(String name) {

    DbDialect dialect = this.dialects.get(name);
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
