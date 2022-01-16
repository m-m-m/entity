package io.github.mmm.entity.bean.db.dialect;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.entity.db.impl.DbDialectProviderImpl;

/**
 * Provider for {@link DbDialect}s.
 *
 * @since 1.0.0
 */
public interface DbDialectProvider {

  /**
   * @param name the {@link DbDialect#getName() name} of the requested {@link DbDialect}.
   * @return the requested {@link DbDialect}.
   * @throws ObjectNotFoundException if no {@link DbDialect} could be found for the given {@code name}.
   */
  DbDialect get(String name);

  /**
   * @param name the {@link DbDialect#getName() name} of the {@link DbDialect} to find.
   * @return {@code true} if the {@link DbDialect} with the given {@link DbDialect#getName() name} is present,
   *         {@code false} otherwise.
   */
  boolean has(String name);

  /**
   * @return the singleton instance of this {@link DbDialectProvider}.
   */
  static DbDialectProvider get() {

    return DbDialectProviderImpl.INSTANCE;
  }

}
