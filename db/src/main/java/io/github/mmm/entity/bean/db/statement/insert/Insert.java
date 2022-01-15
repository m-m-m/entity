/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.insert;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractClause;
import io.github.mmm.entity.bean.db.statement.StartClause;

/**
 * {@link StartClause} to insert data into the database.
 *
 * @since 1.0.0
 */
public final class Insert extends AbstractClause implements StartClause {

  /** Name of {@link Insert} for marshaling. */
  public static final String NAME_INSERT = "insert";

  /**
   * The constructor.
   */
  public Insert() {

    super();
  }

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean entity} to insert into.
   * @return the {@link InsertInto} for fluent API calls.
   */
  public <E extends EntityBean> InsertInto<E> into(E entity) {

    return new InsertInto<>(this, entity);
  }

  @Override
  protected String getMarshallingName() {

    return NAME_INSERT;
  }
}
