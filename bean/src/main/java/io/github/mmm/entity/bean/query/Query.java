/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.AttributeReadOnly;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.property.criteria.CriteriaOrdering;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link Query} to search for {@link EntityBean}s in a database.
 *
 * @since 1.0.0
 */
public final class Query<E extends EntityBean> {

  private final Function<PropertyPath<?>, String> pathFormatter;

  private final E entity;

  private String alias;

  private List<CriteriaPredicate> where;

  private List<PropertyPath<?>> groupBy;

  private List<CriteriaPredicate> having;

  private List<CriteriaOrdering> orderBy;

  Query(E entity) {

    super();
    this.pathFormatter = new PathFormatter();
    this.entity = entity;
    this.alias = entity.getClass().getSimpleName();
    this.where = new ArrayList<>();
    this.groupBy = new ArrayList<>();
    this.having = new ArrayList<>();
    this.orderBy = new ArrayList<>();
  }

  E getEntity() {

    return this.entity;
  }

  String getAlias() {

    return this.alias;
  }

  List<CriteriaPredicate> getWhere() {

    return this.where;
  }

  List<CriteriaPredicate> getHaving() {

    return this.having;
  }

  public Query<E> as(String alias) {

    this.alias = alias;
    return this;
  }

  public Query<E> where(CriteriaPredicate predicate) {

    if (predicate != null) {
      this.where.add(predicate);
    }
    return this;
  }

  public Query<E> where(CriteriaPredicate... predicates) {

    for (CriteriaPredicate predicate : predicates) {
      where(predicate);
    }
    return this;
  }

  public Query<E> groupBy(PropertyPath<?> path) {

    if (path != null) {
      this.groupBy.add(path);
    }
    return this;
  }

  public Query<E> groupBy(PropertyPath<?>... paths) {

    for (PropertyPath<?> path : paths) {
      groupBy(path);
    }
    return this;
  }

  public Query<E> having(CriteriaPredicate predicate) {

    if (predicate != null) {
      this.having.add(predicate);
    }
    return this;
  }

  public Query<E> having(CriteriaPredicate... predicates) {

    for (CriteriaPredicate predicate : predicates) {
      having(predicate);
    }
    return this;
  }

  public Query<E> orderBy(CriteriaOrdering ordering) {

    if (ordering != null) {
      this.orderBy.add(ordering);
    }
    return this;
  }

  public Query<E> orderBy(CriteriaOrdering... orderings) {

    for (CriteriaOrdering ordering : orderings) {
      orderBy(ordering);
    }
    return this;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder(128);
    sb.append("SELECT ");
    sb.append(this.alias);
    sb.append(" FROM ");
    sb.append(this.entity.getType().getSimpleName());
    sb.append(" ");
    sb.append(this.alias);
    append(sb, " WHERE ", this.where, " AND ");
    append(sb, " GROUP BY ", this.groupBy, ", ");
    append(sb, " HAVING ", this.having, " AND ");
    append(sb, " ORDER BY ", this.orderBy, ", ");
    return sb.toString();
  }

  private void append(StringBuilder sb, String key, List<?> list, String separator) {

    if (list.isEmpty()) {
      return;
    }
    sb.append(key);
    String s = "";
    for (Object entry : list) {
      sb.append(s);
      if (entry instanceof CriteriaExpression) {
        ((CriteriaExpression<?>) entry).toString(sb, this.pathFormatter);
      } else {
        sb.append(entry);
      }
      s = separator;
    }
  }

  private class PathFormatter implements Function<PropertyPath<?>, String> {

    @Override
    public String apply(PropertyPath<?> path) {

      if (path instanceof ReadableProperty) {
        AttributeReadOnly lock = ((ReadableProperty<?>) path).getMetadata().getLock();
        if (lock == Query.this.entity) {
          return Query.this.alias + "." + path.getName();
        }
      }
      return path.getName();
    }

  }

}
