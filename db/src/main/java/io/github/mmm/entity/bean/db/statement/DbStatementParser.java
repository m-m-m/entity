/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import static io.github.mmm.base.filter.CharFilter.NEWLINE_OR_SPACE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.base.sort.SortOrder;
import io.github.mmm.bean.BeanFactory;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.bean.mapping.ClassNameMapper;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.constraint.CheckConstraint;
import io.github.mmm.entity.bean.db.constraint.DbConstraint;
import io.github.mmm.entity.bean.db.constraint.ForeignKeyConstraint;
import io.github.mmm.entity.bean.db.constraint.NotNullConstraint;
import io.github.mmm.entity.bean.db.constraint.PrimaryKeyConstraint;
import io.github.mmm.entity.bean.db.constraint.UniqueConstraint;
import io.github.mmm.entity.bean.db.statement.create.CreateTable;
import io.github.mmm.entity.bean.db.statement.create.CreateTableColumns;
import io.github.mmm.entity.bean.db.statement.create.CreateTableStatement;
import io.github.mmm.entity.bean.db.statement.delete.Delete;
import io.github.mmm.entity.bean.db.statement.delete.DeleteFrom;
import io.github.mmm.entity.bean.db.statement.delete.DeleteStatement;
import io.github.mmm.entity.bean.db.statement.insert.Insert;
import io.github.mmm.entity.bean.db.statement.insert.InsertInto;
import io.github.mmm.entity.bean.db.statement.insert.InsertStatement;
import io.github.mmm.entity.bean.db.statement.insert.InsertValues;
import io.github.mmm.entity.bean.db.statement.merge.Merge;
import io.github.mmm.entity.bean.db.statement.merge.MergeInto;
import io.github.mmm.entity.bean.db.statement.merge.MergeStatement;
import io.github.mmm.entity.bean.db.statement.select.GroupBy;
import io.github.mmm.entity.bean.db.statement.select.Having;
import io.github.mmm.entity.bean.db.statement.select.OrderBy;
import io.github.mmm.entity.bean.db.statement.select.Select;
import io.github.mmm.entity.bean.db.statement.select.SelectEntity;
import io.github.mmm.entity.bean.db.statement.select.SelectFrom;
import io.github.mmm.entity.bean.db.statement.select.SelectProjection;
import io.github.mmm.entity.bean.db.statement.select.SelectResult;
import io.github.mmm.entity.bean.db.statement.select.SelectSingle;
import io.github.mmm.entity.bean.db.statement.select.SelectStatement;
import io.github.mmm.entity.bean.db.statement.update.Update;
import io.github.mmm.entity.bean.db.statement.update.UpdateStatement;
import io.github.mmm.entity.bean.db.statement.upsert.Upsert;
import io.github.mmm.entity.bean.db.statement.upsert.UpsertInto;
import io.github.mmm.entity.bean.db.statement.upsert.UpsertStatement;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.property.criteria.CriteriaObjectParser;
import io.github.mmm.property.criteria.CriteriaOperator;
import io.github.mmm.property.criteria.CriteriaOrdering;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.Literal;
import io.github.mmm.property.criteria.ProjectionProperty;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.property.criteria.PropertyPathParser;
import io.github.mmm.property.criteria.SimplePath;
import io.github.mmm.scanner.CharScannerParser;
import io.github.mmm.scanner.CharStreamScanner;
import io.github.mmm.value.CriteriaObject;
import io.github.mmm.value.PropertyPath;

