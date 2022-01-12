/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.base.io.AppendableWriter;
import io.github.mmm.entity.bean.sql.constraint.Constraint;
import io.github.mmm.entity.bean.sql.create.CreateIndexColumns;
import io.github.mmm.entity.bean.sql.create.CreateTable;
import io.github.mmm.entity.bean.sql.create.CreateTableColumns;
import io.github.mmm.entity.bean.sql.delete.Delete;
import io.github.mmm.entity.bean.sql.insert.Insert;
import io.github.mmm.entity.bean.sql.insert.InsertInto;
import io.github.mmm.entity.bean.sql.insert.InsertValues;
import io.github.mmm.entity.bean.sql.select.OrderBy;
import io.github.mmm.entity.bean.sql.select.Select;
import io.github.mmm.entity.bean.sql.select.SelectFrom;
import io.github.mmm.entity.bean.sql.select.SelectStatement;
import io.github.mmm.entity.bean.sql.type.NoSqlTypeMapping;
import io.github.mmm.entity.bean.sql.update.Update;
import io.github.mmm.entity.bean.sql.upsert.Upsert;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.criteria.BooleanLiteral;
import io.github.mmm.property.criteria.CriteriaOrdering;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.CriteriaSqlFormatter;
import io.github.mmm.property.criteria.PredicateOperator;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Formatter to format a {@link Clause} or {@link Statement} to SQL.
 *
 * @since 1.0.0
 */
public class SqlFormatter implements ClauseVisitor {

  private static final CriteriaPredicate PARENT_AND = PredicateOperator.AND.criteria(List.of(BooleanLiteral.TRUE));

  private final TypeMapping typeMapping;

  private final CriteriaSqlFormatter criteriaFormatter;

  SqlFormatter() {

    this(new CriteriaSqlFormatterInline());
  }

  /**
   * The constructor.
   *
   * @param criteriaFormatter the {@link CriteriaSqlFormatter} used to format criteria fragments to SQL.
   */
  public SqlFormatter(CriteriaSqlFormatter criteriaFormatter) {

    this(NoSqlTypeMapping.get(), criteriaFormatter);
  }

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   * @param criteriaFormatter the {@link CriteriaSqlFormatter} used to format criteria fragments to SQL.
   */
  public SqlFormatter(TypeMapping typeMapping, CriteriaSqlFormatter criteriaFormatter) {

    super();
    this.typeMapping = typeMapping;
    this.criteriaFormatter = criteriaFormatter;
  }

  /**
   * @return the {@link CriteriaSqlFormatter} used to format criteria fragments to SQL.
   */
  public CriteriaSqlFormatter getCriteriaFormatter() {

    return this.criteriaFormatter;
  }

  /**
   * @param text the SQL to append.
   */
  protected void write(String text) {

    this.criteriaFormatter.out().append(text);
  }

  /**
   * @return the {@link AppendableWriter} wrapping the {@link Appendable} to write to.
   */
  protected AppendableWriter out() {

    return this.criteriaFormatter.out();
  }

  /**
   * @param statement the {@link Statement} to format to SQL.
   * @return this {@link SqlFormatter} for fluent API calls.
   */
  public SqlFormatter onStatement(Statement<?> statement) {

    for (Clause clause : statement.getClauses()) {
      onClause(clause);
    }
    return this;
  }

  @Override
  public SqlFormatter onClause(Clause clause) {

    if (!clause.isOmit()) {
      ClauseVisitor.super.onClause(clause);
    }
    return this;
  }

  @Override
  public void onSelect(Select<?> select) {

    write("SELECT");
    SelectStatement<?> statement = select.getStatement();
    SelectFrom<?, ?> selectFrom = null;
    if (statement != null) {
      selectFrom = statement.getFrom();
    }
    onSelections(select, selectFrom);
    ClauseVisitor.super.onSelect(select);
  }

  @Override
  public void onDelete(Delete delete) {

    write("DELETE");
    ClauseVisitor.super.onDelete(delete);
  }

