/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.merge;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AliasMap;
import io.github.mmm.entity.bean.db.statement.IntoClause;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link IntoClause} of an {@link MergeStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class MergeInto<E extends EntityBean> extends IntoClause<E, MergeInto<E>> {

  private final MergeStatement<E> statement;

  /**
   * The constructor.
   *
   * @param merge the opening {@link Merge}.
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public MergeInto(Merge merge, E entity) {

    this(merge, entity, null);
  }

  /**
   * The constructor.
   *
   * @param merge the opening {@link Merge}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public MergeInto(Merge merge, E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new MergeStatement<>(merge, this);
  }

  @Override
  public <V> MergeValues<E> values(PropertyAssignment<V> assignment) {

    MergeValues<E> values = this.statement.getValues();
    values.and(assignment);
    return values;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V> MergeValues<E> values(PropertyAssignment<V>... assignments) {

    MergeValues<E> values = this.statement.getValues();
    values.and(assignments);
    return values;
  }

  @Override
  public <V> MergeValues<E> values(PropertyPath<V> property, V value) {

    return values(PropertyAssignment.of(property, value));
  }

  @SuppressWarnings("unchecked")
  @Override
  public MergeValues<E> values() {

    return (MergeValues<E>) super.values();
  }

  @Override
  // make visible
  protected AliasMap getAliasMap() {

    return super.getAliasMap();
  }

}
