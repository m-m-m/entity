/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import java.util.Objects;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link Into}-{@link Clause} of an SQL {@link Statement} such as {@link io.github.mmm.entity.bean.sql.insert.Insert}
 * or {@link io.github.mmm.entity.bean.sql.upsert.Upsert}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class Into<E extends EntityBean, SELF extends Into<E, SELF>> extends AbstractEntityClause<E, SELF> {

  /** Name of {@link Into} for marshaling. */
  public static final String NAME_INTO = "into";

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  protected Into(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  protected Into(E entity, String entityName) {

    super(entity, entityName);
  }

  @Override
  protected String getMarshallingName() {

    return NAME_INTO;
  }

  /**
   * @param <V> type of the {@link PropertyPath#get() value}.
   * @param assignment the {@link PropertyAssignment} to set.
   * @return the {@link Values} for fluent API.
   */
  public abstract <V> Values<E, ?> values(PropertyAssignment<V> assignment);

  /**
   * @param <V> type of the {@link PropertyPath#get() value}.
   * @param assignments the {@link PropertyAssignment}s to set.
   * @return the {@link Values} for fluent API.
   */
  @SuppressWarnings("unchecked")
  public abstract <V> Values<E, ?> values(PropertyAssignment<V>... assignments);

  /**
   * Convenience method for
   * <code>{@link #values(PropertyAssignment) values}({@link PropertyAssignment}.{@link PropertyAssignment#of(PropertyPath, Object) of}(property, value)).</code>
   *
   * @param <V> type of the {@link PropertyPath#get() value}.
   * @param property the {@link PropertyPath property} to set.
   * @param value the {@link io.github.mmm.property.criteria.Literal} value to insert (assign the {@code property} to).
   * @return the {@link Values} for fluent API.
   */
  public abstract <V> Values<E, ?> values(PropertyPath<V> property, V value);

  /**
   * Sets all {@link EntityBean#getProperties() properties} of the {@link #getEntity() entity} that are not
   * {@code null}.
   *
   * @return the {@link Values} for fluent API.
   */
  public Values<E, ?> values() {

    Objects.requireNonNull(this.entity);
    Values<E, ?> values = null;
    for (WritableProperty<?> property : this.entity.getProperties()) {
      values = addValues(values, property);
    }
    if (values == null) {
      throw new IllegalStateException("Entity must not be empty!");
    }
    return values;
  }

  private <V> Values<E, ?> addValues(Values<E, ?> values, WritableProperty<V> property) {

    V value = property.get();
    if (value != null) {
      if (values == null) {
        values = values(property, value);
      } else {
        values = values.and(property, value);
      }
    }
    return values;
  }

}
