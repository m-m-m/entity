/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.AbstractClause;
import io.github.mmm.entity.bean.sql.StartClause;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredReader.State;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.CriteriaAggregation;
import io.github.mmm.property.criteria.CriteriaMarshalling;
import io.github.mmm.value.PropertyPath;

/**
 * {@link StartClause} of a {@link SelectStatement} to query data from the database.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public abstract class Select<R> extends AbstractClause implements StartClause {

  /** Name of {@link Select} for marshaling. */
  public static final String NAME_SELECT = "select";

  /** Name of property {@link #isDistinct()} for marshaling. */
  public static final String NAME_DISTINCT = "distinct";

  /** Name of property {@link #getResultName()} for marshaling. */
  public static final String NAME_RESULT = "result";

  /** Name of property {@link #getSelections() selections} for marshaling. */
  public static final String NAME_SELECTIONS = "sel";

  private SelectStatement<R> statement;

  private final List<? extends Supplier<?>> selections;

  private transient R resultBean;

  private String resultName;

  private boolean distinct;

  /**
   * The constructor.
   *
   * @param resultBean the {@link #getResultBean() result bean}.
   */
  protected Select(R resultBean) {

    super();
    this.selections = new ArrayList<>();
    setResultBean(resultBean);
  }

  @Override
  protected String getMarshallingName() {

    return NAME_SELECT;
  }

  /**
   * @return {@code true} for {@code DISTINCT} selection (filter out duplicates), {@code false} otherwise.
   */
  public boolean isDistinct() {

    return this.distinct;
  }

  /**
   * @return the {@link List} of selections. Only use for generic code. To build queries use fluent API methods.
   */
  public List<? extends Supplier<?>> getSelections() {

    return this.selections;
  }

  /**
   * @return the owning {@link SelectStatement} or {@code null} if not initialized (until {@code from} method is
   *         called).
   */
  public SelectStatement<R> getStatement() {

    return this.statement;
  }

  void setStatement(SelectStatement<R> statement) {

    this.statement = statement;
  }

  /**
   * @return the result {@link io.github.mmm.bean.WritableBean bean} for {@link SelectProjection} or
   *         {@link SelectEntity}, otherwise {@code null}.
   */
  public R getResultBean() {

    return this.resultBean;
  }

  /**
   * @param resultBean the new value of {@link #getResultBean()}.
   */
  protected void setResultBean(R resultBean) {

    if ((resultBean instanceof WritableBean) && (this.resultName == null)) {
      this.resultName = ((WritableBean) resultBean).getType().getStableName();
    }
    this.resultBean = resultBean;
  }

  /**
   * @return the optional result name.
   * @see io.github.mmm.entity.bean.sql.AbstractEntityClause#getEntityName()
   */
  public String getResultName() {

    return this.resultName;
  }

  /**
   * @param resultName new value of {@link #getResultName()}.
   */
  public void setResultName(String resultName) {

    this.resultName = resultName;
  }

  /**
   * Sets {@link #isDistinct() DISTINCT} selection (filter out duplicates).
   *
   * @return this {@link Select} for fluent API calls.
   */
  public Select<R> distinct() {

    this.distinct = true;
    return this;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  void add(Supplier<?> selection) {

    ((List) this.selections).add(selection);
  }

  /**
   * @param aggregation the {@link CriteriaAggregation} to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  protected Select<R> and(CriteriaAggregation<?> aggregation) {

    add(aggregation);
    return this;
  }

  /**
   * @param aggregations the {@link CriteriaAggregation}s to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  protected Select<R> and(CriteriaAggregation<?>... aggregations) {

    for (CriteriaAggregation<?> aggregation : aggregations) {
      add(aggregation);
    }
    return this;
  }

  /**
   * @param property the {@link PropertyPath property} to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  protected Select<R> and(PropertyPath<?> property) {

    add(property);
    return this;
  }

  /**
   * @param properties the {@link PropertyPath properties} to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  protected Select<R> and(PropertyPath<?>... properties) {

    for (PropertyPath<?> property : properties) {
      add(property);
    }
    return this;
  }

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean entity} to select from.
   * @return the {@link SelectFrom} for fluent API calls.
   */
  protected <E extends EntityBean> SelectFrom<R, E> from(E entity) {

    return new SelectFrom<>(this, entity);
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    if (isDistinct()) {
      writer.writeName(NAME_DISTINCT);
      writer.writeValueAsBoolean(Boolean.TRUE);
    }
    if (this.resultName != null) {
      writer.writeName(NAME_RESULT);
      writer.writeValueAsString(this.resultName);
    }
    if (!this.selections.isEmpty()) {
      writer.writeName(NAME_SELECTIONS);
      writer.writeStartArray();
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      for (Supplier<?> selection : this.selections) {
        marshalling.writeArg(writer, selection);
      }
      writer.writeEnd();
    }
    super.writeProperties(writer);
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    if (NAME_DISTINCT.equals(name)) {
      this.distinct = Boolean.TRUE.equals(reader.readValueAsBoolean());
    } else if (NAME_RESULT.equals(name)) {
      this.resultName = reader.readValueAsString();
    } else if (NAME_SELECTIONS.equals(name)) {
      reader.require(State.START_ARRAY, true);
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      while (!reader.readEnd()) {
        Supplier<?> selection = marshalling.readArg(reader);
        add(selection);
      }
    } else {
      super.readProperty(reader, name);
    }
  }

  /**
   * Alternative for {@code new SelectColumn(property)}.
   *
   * @param <R> type of the result of the selection.
   * @param property the single {@link PropertyPath property} to select.
   * @return the {@link SelectColumn} clause.
   */
  public static <R> SelectColumn<R> column(PropertyPath<R> property) {

    return new SelectColumn<>(property);
  }

  /**
   * Alternative for {@code new SelectAggregation(aggregation)}.
   *
   * @param <R> type of the result of the selection.
   * @param aggregation the single {@link CriteriaAggregation property} to select.
   * @return the {@link SelectAggregation} clause.
   */
  public static <R> SelectAggregation<R> aggregation(CriteriaAggregation<R> aggregation) {

    return new SelectAggregation<>(aggregation);
  }

  /**
   * Alternative for {@code new SelectEntity(entity)}.
   *
   * @param <R> type of the {@link EntityBean} to select.
   * @param entity the {@link EntityBean} to select.
   * @return the {@link SelectEntity} clause.
   */
  public static <R extends EntityBean> SelectEntity<R> entity(R entity) {

    return new SelectEntity<>(entity);
  }

  /**
   * Alternative for {@code new SelectProjection(bean)}.
   *
   * @param <R> type of the {@link WritableBean} to select.
   * @param bean the {@link WritableBean} to select.
   * @return the {@link SelectProjection} clause.
   */
  public static <R extends WritableBean> SelectProjection<R> projection(R bean) {

    return new SelectProjection<>(bean);
  }
}
