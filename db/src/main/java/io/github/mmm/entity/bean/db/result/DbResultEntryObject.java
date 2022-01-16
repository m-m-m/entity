package io.github.mmm.entity.bean.db.result;

import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.value.CriteriaSelection;
import io.github.mmm.value.PropertyPath;

/**
 * Implementation of {@link DbResultEntry} as simple object.
 *
 * @param <V> type of the {@link #getValue() value}.
 * @since 1.0.0
 */
public class DbResultEntryObject<V> implements DbResultEntry<V> {

  /** @see #getSelection() */
  protected final CriteriaSelection<?> selection;

  /** @see #getDbName() */
  protected final String dbName;

  final V value;

  /**
   * The constructor.
   *
   * @param selection the {@link #getSelection() selection}.
   * @param value the result {@link #getValue() value} from the DB.
   */
  public DbResultEntryObject(CriteriaSelection<?> selection, V value) {

    this(selection, value, computeDbName(selection));
  }

  /**
   * The constructor.
   *
   * @param selection the {@link #getSelection() selection}.
   * @param value the result {@link #getValue() value} from the DB.
   * @param dbName the {@link #getDbName() database name}.
   */
  public DbResultEntryObject(CriteriaSelection<?> selection, V value, String dbName) {

    super();
    this.selection = selection;
    this.value = value;
    this.dbName = dbName;
  }

  @Override
  public CriteriaSelection<?> getSelection() {

    return this.selection;
  }

  @Override
  public String getDbName() {

    return this.dbName;
  }

  private static String computeDbName(CriteriaSelection<?> selection) {

    StringBuilder sb = new StringBuilder();
    computeDbName(selection, sb);
    return sb.toString();
  }

  private static void computeDbName(CriteriaSelection<?> selection, StringBuilder sb) {

    if (selection instanceof PropertyPath) {
      sb.append(((PropertyPath<?>) selection).getName());
    } else if (selection instanceof CriteriaExpression) {
      CriteriaExpression<?> expression = (CriteriaExpression<?>) selection;
      String op = expression.getOperator().getName();
      int count = expression.getArgCount();
      if (count == 0) {
        sb.append(op);
      } else if (count == 1) {
        sb.append(op);
        sb.append('_');
        computeDbName(expression.getFirstArg(), sb);
      } else {
        String infix = "";
        for (CriteriaSelection<?> arg : expression.getArgs()) {
          sb.append(infix);
          computeDbName(arg, sb);
          if (infix.isEmpty()) {
            infix = "_" + op + "_";
          }
        }
      }
    } else {
      sb.append(selection.toString());
    }
  }

  @Override
  public V getValue() {

    return this.value;
  }

  @Override
  public DbResultEntryObject<V> withValue(V newValue) {

    if (this.value == newValue) {
      return this;
    }
    return new DbResultEntryObject<>(this.selection, newValue, this.dbName);
  }

  @Override
  public String toString() {

    return this.dbName + "[" + this.value + "]";
  }

}