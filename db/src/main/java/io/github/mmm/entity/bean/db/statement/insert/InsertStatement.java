/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.insert;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractDbClause;
import io.github.mmm.entity.bean.db.statement.AbstractDbStatement;
import io.github.mmm.entity.bean.db.statement.AliasMap;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.DbStatement;

/**
 * {@link DbStatement} to {@link Insert insert} data into the database.
 *
 * @param <E> type of the {@link InsertInto#getEntity() entity}.
 * @since 1.0.0
 */
// TODO: support sub-query as alternative to values
// INSERT INTO table4 ( name, age, sex, city, id, number, nationality)
// SELECT name, age, sex, city, p.id, number, n.nationality
// FROM table1 p
// INNER JOIN table2 c ON c.Id = p.Id
// INNER JOIN table3 n ON p.Id = n.Id
public class InsertStatement<E extends EntityBean> extends AbstractDbStatement<E> {

  private final Insert insert;

  private final InsertInto<E> into;

  private final InsertValues<E> values;

  /**
   * The constructor.
   *
   * @param insert the {@link #getInsert() insert}.
   * @param into the #getInto
   */
  public InsertStatement(Insert insert, InsertInto<E> into) {

    super();
    this.insert = insert;
    this.into = into;
    this.values = new InsertValues<>(this);
  }

  /**
   * @deprecated use {@link #getInsert()} to make it more explicit.
   */
  @Deprecated
  @Override
  public Insert getStart() {

    return this.insert;
  }

  /**
   * @return the opening {@link Insert}-{@link DbClause}.
   */
  public Insert getInsert() {

    return this.insert;
  }

  /**
   * @return the {@link InsertInto Into}-{@link DbClause}.
   */
  public InsertInto<E> getInto() {

    return this.into;
  }

  /**
   * @return the {@link InsertValues Values}-{@link DbClause} or {@code null} if none was added.
   */
  public InsertValues<E> getValues() {

    return this.values;
  }

  @Override
  protected void addClauses(List<AbstractDbClause> list) {

    list.add(this.insert);
    list.add(this.into);
    list.add(this.values);
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.into.getAliasMap();
  }

}
