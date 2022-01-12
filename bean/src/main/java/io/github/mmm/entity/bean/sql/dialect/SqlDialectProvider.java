package io.github.mmm.entity.bean.sql.dialect;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.entity.impl.SqlDialectProviderImpl;

/**
 * Provider for {@link SqlDialect}s.
 *
 * @since 1.0.0
 */
public interface SqlDialectProvider {

  /**
   * @param name the {@link SqlDialect#getName() name} of the requested {@link SqlDialect}.
   * @return the requested {@link SqlDialect}.
   * @throws ObjectNotFoundException if no {@link SqlDialect} could be found for the given {@code name}.
   */
  SqlDialect get(String name);

  /**
   * @param name the {@link SqlDialect#getName() name} of the {@link SqlDialect} to find.
   * @return {@code true} if the {@link SqlDialect} with the given {@link SqlDialect#getName() name} is present,
   *         {@code false} otherwise.
   */
  boolean has(String name);

  /**
   * @return the singleton instance of this {@link SqlDialectProvider}.
   */
  static SqlDialectProvider get() {

    return SqlDialectProviderImpl.INSTANCE;
  }

}
