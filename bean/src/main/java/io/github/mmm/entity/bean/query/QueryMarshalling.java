/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.marshall.Marshalling;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredReader.State;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.property.criteria.CriteriaMarshalling;
import io.github.mmm.property.criteria.CriteriaOrdering;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.value.PropertyPath;

/**
 *
 */
public class QueryMarshalling implements Marshalling<Query<?>> {

  /** Property name of the {@link EntityBean}. */
  public static final String NAME_ENTITY = "from";

  /** Property name of the entity alias. */
  public static final String NAME_ALIAS = "as";

  /** Property name of the WHERE clause. */
  public static final String NAME_WHERE = "where";

  /** Property name of the GROUP BY clause. */
  public static final String NAME_GROUP_BY = "groupBy";

  /** Property name of the HAVING clause. */
  public static final String NAME_HAVING = "having";

  /** Property name of the ORDER BY clause. */
  public static final String NAME_ORDER_BY = "orderBy";

  private static final QueryMarshalling INSTANCE = new QueryMarshalling();

  private final CriteriaMarshalling criteriaMarshalling;

  /**
   * The constructor.
   */
  protected QueryMarshalling() {

    this(CriteriaMarshalling.get());
  }

  /**
   * The constructor.
   *
   * @param criteriaMarshalling the {@link CriteriaMarshalling}.
   */
  protected QueryMarshalling(CriteriaMarshalling criteriaMarshalling) {

    super();
    this.criteriaMarshalling = criteriaMarshalling;
  }

  @Override
  public void writeObject(StructuredWriter writer, Query<?> query) {

    if (query == null) {
      writer.writeValueAsNull();
      return;
    }
    writer.writeStartObject();
    EntityBean entity = query.getEntity();
    String entityName = entity.getType().getSimpleName();
    writer.writeName(NAME_ENTITY);
    writer.writeValueAsString(entityName);
    writer.writeName(NAME_ALIAS);
    writer.writeValueAsString(query.getAlias());
    writeWhere(writer, query.getWhere());
    writeGroupBy(writer, query.getGroupBy());
    writeHaving(writer, query.getHaving());
    writeOrderBy(writer, query.getOrderBy());
    writer.writeEnd();
  }

  private void writeWhere(StructuredWriter writer, List<CriteriaPredicate> where) {

    if (where.isEmpty()) {
      return;
    }
    writer.writeName(NAME_WHERE);
    writer.writeStartArray();
    for (CriteriaPredicate predicate : where) {
      this.criteriaMarshalling.writeObject(writer, predicate);
    }
    writer.writeEnd();
  }

  private void writeGroupBy(StructuredWriter writer, List<PropertyPath<?>> groupBy) {

    if (groupBy.isEmpty()) {
      return;
    }
    writer.writeName(NAME_GROUP_BY);
    for (PropertyPath<?> propertyPath : groupBy) {
      this.criteriaMarshalling.writePropertyPath(writer, propertyPath);
    }
  }

  private void writeHaving(StructuredWriter writer, List<CriteriaPredicate> where) {

    if (where.isEmpty()) {
      return;
    }
    writer.writeName(NAME_HAVING);
    writer.writeStartArray();
    for (CriteriaPredicate predicate : where) {
      this.criteriaMarshalling.writeObject(writer, predicate);
    }
    writer.writeEnd();
  }

  private void writeOrderBy(StructuredWriter writer, List<CriteriaOrdering> orderBy) {

    if (orderBy.isEmpty()) {
      return;
    }
    writer.writeName(NAME_ORDER_BY);
    writer.writeStartArray();
    for (CriteriaOrdering ordering : orderBy) {
      this.criteriaMarshalling.writeOrdering(writer, ordering);
    }
    writer.writeEnd();
  }

  @Override
  public Query<?> readObject(StructuredReader reader) {

    reader.require(State.START_OBJECT);
    Query<?> query = new Query<>();
    while (!reader.readEnd()) {
      String name = reader.readName();
      if (NAME_ENTITY.equals(name)) {
        query.setEntityName(reader.readValueAsString());
      } else if (NAME_ALIAS.equals(name)) {
        query.as(reader.readValueAsString());
      } else if (NAME_WHERE.equals(name)) {
        readPredicates(reader, query.getWhere());
      } else if (NAME_GROUP_BY.equals(name)) {
        readPaths(reader, query.getGroupBy());
      } else if (NAME_HAVING.equals(name)) {
        readPredicates(reader, query.getHaving());
      } else if (NAME_ORDER_BY.equals(name)) {
        readOrderings(reader, query.getOrderBy());
      } else {
        // ignore unknown properties for compatibility
        reader.skipValue();
      }
    }
    return query;
  }

  private void readPredicates(StructuredReader reader, List<CriteriaPredicate> predicates) {

    reader.require(State.START_ARRAY);
    while (!reader.readEnd()) {
      predicates.add(readPredicate(reader));
    }
  }

  private CriteriaPredicate readPredicate(StructuredReader reader) {

    CriteriaExpression<?> expression = this.criteriaMarshalling.readObject(reader);
    if (expression instanceof CriteriaPredicate) {
      return (CriteriaPredicate) expression;
    }
    throw new IllegalStateException("" + expression);
  }

  private void readPaths(StructuredReader reader, List<PropertyPath<?>> paths) {

    reader.require(State.START_ARRAY);
    while (!reader.readEnd()) {
      paths.add(this.criteriaMarshalling.readPropertyPath(reader));
    }
  }

  private void readOrderings(StructuredReader reader, List<CriteriaOrdering> orderings) {

    reader.require(State.START_ARRAY);
    while (!reader.readEnd()) {
      orderings.add(this.criteriaMarshalling.readOrdering(reader));
    }
  }

  /**
   * @return the singleton instance of this {@link QueryMarshalling}.
   */
  public static QueryMarshalling get() {

    return INSTANCE;
  }

}
