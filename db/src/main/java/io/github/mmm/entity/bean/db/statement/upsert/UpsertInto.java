/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.upsert;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AliasMap;
import io.github.mmm.entity.bean.db.statement.IntoClause;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link IntoClause} of an {@link UpsertStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class UpsertInto<E extends EntityBean> extends IntoClause<E, UpsertInto<E>> {

  private final UpsertStatement<E> statement;

  /**
   * The constructor.
   *
   * @param upset the opening {@link Upsert}.
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public UpsertInto(Upsert upset, E entity) {

    this(upset, entity, null);
  }

  /**
   * The constructor.
   *
   * @param upsert the opening {@link Upsert}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public UpsertInto(Upsert upsert, E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new UpsertStatement<>(upsert, this);
  }

  @Override
  public <V> UpsertValues<E> values(PropertyAssignment<V> assignment) {

    UpsertValues<E> values = this.statement.getValues();
    values.and(assignment);
    return values;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V> UpsertValues<E> values(PropertyAssignment<V>... assignments) {

    UpsertValues<E> values = this.statement.getValues();
    values.and(assignments);
    return values;
  }

  @Override
  public <V> UpsertValues<E> values(PropertyPath<V> property, V value) {

    return values(PropertyAssignment.of(property, value));
  }

  @SuppressWarnings("unchecked")
  @Override
  public UpsertValues<E> values() {

    return (UpsertValues<E>) super.values();
  }

}