/**
 * {@link CharScannerParser} for {@link DbStatement}s.<br>
 * <b>ATTENTION:</b> This is NOT a generic SQL parser. It will only support the exact syntax produced by
 * {@link DbStatementFormatter} wit the defaults (as used by {@link DbStatement#toString()}).
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DbStatementParser implements CharScannerParser<DbStatement<?>> {

  private static final DbStatementParser INSTANCE = new DbStatementParser();

  private final ClassNameMapper classNameMapper;

  private final BeanFactory beanFactory;

  private final CriteriaObjectParser criteriaSelectionParser;

  /**
   * The constructor.
   */
  protected DbStatementParser() {

    super();
    this.classNameMapper = ClassNameMapper.get();
    this.beanFactory = BeanFactory.get();
    this.criteriaSelectionParser = CriteriaObjectParser.get();
  }

  @Override
  public DbStatement<?> parse(CharStreamScanner scanner) {

    try {
      DbStatement<?> statement;
      scanner.skipWhile(NEWLINE_OR_SPACE);
      String name = scanner.readWhile(CharFilter.LATIN_LETTER);
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      if (Select.NAME_SELECT.equalsIgnoreCase(name)) {
        statement = parseSelectStatement(scanner);
      } else if (Update.NAME_UPDATE.equalsIgnoreCase(name)) {
        statement = parseUpdateStatement(scanner);
      } else if (Insert.NAME_INSERT.equalsIgnoreCase(name)) {
        statement = parseInsertStatement(scanner);
      } else if (Delete.NAME_DELETE.equalsIgnoreCase(name)) {
        statement = parseDeleteStatement(scanner);
      } else if ("CREATE".equalsIgnoreCase(name)) {
        scanner.require("TABLE", true);
        scanner.requireOneOrMore(NEWLINE_OR_SPACE);
        statement = parseCreateTableStatement(scanner);
      } else if (Upsert.NAME_UPSERT.equalsIgnoreCase(name)) {
        statement = parseUsertStatement(scanner);
      } else if (Merge.NAME_MERGE.equalsIgnoreCase(name)) {
        statement = parseMergeStatement(scanner);
      } else {
        throw new IllegalStateException("Unknown statement: " + name);
      }
      scanner.skipWhile(NEWLINE_OR_SPACE);
      if (scanner.hasNext()) {
        throw new IllegalStateException("Internal error: Statement not parsed to the end.");
      }
      return statement;
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse DB statement after \n" + scanner.getBufferParsed()
          + "\n and before \n" + scanner.getBufferToParse() + "\nwith error: " + e.getMessage(), e);
    }
  }

  private CreateTableStatement<?> parseCreateTableStatement(CharStreamScanner scanner) {

    CreateTable<?> createTable = parseCreateTable(scanner);
    CreateTableColumns<?> columns = parseCreateTableColumns(scanner, createTable);
    return columns.get();
  }

  private CreateTableColumns<?> parseCreateTableColumns(CharStreamScanner scanner, CreateTable<?> createTable) {

    CreateTableColumns<?> columns = createTable.column(WritableProperty.NO_PROPERTIES);
    EntityBean entity = createTable.getEntity();
    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.requireOne('(');
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      if (scanner.expectStrict("CONSTRAINT")) {
        DbConstraint constraint = parseConstraint(scanner, entity);
        columns.constraint(constraint);
      } else {
        WritableProperty<?> column = EntityPathParser.parsePath(scanner, entity);
        columns.and(column, false);
        scanner.requireOne(NEWLINE_OR_SPACE);
        String columnType = scanner.readWhile(CharFilter.IDENTIFIER);
        Class<?> columnClass = this.classNameMapper.getClass(columnType);
        assert (columnClass == column.getValueClass());
      }
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
    scanner.requireOne(')');
    return columns;
  }

  private DbConstraint parseConstraint(CharStreamScanner scanner, EntityBean entity) {

    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    String constraintName = parseSegment(scanner);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    DbConstraint constraint = null;
    if (scanner.expectStrict(ForeignKeyConstraint.TYPE, true)) {
      ReadableProperty<?> column = parseColumn(scanner, entity);
      scanner.require("REFERENCES", true);
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      String referenceTable = PropertyPathParser.readSegment(scanner, null);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      scanner.requireOne('(');
      scanner.skipWhile(NEWLINE_OR_SPACE);
      String referenceColumn = PropertyPathParser.readSegment(scanner, null);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      scanner.requireOne(')');
      constraint = new ForeignKeyConstraint(constraintName, column, referenceTable, referenceColumn);
    } else if (scanner.expectStrict(PrimaryKeyConstraint.TYPE, true)) {
      ReadableProperty<?> column = parseColumn(scanner, entity);
      constraint = new PrimaryKeyConstraint(constraintName, column);
    } else if (scanner.expectStrict(NotNullConstraint.TYPE, true)) {
      ReadableProperty<?> column = parseColumn(scanner, entity);
      constraint = new NotNullConstraint(constraintName, column);
    } else if (scanner.expectStrict(UniqueConstraint.TYPE, true)) {
      List<WritableProperty<?>> columns = parseColumns(scanner, entity, 1);
      constraint = new UniqueConstraint(constraintName, columns.get(0));
      List<WritableProperty<?>> list = (List<WritableProperty<?>>) constraint.getColumns();
      for (int i = 1; i < columns.size(); i++) {
        list.add(columns.get(i));
      }
    } else if (scanner.expectStrict(CheckConstraint.TYPE, true)) {
      PropertyPathParser pathParser = new EntityPathParser(entity);
      CriteriaPredicate predicate = this.criteriaSelectionParser.parsePredicate(scanner, pathParser);
      constraint = new CheckConstraint(constraintName, predicate);
    }
    return constraint;
  }

  private WritableProperty<?> parseColumn(CharStreamScanner scanner, EntityBean entity) {

    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.requireOne('(');
    scanner.skipWhile(NEWLINE_OR_SPACE);
    WritableProperty<?> column = EntityPathParser.parsePath(scanner, entity);
    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.requireOne(')');
    scanner.skipWhile(NEWLINE_OR_SPACE);
    return column;
  }

  private List<WritableProperty<?>> parseColumns(CharStreamScanner scanner, EntityBean entity, int min) {

    List<WritableProperty<?>> columns = new ArrayList<>();
    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.requireOne('(');
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      WritableProperty<?> column = EntityPathParser.parsePath(scanner, entity);
      columns.add(column);
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
    scanner.requireOne(')');
    if (columns.size() < min) {
      throw new IllegalArgumentException("Requires " + min + " column(s) but found only " + columns.size() + ".");
    }
    return columns;
  }

  private MergeStatement<?> parseMergeStatement(CharStreamScanner scanner) {

    MergeStatement statement = new MergeInto<>(new Merge(), null).values(PropertyAssignment.EMPTY_ARRAY).get();
    // TODO Auto-generated method stub
    return statement;
  }

  private UpsertStatement<?> parseUsertStatement(CharStreamScanner scanner) {

    UpsertStatement upsertStatement = new UpsertInto<>(new Upsert(), null).values(PropertyAssignment.EMPTY_ARRAY).get();
    // TODO Auto-generated method stub
    return upsertStatement;
  }

  private DeleteStatement<?> parseDeleteStatement(CharStreamScanner scanner) {

    DeleteFrom<EntityBean> from = new DeleteFrom<>(new Delete(), null);
    parseFrom(scanner, from);
    DeleteStatement<EntityBean> statement = from.get();
    parseWhere(scanner, statement.getWhere());
    // TODO
    return statement;
  }

  private InsertStatement<?> parseInsertStatement(CharStreamScanner scanner) {

    InsertInto<?> into = new InsertInto<>(new Insert(), null);
    parseInto(scanner, into);
    scanner.skipWhile(' ');
    InsertValues<?> values = into.values(PropertyAssignment.EMPTY_ARRAY);
    parseValues(scanner, values, into.getEntity());
    InsertStatement statement = values.get();
    // TODO Auto-generated method stub
    return statement;
  }

  private UpdateStatement<?> parseUpdateStatement(CharStreamScanner scanner) {

    Update<?> update = parseUpdate(scanner);
    UpdateStatement<?> statement = update.get();
    AliasMap aliasMap = getAliasMap(update);
    parseUpdateSet(scanner, statement.getSet(), aliasMap);
    parseWhere(scanner, statement.getWhere());
    return statement;
  }

  private void parseUpdateSet(CharStreamScanner scanner, SetClause set, PropertyPathParser pathParser) {

    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      PropertyPath<?> property = pathParser.parse(scanner);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      scanner.requireOne('=');
      scanner.skipWhile(NEWLINE_OR_SPACE);
      CriteriaObject value = this.criteriaSelectionParser.parseSelection(scanner, pathParser);
      set.and(PropertyAssignment.of(property, value));
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
  }

  private Update<?> parseUpdate(CharStreamScanner scanner) {

    Update<?> update = new Update<>(null);
    parseEntitiesClause(scanner, update);
    scanner.require("SET", true);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    return update;
  }

  private SelectStatement<?> parseSelectStatement(CharStreamScanner scanner) {

    Select<?> select = parseSelect(scanner);
    SelectFrom from = new SelectFrom<>(select, null);
    parseFrom(scanner, from);
    if (select instanceof SelectEntity) {
      String aliasFrom = from.getAlias();
      String aliasSelect = select.getResultName();
      if (!Objects.equals(aliasFrom, aliasSelect)) {
        throw new IllegalArgumentException(
            "Alias of SELECT (" + aliasSelect + ") and FROM (" + aliasFrom + ") do not match.");
      }
    }
    SelectStatement statement = from.get();
    parseWhere(scanner, statement.getWhere());
    parseGroupBy(scanner, statement.getGroupBy());
    parseHaving(scanner, statement.getHaving());
    parseOrderBy(scanner, statement.getOrderBy());
    // post process selections
    AliasMap aliasMap = ((AbstractDbStatement) statement).getAliasMap();
    List<CriteriaObject<?>> selections = (List<CriteriaObject<?>>) select.getSelections();
    int size = selections.size();
    for (int i = 0; i < size; i++) {
      CriteriaObject<?> selection = selections.get(i);
      CriteriaObject<?> resolved = resolve(selection, aliasMap);
      if (resolved != selection) {
        selections.set(i, resolved);
      }
    }
    return statement;

  }

  private void parseValues(CharStreamScanner scanner, ValuesClause values, EntityBean entity) {

    // TODO consider mql change: INSERT INTO MyEntity e VALUES (e.Property1=value1, e.Property2=value2)
    scanner.requireOne('(');
    List<PropertyPath<?>> columns = new ArrayList<>();
    boolean todo = true;
    while (todo) {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      PropertyPath<?> path = EntityPathParser.parsePath(scanner, entity);
      columns.add(path);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      if (scanner.expectOne(')')) {
        todo = false;
      } else if (!scanner.expectOne(',')) {
        throw new IllegalArgumentException("Expecting ')' or ','.");
      }
    }
    scanner.skipWhile(' ');
    // TODO: make optional (e.g. support sub-query instead)
    scanner.require("VALUES", true);
    scanner.skipWhile(' ');
    scanner.requireOne('(');
    int i = 0;
    todo = true;
    int columnCount = columns.size();
    while (todo) {
      if (i >= columnCount) {
        break;
      }
      PropertyPath<?> path = columns.get(i);
      scanner.skipWhile(' ');
      Literal literal = this.criteriaSelectionParser.parseLiteral(scanner);
      values.and(PropertyAssignment.of(path, literal));
      i++;
      scanner.skipWhile(' ');
      if (scanner.expectOne(')')) {
        todo = false;
      } else if (!scanner.expectOne(',')) {
        throw new IllegalArgumentException("Expecting ')' or ','.");
      }
    }
    if (i != columnCount) {
      throw new IllegalArgumentException("Found " + columnCount + " coumn(s) but " + i + " value(s).");
    }
  }

  private void parseInto(CharStreamScanner scanner, IntoClause into) {

    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.require("INTO", true);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    parseEntityClause(scanner, into);
  }

  private CreateTable<?> parseCreateTable(CharStreamScanner scanner) {

    CreateTable<?> createTable = new CreateTable<>(null);
    parseEntityClause(scanner, createTable);
    return createTable;
  }

  private void parseEntityClause(CharStreamScanner scanner, AbstractEntityClause entityClause) {

    String entityName = parseSegment(scanner);
    Class entityClass = this.classNameMapper.getClass(entityName);
    EntityBean entity = (EntityBean) this.beanFactory.create(entityClass);
    entityClause.setEntity(entity);
  }

  private CriteriaObject<?> resolve(CriteriaObject<?> selection, AliasMap aliasMap) {

    if (selection instanceof SimplePath) {
      return aliasMap.resolvePath((SimplePath) selection);
    } else if (selection instanceof CriteriaExpression) {
      CriteriaExpression<?> expression = (CriteriaExpression<?>) selection;
      CriteriaOperator operator = expression.getOperator();
      List<? extends CriteriaObject<?>> args = expression.getArgs();
      List<CriteriaObject<?>> newArgs = null;
      int i = 0;
      for (CriteriaObject<?> arg : args) {
        CriteriaObject<?> resolved = resolve(arg, aliasMap);
        if (newArgs == null) {
          if (resolved != arg) {
            newArgs = new ArrayList<>(args.size());
            for (int j = 0; j <= i; j++) {
              newArgs.add(args.get(j));
            }
          }
        } else {
          newArgs.add(resolved);
        }
        i++;
      }
      if (newArgs != null) {
        return operator.expression(newArgs);
      }
    }
    // nothing to do (e.g. literal)...
    return selection;
  }

  private void parseOrderBy(CharStreamScanner scanner, OrderBy orderBy) {

    if (!scanner.expectStrict("ORDER BY ", true)) {
      return;
    }
    AliasMap aliasMap = ((AbstractDbStatement) orderBy.get()).getAliasMap();
    do {
      scanner.skipWhile(' ');
      PropertyPath<?> property = aliasMap.parse(scanner);
      SortOrder order = SortOrder.ASC;
      int spaces = scanner.skipWhile(' ');
      if (spaces > 0) {
        if (scanner.expectStrict("ASC", true)) {
          order = SortOrder.ASC;
        } else if (scanner.expectStrict("DESC", true)) {
          order = SortOrder.DESC;
        }
        scanner.skipWhile(' ');
      }
      CriteriaOrdering ordering = new CriteriaOrdering(property, order);
      orderBy.and(ordering);
    } while (scanner.expectOne(','));

  }

  private Select parseSelect(CharStreamScanner scanner) {

    scanner.skipWhile(NEWLINE_OR_SPACE);
    Select select;
    if (scanner.expectStrict("new", true)) {
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      WritableBean projectionBean = null;
      String bean = scanner.readUntil('(', false);
      if (bean == null) {
        throw new IllegalArgumentException("Missing '(' after 'SELECT +'.");
      } else if (bean.length() > 0) {
        Class beanClass = this.classNameMapper.getClass(bean);
        projectionBean = this.beanFactory.create(beanClass);
        select = new SelectProjection(projectionBean);
      } else {
        select = new SelectResult();
      }
      char c;
      do {
        scanner.skipWhile(NEWLINE_OR_SPACE);
        CriteriaObject<?> selection = this.criteriaSelectionParser.parseSelection(scanner);
        if (projectionBean != null) {
          PropertyPath path = null;
          int spaces = scanner.skipWhile(NEWLINE_OR_SPACE);
          if ((spaces > 0)) {
            if (scanner.expectStrict("AS", true)) {
              scanner.requireOneOrMore(NEWLINE_OR_SPACE);
            }
            path = EntityPathParser.parsePath(scanner, projectionBean);
          } else if (selection instanceof PropertyPath) {
            path = EntityPathParser.resolvePath(projectionBean, (PropertyPath) selection);
          } else {
            // error?
          }
          selection = new ProjectionProperty(selection, path);
        }
        select.getSelections().add(selection);
        c = scanner.next();
      } while (c == ',');
      if (c != ')') {
        throw new IllegalArgumentException("Missing ')'.");
      }
    } else {
      // single selection or entityAlias
      PropertyPath path = SimplePath.PARSER.parse(scanner);
      if (path.parentPath() == null) {
        select = new SelectEntity(path.getName());
      } else {
        select = new SelectSingle(path);
      }
    }
    return select;
  }

  private void parseFrom(CharStreamScanner scanner, FromClause from) {

    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.require("FROM", true);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    parseEntitiesClause(scanner, from);
  }

  private void parseEntitiesClause(CharStreamScanner scanner, AbstractEntitiesClause entitesClause) {

    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      String entityName = parseSegment(scanner);
      Class entityClass = this.classNameMapper.getClass(entityName);
      EntityBean entity = (EntityBean) this.beanFactory.create(entityClass);
      if (entitesClause.getEntity() == null) {
        entitesClause.setEntity(entity);
      } else {
        entitesClause.and(entity);
      }
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      String entityAlias = parseSegment(scanner);
      entitesClause.as(entityAlias);
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
  }

  private void parseWhere(CharStreamScanner scanner, WhereClause where) {

    if (!scanner.expectStrict("WHERE", true)) {
      return;
    }
    do {
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      CriteriaObject<?> expression = this.criteriaSelectionParser.parse(scanner);
      if (expression instanceof CriteriaPredicate) {
        where.and((CriteriaPredicate) expression);
      } else {
        throw new IllegalArgumentException("Expected predicate but found " + expression);
      }
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectStrict("AND", true));
  }

  private void parseGroupBy(CharStreamScanner scanner, GroupBy groupBy) {

    if (!scanner.expectStrict(" GROUP BY", true)) {
      return;
    }
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    PropertyPathParser pathParser = getAliasMap(groupBy);
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      PropertyPath<?> path = pathParser.parse(scanner);
      groupBy.and(path);
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
  }

  private String parseSegment(CharStreamScanner scanner) {

    String segment = scanner.readWhile(CharFilter.SEGMENT);
    if (segment.isEmpty()) {
      throw new IllegalArgumentException("Expected segment not found.");
    }
    return segment;
  }

  private void parseHaving(CharStreamScanner scanner, Having having) {

    // TODO Auto-generated method stub

  }

  private static AliasMap getAliasMap(MainDbClause clause) {

    return ((AbstractDbStatement) clause.get()).getAliasMap();
  }

  /**
   * @return the singleton instance of this {@link DbStatementParser}.
   */
  public static DbStatementParser get() {

    return INSTANCE;
  }

}
