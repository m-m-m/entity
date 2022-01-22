/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.entity.bean.db.statement.create.CreateIndex;
import io.github.mmm.entity.bean.db.statement.create.CreateIndexColumns;
import io.github.mmm.entity.bean.db.statement.create.CreateTable;
import io.github.mmm.entity.bean.db.statement.create.CreateTableColumns;
import io.github.mmm.entity.bean.db.statement.delete.Delete;
import io.github.mmm.entity.bean.db.statement.insert.Insert;
import io.github.mmm.entity.bean.db.statement.merge.Merge;
import io.github.mmm.entity.bean.db.statement.select.GroupBy;
import io.github.mmm.entity.bean.db.statement.select.Having;
import io.github.mmm.entity.bean.db.statement.select.OrderBy;
import io.github.mmm.entity.bean.db.statement.select.Select;
import io.github.mmm.entity.bean.db.statement.update.Update;
import io.github.mmm.entity.bean.db.statement.upsert.Upsert;

/**
 * Interface for visitor on {@link DbClause}. Override individual methods you are interested in. Typically do something
 * before and/or after delegating to parent method ({@code super.on...(...)}).
 *
 * @since 1.0.0
 */
public interface DbClauseVisitor {

  /**
   * @param clause the {@link DbClause} to visit.
   * @return this {@link DbClauseVisitor} itself for fluent API calls.
   */
  default DbClauseVisitor onClause(DbClause clause) {

    if (clause instanceof StartClause) {
      onStart((StartClause) clause);
    } else if (clause instanceof MainDbClause) {
      onMainClause((MainDbClause<?>) clause);
    } else {
      onOtherClause(clause);
    }
    return this;
  }

  /**
   * @param clause the {@link DbClause} that is neither a {@link StartClause} nor a {@link MainDbClause}.
   * @return this {@link DbClauseVisitor} itself for fluent API calls.
   */
  default DbClauseVisitor onOtherClause(DbClause clause) {

    if (clause instanceof IntoClause) {
      onInto((IntoClause<?, ?>) clause);
    }
    return this;
  }

  /**
   * @param clause the {@link DbClause} to visit.
   * @return this {@link DbClauseVisitor} itself for fluent API calls.
   */
  default DbClauseVisitor onMainClause(MainDbClause<?> clause) {

    if (clause instanceof FromClause) {
      onFrom((FromClause<?, ?, ?>) clause);
    } else if (clause instanceof WhereClause) {
      onWhere((WhereClause<?, ?>) clause);
    } else if (clause instanceof GroupBy) {
      onGroupBy((GroupBy<?>) clause);
    } else if (clause instanceof Having) {
      onHaving((Having<?>) clause);
    } else if (clause instanceof OrderBy) {
      onOrderBy((OrderBy<?>) clause);
    } else if (clause instanceof ValuesClause) {
      onValues((ValuesClause<?, ?>) clause);
    } else if (clause instanceof SetClause) {
      onSet((SetClause<?, ?>) clause);
    } else if (clause instanceof CreateTableColumns) {
      onColumns((CreateTableColumns<?>) clause);
    } else if (clause instanceof CreateIndexColumns) {
      onColumns((CreateIndexColumns<?>) clause);
    }
    return this;
  }

  /**
   * @param start the {@link StartClause} to visit.
   */
  default void onStart(StartClause start) {

    if (start instanceof Select) {
      onSelect((Select<?>) start);
    } else if (start instanceof Update) {
      onUpdate((Update<?>) start);
    } else if (start instanceof Insert) {
      onInsert((Insert) start);
    } else if (start instanceof Delete) {
      onDelete((Delete) start);
    } else if (start instanceof Merge) {
      onMerge((Merge) start);
    } else if (start instanceof Upsert) {
      onUpsert((Upsert) start);
    } else if (start instanceof CreateTable) {
      onCreateTable((CreateTable<?>) start);
    } else if (start instanceof CreateIndex) {
      onCreateIndex((CreateIndex) start);
    }
  }

  /**
   * @param select the {@link Select}-{@link DbClause} to visit.
   */
  default void onSelect(Select<?> select) {

  }

  /**
   * @param update the {@link Update}-{@link DbClause} to visit.
   */
  default void onUpdate(Update<?> update) {

  }

  /**
   * @param insert the {@link Insert}-{@link DbClause} to visit.
   */
  default void onInsert(Insert insert) {

  }

  /**
   * @param delete the {@link Delete}-{@link DbClause} to visit.
   */
  default void onDelete(Delete delete) {

  }

  /**
   * @param merge the {@link Merge}-{@link DbClause} to visit.
   */
  default void onMerge(Merge merge) {

  }

  /**
   * @param upsert the {@link Upsert}-{@link DbClause} to visit.
   */
  default void onUpsert(Upsert upsert) {

  }

  /**
   * @param createTable the {@link CreateTable}-{@link DbClause} to visit.
   */
  default void onCreateTable(CreateTable<?> createTable) {

  }

  /**
   * @param createIndex the {@link CreateIndex}-{@link DbClause} to visit.
   */
  default void onCreateIndex(CreateIndex createIndex) {

  }

  /**
   * @param from the {@link FromClause}-{@link DbClause} to visit.
   */
  default void onFrom(FromClause<?, ?, ?> from) {

  }

  /**
   * @param where the {@link WhereClause}-{@link DbClause} to visit.
   */
  default void onWhere(WhereClause<?, ?> where) {

  }

  /**
   * @param groupBy the {@link GroupBy}-{@link DbClause} to visit.
   */
  default void onGroupBy(GroupBy<?> groupBy) {

  }

  /**
   * @param having the {@link Having}-{@link DbClause} to visit.
   */
  default void onHaving(Having<?> having) {

  }

  /**
   * @param orderBy the {@link OrderBy}-{@link DbClause} to visit.
   */
  default void onOrderBy(OrderBy<?> orderBy) {

  }

  /**
   * @param into the {@link IntoClause}-{@link DbClause} to visit.
   */
  default void onInto(IntoClause<?, ?> into) {

  }

  /**
   * @param values the {@link ValuesClause}-{@link DbClause} to visit.
   */
  default void onValues(ValuesClause<?, ?> values) {

  }

  /**
   * @param columns the {@link CreateTableColumns}-{@link DbClause} to visit.
   */
  default void onColumns(CreateTableColumns<?> columns) {

  }

  /**
   * @param set the {@link SetClause}-{@link DbClause} to visit.
   */
  default void onSet(SetClause<?, ?> set) {

  }

  /**
   * @param columns the {@link CreateIndexColumns}-{@link DbClause} to visit.
   */
  default void onColumns(CreateIndexColumns<?> columns) {

  }

}
