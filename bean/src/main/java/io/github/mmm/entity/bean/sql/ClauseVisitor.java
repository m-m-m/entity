/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.sql.create.CreateIndex;
import io.github.mmm.entity.bean.sql.create.CreateIndexColumns;
import io.github.mmm.entity.bean.sql.create.CreateTable;
import io.github.mmm.entity.bean.sql.create.CreateTableColumns;
import io.github.mmm.entity.bean.sql.delete.Delete;
import io.github.mmm.entity.bean.sql.insert.Insert;
import io.github.mmm.entity.bean.sql.merge.Merge;
import io.github.mmm.entity.bean.sql.select.GroupBy;
import io.github.mmm.entity.bean.sql.select.Having;
import io.github.mmm.entity.bean.sql.select.OrderBy;
import io.github.mmm.entity.bean.sql.select.Select;
import io.github.mmm.entity.bean.sql.update.Update;
import io.github.mmm.entity.bean.sql.upsert.Upsert;

/**
 * Interface for visitor on {@link Clause}. Override individual methods you are interested in. Typically do something
 * before and/or after delegating to parent method ({@code super.on...(...)}).
 *
 * @since 1.0.0
 */
public interface ClauseVisitor {

  /**
   * @param clause the {@link Clause} to visit.
   * @return this {@link ClauseVisitor} itself for fluent API calls.
   */
  default ClauseVisitor onClause(Clause clause) {

    if (clause instanceof StartClause) {
      onStart((StartClause) clause);
    } else if (clause instanceof MainClause) {
      onMainClause((MainClause<?>) clause);
    } else {
      onOtherClause(clause);
    }
    return this;
  }

  /**
   * @param clause the {@link Clause} that is neither a {@link StartClause} nor a {@link MainClause}.
   * @return this {@link ClauseVisitor} itself for fluent API calls.
   */
  default ClauseVisitor onOtherClause(Clause clause) {

    if (clause instanceof Into) {
      onInto((Into<?, ?>) clause);
    }
    return this;
  }

  /**
   * @param clause the {@link Clause} to visit.
   * @return this {@link ClauseVisitor} itself for fluent API calls.
   */
  default ClauseVisitor onMainClause(MainClause<?> clause) {

    if (clause instanceof From) {
      onFrom((From<?, ?>) clause);
    } else if (clause instanceof Where) {
      onWhere((Where<?, ?>) clause);
    } else if (clause instanceof GroupBy) {
      onGroupBy((GroupBy<?>) clause);
    } else if (clause instanceof Having) {
      onHaving((Having<?>) clause);
    } else if (clause instanceof OrderBy) {
      onOrderBy((OrderBy<?>) clause);
    } else if (clause instanceof Values) {
      onValues((Values<?, ?>) clause);
    } else if (clause instanceof Set) {
      onSet((Set<?, ?>) clause);
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
      onSelect((Select) start);
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
   * @param select the {@link Select}-{@link Clause} to visit.
   */
  default void onSelect(Select select) {

  }

  /**
   * @param update the {@link Update}-{@link Clause} to visit.
   */
  default void onUpdate(Update<?> update) {

  }

  /**
   * @param insert the {@link Insert}-{@link Clause} to visit.
   */
  default void onInsert(Insert insert) {

  }

  /**
   * @param delete the {@link Delete}-{@link Clause} to visit.
   */
  default void onDelete(Delete delete) {

  }

  /**
   * @param merge the {@link Merge}-{@link Clause} to visit.
   */
  default void onMerge(Merge merge) {

  }

  /**
   * @param upsert the {@link Upsert}-{@link Clause} to visit.
   */
  default void onUpsert(Upsert upsert) {

  }

  /**
   * @param createTable the {@link CreateTable}-{@link Clause} to visit.
   */
  default void onCreateTable(CreateTable<?> createTable) {

  }

  /**
   * @param createIndex the {@link CreateIndex}-{@link Clause} to visit.
   */
  default void onCreateIndex(CreateIndex createIndex) {

  }

  /**
   * @param from the {@link From}-{@link Clause} to visit.
   */
  default void onFrom(From<?, ?> from) {

  }

  /**
   * @param where the {@link Where}-{@link Clause} to visit.
   */
  default void onWhere(Where<?, ?> where) {

  }

  /**
   * @param groupBy the {@link GroupBy}-{@link Clause} to visit.
   */
  default void onGroupBy(GroupBy<?> groupBy) {

  }

  /**
   * @param having the {@link Having}-{@link Clause} to visit.
   */
  default void onHaving(Having<?> having) {

  }

  /**
   * @param orderBy the {@link OrderBy}-{@link Clause} to visit.
   */
  default void onOrderBy(OrderBy<?> orderBy) {

  }

  /**
   * @param into the {@link Into}-{@link Clause} to visit.
   */
  default void onInto(Into<?, ?> into) {

  }

  /**
   * @param values the {@link Values}-{@link Clause} to visit.
   */
  default void onValues(Values<?, ?> values) {

  }

  /**
   * @param columns the {@link CreateTableColumns}-{@link Clause} to visit.
   */
  default void onColumns(CreateTableColumns<?> columns) {

  }

  /**
   * @param set the {@link Set}-{@link Clause} to visit.
   */
  default void onSet(Set<?, ?> set) {

  }

  /**
   * @param columns the {@link CreateIndexColumns}-{@link Clause} to visit.
   */
  default void onColumns(CreateIndexColumns<?> columns) {

  }

}
