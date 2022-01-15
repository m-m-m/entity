/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import java.util.List;

import io.github.mmm.marshall.MarshallingObject;

/**
 * A complete SQL statement that may be executed to the database.
 *
 * @param <E> type of the entity or object this statement primarily operates on.
 * @since 1.0.0
 */
public abstract interface DbStatement<E> extends MarshallingObject {

  /**
   * @return the {@link StartClause}
   */
  StartClause getStart();

  /**
   * @return the {@link List} of {@link DbClause}s this {@link DbStatement} is composed of. Please note that this is a
   *         generic API. Specific sub-classes implementing {@link DbStatement} will have dedicated getters for each type
   *         of {@link DbClause}.
   */
  List<DbClause> getClauses();

}
