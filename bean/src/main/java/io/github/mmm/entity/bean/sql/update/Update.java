/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.update;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.AbstractEntityClause;
import io.github.mmm.entity.bean.sql.MainClause;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;

/**
 * {@link StartClause} of an UpdateStatement to update data in the database.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public final class Update<E extends EntityBean> extends AbstractEntityClause<E, Update<E>>
    implements StartClause, MainClause<E> {

  /** Name of {@link Update} for marshaling. */
  public static final String NAME_UPDATE = "update";

  private final UpdateStatement<E> statement;

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public Update(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public Update(E entity, String entityName) {

    super(entity, entityName);
    this.statement = new UpdateStatement<>(this);
  }

  /**
   * @param <V> type of the {@link PropertyPath#get() value}.
   * @param assignment the {@link PropertyAssignment} to set.
   * @return the {@link UpdateSet} for fluent API.
   */
  public <V> UpdateSet<E> set(PropertyAssignment<V> assignment) {

    UpdateSet<E> set = this.statement.getSet();
    set.and(assignment);
    return set;
  }

  /**
   * @param <V> type of the {@link PropertyPath#get() value}.
   * @param assignments the {@link PropertyAssignment}s to set.
   * @return the {@link UpdateSet} for fluent API.
   */
  @SuppressWarnings("unchecked")
  public <V> UpdateSet<E> set(PropertyAssignment<V>... assignments) {

    UpdateSet<E> set = this.statement.getSet();
    set.and(assignments);
    return set;
  }

  /**
   * Convenience method for
   * <code>{@link #set(PropertyAssignment) set}({@link PropertyAssignment}.{@link PropertyAssignment#of(PropertyPath, Object) of}(property, value)).</code>
   *
   * @param <V> type of the {@link PropertyPath#get() value}.
   * @param property the {@link PropertyPath property} to set.
   * @param value the {@link io.github.mmm.property.criteria.Literal} value to set (assign the {@code property} to).
   * @return the {@link UpdateSet} for fluent API.
   */
  public <V> UpdateSet<E> set(PropertyPath<V> property, V value) {

    return set(PropertyAssignment.of(property, value));
  }

  @Override
  protected String getMarshallingName() {

    return NAME_UPDATE;
  }

  @Override
  public UpdateStatement<E> get() {

    return this.statement;
  }
}
