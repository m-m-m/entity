/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import java.util.Objects;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.property.criteria.CriteriaAggregation;
import io.github.mmm.property.criteria.ProjectionProperty;
import io.github.mmm.value.PropertyPath;

/**
 * {@link StartClause} of a {@link SelectStatement} to query data from the database. Such projection is what is called a
 * <em>constructor query</em> in JPA such as
 *
 * <pre>
 * SELECT new com.example.Result(sum(i.price), count(i.price)) FROM Item AS i GROUP BY i.order
 * </pre>
 *
 * As analogy we can create {@code Result} as {@link WritableBean}:
 *
 * <pre>
 * public interface Result extends {@link WritableBean} {
 *   {@link io.github.mmm.property.number.bigdecimal.BigDecimalProperty} Total();
 *   {@link io.github.mmm.property.number.integers.IntegerProperty} Count();
 *   static Result of() {
 *     return {@link io.github.mmm.bean.BeanFactory#get()}.{@link io.github.mmm.bean.BeanFactory#create(Class) create}(Result.class);
 *   }
 * }
 * </pre>
 *
 * Now we can create our query as following:
 *
 * <pre>
 * Result result = Result.of();
 * Item item = Item.of();
 * new {@link SelectProjection}<>(result, item.Price().count())
 * </pre>
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public final class SelectProjection<R extends WritableBean> extends Select<R> {

  /**
   * The constructor.
   *
   * @param resultBean the {@link #getResultBean() result bean} to project to (via "constructor query").
   */
  public SelectProjection(R resultBean) {

    super(resultBean);
  }

  /**
   * The constructor.
   *
   * @param <V> type of the type of the {@link PropertyPath#get() property value}.
   * @param resultBean the {@link #getResultBean() result bean} to project to (via "constructor query").
   * @param selection the {@link PropertyPath} to add to the selection.
   * @param property the {@link ProjectionProperty#getProperty() projection property} to map to.
   */
  public <V> SelectProjection(R resultBean, PropertyPath<V> selection, PropertyPath<V> property) {

    this(resultBean);
    and(selection, property);
  }

  /**
   * The constructor.
   *
   * @param <V> type of the type of the {@link PropertyPath#get() property value}.
   * @param resultBean the {@link #getResultBean() result bean} to project to (via "constructor query").
   * @param selection the {@link CriteriaAggregation} to add to the selection.
   * @param property the {@link ProjectionProperty#getProperty() projection property} to map to.
   */
  public <V extends Number> SelectProjection(R resultBean, CriteriaAggregation<V> selection, PropertyPath<V> property) {

    this(resultBean);
    and(selection, property);
  }

  /**
   * @param <V> type of the type of the {@link PropertyPath#get() property value}.
   * @param selection the {@link PropertyPath} to add to the selection.
   * @param property the {@link ProjectionProperty#getProperty() projection property} to map to.
   * @return this {@link Select} for fluent API calls.
   */
  public <V> SelectProjection<R> and(PropertyPath<V> selection, PropertyPath<V> property) {

    ProjectionProperty<V> projectionProperty = ProjectionProperty.of(selection, property);
    return and(projectionProperty);
  }

  /**
   * @param <V> type of the type of the {@link PropertyPath#get() property value}.
   * @param selection the {@link CriteriaAggregation} to add to the selection.
   * @param property the {@link ProjectionProperty#getProperty() projection property} to map to.
   * @return this {@link Select} for fluent API calls.
   */
  public <V extends Number> SelectProjection<R> and(CriteriaAggregation<V> selection, PropertyPath<V> property) {

    ProjectionProperty<V> projectionProperty = ProjectionProperty.of(selection, property);
    return and(projectionProperty);
  }

  /**
   * @param projectionProperty the {@link ProjectionProperty} to add to the selection.
   * @return this {@link SelectProjection} for fluent API calls.
   */
  public SelectProjection<R> and(ProjectionProperty<?> projectionProperty) {

    Objects.requireNonNull(projectionProperty, "projectionProperty");
    add(projectionProperty);
    return this;
  }

  /**
   * @param projectionProperties the {@link ProjectionProperty} instances to add to the selection.
   * @return this {@link SelectProjection} for fluent API calls.
   */
  public SelectProjection<R> and(ProjectionProperty<?>... projectionProperties) {

    for (ProjectionProperty<?> property : projectionProperties) {
      add(property);
    }
    return this;
  }

  @Override
  public SelectProjection<R> distinct() {

    super.distinct();
    return this;
  }

  @Override
  public <E extends EntityBean> SelectFrom<R, E> from(E entity) {

    if (getSelections().isEmpty()) {
      throw new IllegalStateException("Selections must not be empty! Call 'and' method at least once!");
    }
    return super.from(entity);
  }

}
