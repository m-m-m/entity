/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
 * @since 1.0.0
 */
public final class Select extends AbstractClause implements StartClause {

  /** Name of {@link Select} for marshaling. */
  public static final String NAME_SELECT = "select";

  /** Name of property {@link #isDistinct()} for marshaling. */
  public static final String NAME_DISTINCT = "distinct";

  /** Name of property {@link #getSelections() selections} for marshaling. */
  public static final String NAME_SELECTIONS = "sel";

  private final List<Supplier<?>> selections;

  private SelectStatement<?> statement;

  private boolean distinct;

  /**
   * The constructor.
   */
  public Select() {

    super();
    this.selections = new ArrayList<>();
  }

  /**
   * The constructor.
   *
   * @param property the {@link PropertyPath property} to select.
   */
  public Select(PropertyPath<?> property) {

    this();
    and(property);
  }

  /**
   * The constructor.
   *
   * @param properties the {@link PropertyPath properties} to select.
   */
  public Select(PropertyPath<?>... properties) {

    this();
    for (PropertyPath<?> property : properties) {
      and(property);
    }
  }

  /**
   * The constructor.
   *
   * @param aggregation the {@link CriteriaAggregation} to select.
   */
  public Select(CriteriaAggregation<?> aggregation) {

    this();
    and(aggregation);
  }

  /**
   * The constructor.
   *
   * @param aggregations the {@link CriteriaAggregation}s to select.
   */
  public Select(CriteriaAggregation<?>... aggregations) {

    this();
    for (CriteriaAggregation<?> aggregation : aggregations) {
      and(aggregation);
    }
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
   * @return the {@link List} of selections. Only use for generic code. To build queries use fluent API methods such as
   *         {@link #and(PropertyPath)} or {@link #from(EntityBean)}.
   */
  public List<Supplier<?>> getSelections() {

    return this.selections;
  }

  /**
   * @return the owning {@link SelectStatement} or {@code null} if not initialized (what happens when
   *         {@link #from(EntityBean)} is called).
   */
  public SelectStatement<?> getStatement() {

    return this.statement;
  }

  void setStatement(SelectStatement<?> statement) {

    this.statement = statement;
  }

  /**
   * @param aggregation the {@link CriteriaAggregation} to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  public Select and(CriteriaAggregation<?> aggregation) {

    this.selections.add(aggregation);
    return this;
  }

  /**
   * @param aggregations the {@link CriteriaAggregation}s to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  public Select and(CriteriaAggregation<?>... aggregations) {

    for (CriteriaAggregation<?> aggregation : aggregations) {
      this.selections.add(aggregation);
    }
    return this;
  }

  /**
   * @param property the {@link PropertyPath property} to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  public Select and(PropertyPath<?> property) {

    this.selections.add(property);
    return this;
  }

  /**
   * @param properties the {@link PropertyPath properties} to add to the selection.
   * @return this {@link Select} for fluent API calls.
   */
  public Select and(PropertyPath<?>... properties) {

    for (PropertyPath<?> property : properties) {
      this.selections.add(property);
    }
    return this;
  }

  /**
   * Sets {@link #isDistinct() DISTINCT} selection (filter out duplicates).
   *
   * @return this {@link Select} for fluent API calls.
   */
  public Select distinct() {

    this.distinct = true;
    return this;
  }

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean entity} to select from.
   * @return the {@link SelectFrom} for fluent API calls.
   */
  public <E extends EntityBean> SelectFrom<E> from(E entity) {

    return new SelectFrom<>(this, entity);
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    if (isDistinct()) {
      writer.writeName(NAME_DISTINCT);
      writer.writeValueAsBoolean(Boolean.TRUE);
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
    } else if (NAME_SELECTIONS.equals(name)) {
      reader.require(State.START_ARRAY, true);
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      while (!reader.readEnd()) {
        Supplier<?> selection = marshalling.readArg(reader);
        this.selections.add(selection);
      }
    }
    super.readProperty(reader, name);
  }
}