  @Override
  public void onInsert(Insert insert) {

    write("INSERT");
    ClauseVisitor.super.onInsert(insert);
  }

  @Override
  public void onUpdate(Update<?> update) {

    write("UPDATE ");
    onEntity(update);
    ClauseVisitor.super.onUpdate(update);
  }

  @Override
  public void onUpsert(Upsert upsert) {

    write("UPSERT");
    ClauseVisitor.super.onUpsert(upsert);
  }

  @Override
  public void onCreateTable(CreateTable<?> createTable) {

    write("CREATE TABLE ");
    write(createTable.getEntityName());
    write(" (\n");
    ClauseVisitor.super.onCreateTable(createTable);
  }

  /**
   * @param select the {@link Select} with the {@link Select#getSelections() selections}.
   * @param selectFrom the {@link SelectFrom}.
   */
  protected void onSelections(Select<?> select, SelectFrom<?, ?> selectFrom) {

    List<? extends Supplier<?>> selections = select.getSelections();
    if (selections.isEmpty()) {
      onSelectAll(selectFrom);
    } else {
      String s = " (";
      int i = 0;
      for (Supplier<?> selection : selections) {
        write(s);
        this.criteriaFormatter.onArg(selection, i++, null);
        s = ", ";
      }
      write(")");
    }
  }

  /**
   * @return {@code true} if a {@link Select} of all properties should happen via {@link SelectFrom#getAlias() alias},
   *         {@code false} otherwise (to simply use {@code *}). The default is {@code false}. Override to change. E.g.
   *         in JPQL you would write "SELECT a FROM Entity a ..." whereas in plain SQL you would write "SELECT * FROM
   *         Entity ..."
   */
  public boolean isSelectAllByAlias() {

    return true;
  }

  /**
   * @return {@code true} to use the {@code AS} keyword before an {@link SelectFrom#getAlias() alias} (e.g. "FROM Entity
   *         <b>AS</b> e"), {@code false} otherwise.
   */
  public boolean isUseAsBeforeAlias() {

    return false;
  }

  /**
   * @param selectFrom the {@link SelectFrom} giving access to the {@link SelectFrom#getAlias() alias}.
   */
  protected void onSelectAll(SelectFrom<?, ?> selectFrom) {

    if (isSelectAllByAlias()) {
      write(" ");
      write(selectFrom.getAlias());
    } else {
      write(" *");
    }
  }

  @Override
  public void onFrom(From<?, ?, ?> from) {

    write(" FROM ");
    onEntities(from);
    ClauseVisitor.super.onFrom(from);
  }

  /**
   * @param entities the {@link AbstractEntitiesClause} to format.
   */
  protected void onEntities(AbstractEntitiesClause<?, ?, ?> entities) {

    onEntity(entities);
    for (EntitySubClause<?, ?> entity : entities.getAdditionalEntities()) {
      onAdditionalEntity(entity);
    }
  }

  /**
   * @param entity the {@link AbstractEntityClause} to format.
   */
  protected void onEntity(AbstractEntityClause<?, ?, ?> entity) {

    write(entity.getEntityName());
    onAlias(entity.getAlias(), entity);
  }

  /**
   * @param entity the {@link EntitySubClause} to format.
   */
  protected void onAdditionalEntity(EntitySubClause<?, ?> entity) {

    write(", ");
    onEntity(entity);
  }

  /**
   * @param alias the {@link EntitySubClause#getAlias() alias}.
   * @param clause the owning {@link AbstractEntityClause}.
   */
  protected void onAlias(String alias, Clause clause) {

    if (alias != null) {
      write(" ");
      if (isUseAsBeforeAlias()) {
        write("AS ");
      }
      write(alias);
    }
  }

  @Override
  public void onWhere(Where<?, ?> where) {

    write(" WHERE ");
    onPredicateClause(where);
    ClauseVisitor.super.onWhere(where);
  }

  private void onPredicateClause(PredicateClause<?, ?> clause) {

    String s = "";
    for (CriteriaPredicate predicate : clause.getPredicates()) {
      write(s);
      this.criteriaFormatter.onExpression(predicate, PARENT_AND);
      s = " AND ";
    }
  }

