/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.select.Having;
import io.github.mmm.entity.bean.sql.select.SelectWhere;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredReader.State;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.CriteriaMarshalling;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.PredicateOperator;

/**
 * {@link Clause} containing {@link #getPredicates() predicates} like a {@link SelectWhere}- or {@link Having}-clause.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class PredicateClause<E, SELF extends PredicateClause<E, SELF>> extends AbstractTypedClause<E, SELF> {

  /** Name of the property {@link #getPredicates()} for marshaling. */
  public static final String NAME_PREDICATES = "and";

  private final List<CriteriaPredicate> predicates;

  private transient boolean simplify;

  /**
   * The constructor.
   */
  public PredicateClause() {

    super();
    this.predicates = new ArrayList<>();
    this.simplify = true;
  }

  /**
   * @param predicate the {@link CriteriaPredicate} to add. They will be combined with {@link PredicateOperator#AND AND}
   *        if called multiple times.
   * @return this {@link Clause} itself for fluent API calls.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SELF and(CriteriaPredicate predicate) {

    if (predicate != null) {
      if (this.simplify) {
        predicate = predicate.simplify();
        if (predicate.getOperator() == PredicateOperator.AND) {
          // AND should only be used here with predicated.
          // Boolean literal would not make sense in query as well after simplification.
          this.predicates.addAll((List) predicate.getArgs());
          return self();
        }
      }
      this.predicates.add(predicate);
    }
    return self();
  }

  /**
   * @param criteriaPredicates the {@link CriteriaPredicate}s to add. They will be combined with
   *        {@link PredicateOperator#AND AND}.
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(CriteriaPredicate... criteriaPredicates) {

    for (CriteriaPredicate predicate : criteriaPredicates) {
      and(predicate);
    }
    return self();
  }

  /**
   * @return the {@link List} of {@link CriteriaPredicate}s.
   */
  public List<CriteriaPredicate> getPredicates() {

    return this.predicates;
  }

  @Override
  public boolean isOmit() {

    return this.predicates.isEmpty();
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    if (!this.predicates.isEmpty()) {
      writer.writeName(NAME_PREDICATES);
      writer.writeStartArray();
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      for (CriteriaPredicate predicate : this.predicates) {
        marshalling.writeObject(writer, predicate);
      }
      writer.writeEnd();
    }
    super.writeProperties(writer);
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    if (NAME_PREDICATES.equals(name)) {
      reader.require(State.START_ARRAY, true);
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      while (!reader.readEnd()) {
        this.predicates.add(marshalling.readPredicate(reader));
      }
    } else {
      super.readProperty(reader, name);
    }
  }

}
