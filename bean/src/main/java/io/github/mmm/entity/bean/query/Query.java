/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.criteria.BooleanLiteral;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.property.criteria.CriteriaOrdering;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.CriteriaSqlFormatter;
import io.github.mmm.property.criteria.PredicateOperator;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link Query} to search for {@link EntityBean}s in a database.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @since 1.0.0
 */
public final class Query<E extends EntityBean> {

  private static final CriteriaPredicate PARENT_AND = PredicateOperator.AND.criteria(List.of(BooleanLiteral.TRUE));

  private final E entity;

  private final List<CriteriaPredicate> where;

  private final List<PropertyPath<?>> groupBy;

  private final List<CriteriaPredicate> having;

  private final List<CriteriaOrdering> orderBy;

  private String entityName;

  private String alias;

  private boolean simplify;

  Query() {

    this(null, null);
  }

  Query(String entityName) {

    this(null, entityName);
  }

  Query(E entity) {

    this(entity, null);
  }

  Query(E entity, String entityName) {

    super();
    this.entity = entity;
    if ((entityName == null) && (entity != null)) {
      entityName = entity.getType().getSimpleName();
    }
    this.entityName = entityName;
    this.simplify = true;
    this.where = new ArrayList<>();
    this.groupBy = new ArrayList<>();
    this.having = new ArrayList<>();
    this.orderBy = new ArrayList<>();
  }

  E getEntity() {

    return this.entity;
  }

  String getEntityName() {

    return this.entityName;
  }

  void setEntityName(String entityName) {

    this.entityName = entityName;
  }

  String getAlias() {

    return this.alias;
  }

  List<CriteriaPredicate> getWhere() {

    return this.where;
  }

  List<PropertyPath<?>> getGroupBy() {

    return this.groupBy;
  }

  List<CriteriaPredicate> getHaving() {

    return this.having;
  }

  List<CriteriaOrdering> getOrderBy() {

    return this.orderBy;
  }

  /**
   * @param entityAlias the alias (variable name) for the {@link EntityBean} to query.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> as(String entityAlias) {

    this.alias = entityAlias;
    if (this.entity != null) {
      this.entity.path(entityAlias + ".");
    }
    return this;
  }

  /**
   * @param predicate the {@link CriteriaPredicate} to add to the {@code WHERE} clause. If called multiple times, they
   *        will be combined with {@code AND}.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> where(CriteriaPredicate predicate) {

    addPredicate(this.where, predicate);
    return this;
  }

  /**
   * @param predicates the {@link CriteriaPredicate}s to add to the {@code WHERE} clause. They will be combined with
   *        {@code AND}.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> where(CriteriaPredicate... predicates) {

    for (CriteriaPredicate predicate : predicates) {
      where(predicate);
    }
    return this;
  }

  /**
   * @param path the {@link PropertyPath} to the property to group by.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> groupBy(PropertyPath<?> path) {

    if (path != null) {
      this.groupBy.add(path);
    }
    return this;
  }

  /**
   * @param paths the {@link PropertyPath}s to the properties to group by.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> groupBy(PropertyPath<?>... paths) {

    for (PropertyPath<?> path : paths) {
      groupBy(path);
    }
    return this;
  }

  /**
   * @param predicate the {@link CriteriaPredicate} to add to the {@code HAVING} clause. If called multiple times, they
   *        will be combined with {@code AND}.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> having(CriteriaPredicate predicate) {

    addPredicate(this.having, predicate);
    return this;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private void addPredicate(List<CriteriaPredicate> list, CriteriaPredicate predicate) {

    if (predicate != null) {
      if (this.simplify) {
        predicate = predicate.simplify();
        if (predicate.getOperator() == PredicateOperator.AND) {
          // AND should only be used here with predicated.
          // Boolean literal would not make sense in query as well after simplification.
          list.addAll((List) predicate.getArgs());
          return;
        }
      }
      list.add(predicate);
    }
  }

  /**
   * @param predicates the {@link CriteriaPredicate}s to add to the {@code HAVING} clause. They will be combined with
   *        {@code AND}.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> having(CriteriaPredicate... predicates) {

    for (CriteriaPredicate predicate : predicates) {
      having(predicate);
    }
    return this;
  }

  /**
   * @param ordering the {@link CriteriaOrdering} to order by.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> orderBy(CriteriaOrdering ordering) {

    if (ordering != null) {
      this.orderBy.add(ordering);
    }
    return this;
  }

  /**
   * @param orderings the {@link CriteriaOrdering}s to order by.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> orderBy(CriteriaOrdering... orderings) {

    for (CriteriaOrdering ordering : orderings) {
      orderBy(ordering);
    }
    return this;
  }

  /**
   * @param newSimplify {@code true} to {@link CriteriaExpression#simplify() simplify} {@link CriteriaExpression}s
   *        whilst recording (default), {@code false} otherwise. Shall be called at the beginning before other methods.
   * @return this {@link Query} for fluent API.
   */
  public Query<E> simplify(boolean newSimplify) {

    this.simplify = newSimplify;
    return this;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder(128);
    CriteriaSqlFormatter formatter = new CriteriaSqlFormatter(sb);
    if (this.alias == null) {
      as(this.entity.getType().getSimpleName());
    }
    sb.append("SELECT ");
    sb.append(this.alias);
    sb.append(" FROM ");
    sb.append(this.entityName);
    sb.append(" ");
    sb.append(this.alias);
    append(sb, formatter, " WHERE ", this.where, " AND ");
    append(sb, formatter, " GROUP BY ", this.groupBy, ", ");
    append(sb, formatter, " HAVING ", this.having, " AND ");
    append(sb, formatter, " ORDER BY ", this.orderBy, ", ");
    return sb.toString();
  }

  private void append(StringBuilder sb, CriteriaSqlFormatter formatter, String key, List<?> list, String separator) {

    if (list.isEmpty()) {
      return;
    }
    sb.append(key);
    String s = "";
    for (Object entry : list) {
      sb.append(s);
      if (entry instanceof CriteriaExpression) {
        formatter.onExpression((CriteriaExpression<?>) entry, PARENT_AND);
      } else {
        sb.append(entry);
      }
      s = separator;
    }
  }

}