  @Override
  public void onOrderBy(OrderBy<?> orderBy) {

    write(" ORDER BY ");
    String s = "";
    for (CriteriaOrdering ordering : orderBy.getOrderings()) {
      write(s);
      this.criteriaFormatter.onOrdering(ordering);
      s = ", ";
    }
    ClauseVisitor.super.onOrderBy(orderBy);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onInto(Into<?, ?> into) {

    write(" INTO ");
    write(into.getEntityName());
    if (into instanceof InsertInto) {
      InsertInto<?> insertInto = (InsertInto<?>) into;
      InsertValues<?> values = insertInto.values((PropertyAssignment[]) PropertyAssignment.EMPTY_ARRAY);
      String s = "(";
      int i = 0;
      for (PropertyAssignment<?> assignment : values.getAssignments()) {
        write(s);
        this.criteriaFormatter.onPropertyPath(assignment.getProperty(), i++, null);
        s = ", ";
      }
      write(")");
    }
    ClauseVisitor.super.onInto(into);
  }

  @Override
  public void onValues(Values<?, ?> values) {

    write(" VALUES (");
    String s = "";
    int i = 0;
    for (PropertyAssignment<?> assignment : values.getAssignments()) {
      write(s);
      this.criteriaFormatter.onArg(assignment.getValue(), i++, null);
      s = ", ";
    }
    write(")");
    ClauseVisitor.super.onValues(values);
  }

  @Override
  public void onSet(Set<?, ?> set) {

    write(" SET ");
    String s = "";
    int i = 0;
    for (PropertyAssignment<?> assignment : set.getAssignments()) {
      write(s);
      this.criteriaFormatter.onPropertyPath(assignment.getProperty(), i++, null);
      write("=");
      this.criteriaFormatter.onArg(assignment.getValue(), i++, null);
      s = ", ";
    }
    ClauseVisitor.super.onSet(set);
  }

  @Override
  public void onColumns(CreateTableColumns<?> columns) {

    String s = "  ";
    List<PropertyPath<?>> cols = columns.getProperties().stream().sorted((p1, p2) -> p1.path().compareTo(p2.path()))
        .collect(Collectors.toList());
    for (PropertyPath<?> property : cols) {
      write(s);
      onCreateColumn((ReadableProperty<?>) property);
      s = ",\n  ";
    }
    List<Constraint> constraints = columns.getConstraints().stream()
        .sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
    for (Constraint constraint : constraints) {
      write(s);
      write(constraint.toString());
      s = ",\n  ";
    }
    ClauseVisitor.super.onColumns(columns);
    write("\n)");
  }

  /**
   * @param column the {@link ReadableProperty} representing the column to create.
   */
  protected void onCreateColumn(ReadableProperty<?> column) {

    // TODO must be refactored to work recursive...
    TypeMapper<?, ?> sqlType = this.typeMapping.getTypeMapper(column);
    String columnName = column.path();
    if (sqlType == null) {
      throw new ObjectNotFoundException("SqlType", column.getValueClass());
    }
    do {
      write(sqlType.mapName(columnName));
      write(" ");
      if (sqlType.hasDeclaration()) {
        write(sqlType.getDeclaration());
      } else {
        // TODO omit at least "java.lang."
        Class<?> valueClass = column.getValueClass();
        String name = valueClass.getName();
        if (name.startsWith("java.lang.")) {
          name = name.substring(10);
        } else if (name.startsWith("io.github.mmm.")) {
          int lastDot = name.lastIndexOf('.');
          name = name.substring(lastDot + 1);
        }
        write(name);
      }
      sqlType = sqlType.next();
    } while (sqlType != null);
  }

  @Override
  public void onColumns(CreateIndexColumns<?> columns) {

    ClauseVisitor.super.onColumns(columns);
  }

  @Override
  public String toString() {

    return out().toString();
  }

  private static class CriteriaSqlFormatterInline extends CriteriaSqlFormatter {

    CriteriaSqlFormatterInline() {

      super();
    }

  }

}
