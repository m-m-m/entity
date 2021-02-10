/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.insert;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.Clause;
import io.github.mmm.entity.bean.sql.Into;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link Into}-{@link Clause} of an SQL {@link InsertStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class InsertInto<E extends EntityBean> extends Into<E, InsertInto<E>> {

  private final InsertStatement<E> statement;

  /**
   * The constructor.
   *
   * @param insert the opening {@link Insert}.
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public InsertInto(Insert insert, E entity) {

    this(insert, entity, null);
  }

  /**
   * The constructor.
   *
   * @param insert the opening {@link Insert}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public InsertInto(Insert insert, E entity, String entityName) {

    super(entity, entityName);
    this.statement = new InsertStatement<>(insert, this);
  }

  @Override
  public <V> InsertValues<E> values(PropertyAssignment<V> assignment) {

    InsertValues<E> values = this.statement.getValues();
    values.and(assignment);
    return values;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V> InsertValues<E> values(PropertyAssignment<V>... assignments) {

    InsertValues<E> values = this.statement.getValues();
    values.and(assignments);
    return values;
  }

  @Override
  public <V> InsertValues<E> values(PropertyPath<V> property, V value) {

    return values(PropertyAssignment.of(property, value));
  }

  @SuppressWarnings("unchecked")
  @Override
  public InsertValues<E> values() {

    return (InsertValues<E>) super.values();
  }

}
