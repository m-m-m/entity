/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.entity.bean.db.statement.AbstractTypedClause;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.MainDbClause;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredState;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.CriteriaMarshalling;
import io.github.mmm.property.criteria.CriteriaOrdering;

/**
 * A {@link OrderBy}-{@link DbClause} of an SQL {@link SelectStatement}.
 *
 * @param <R> type of the result of the selection.
 * @since 1.0.0
 */
public class OrderBy<R> extends AbstractTypedClause<R, OrderBy<R>> implements MainDbClause<R> {

  /** Name of {@link OrderBy} for marshaling. */
  public static final String NAME_ORDER_BY = "orderBy";

  /** Name of {@link #getOrderings()} for marshaling. */
  public static final String NAME_ORDERINGS = "o";

  private final SelectStatement<R> statement;

  private final List<CriteriaOrdering> orderings;

  /**
   * The constructor.
   *
   * @param statement the owning {@link SelectStatement}.
   */
  public OrderBy(SelectStatement<R> statement) {

    super();
    this.statement = statement;
    this.orderings = new ArrayList<>();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_ORDER_BY;
  }

  @Override
  public boolean isOmit() {

    return this.orderings.isEmpty();
  }

  /**
   * @param ordering the {@link CriteriaOrdering} to add.
   * @return this {@link OrderBy}-class itself for fluent API calls.
   */
  public OrderBy<R> and(CriteriaOrdering ordering) {

    Objects.requireNonNull(ordering, "ordering");
    this.orderings.add(ordering);
    return this;
  }

  /**
   * @param criteriaOrderings the {@link CriteriaOrdering}s to add.
   * @return this {@link OrderBy}-class itself for fluent API calls.
   */
  public OrderBy<R> and(CriteriaOrdering... criteriaOrderings) {

    for (CriteriaOrdering ordering : criteriaOrderings) {
      and(ordering);
    }
    return this;
  }

  /**
   * @return the {@link List} of {@link CriteriaOrdering}s to order by.
   */
  public List<CriteriaOrdering> getOrderings() {

    return this.orderings;
  }

  @Override
  public SelectStatement<R> get() {

    return this.statement;
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    if (!this.orderings.isEmpty()) {
      writer.writeName(NAME_ORDERINGS);
      writer.writeStartArray();
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      for (CriteriaOrdering ordering : this.orderings) {
        marshalling.writeOrdering(writer, ordering);
      }
      writer.writeEnd();

    }
    super.writeProperties(writer);
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    if (reader.isNameMatching(name, NAME_ORDERINGS)) {
      reader.require(StructuredState.START_ARRAY, true);
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      while (!reader.readEnd()) {
        this.orderings.add(marshalling.readOrdering(reader));
      }
    } else {
      super.readProperty(reader, name);
    }
  }

}
