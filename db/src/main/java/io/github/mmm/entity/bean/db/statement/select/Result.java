package io.github.mmm.entity.bean.db.statement.select;

import java.util.Iterator;
import java.util.function.Supplier;

import io.github.mmm.base.collection.ArrayIterator;
import io.github.mmm.entity.bean.db.statement.select.Result.ResultEntry;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link Result} represents a generic result of a {@link SelectStatement} from a database.
 *
 * @see SelectFrom
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Result implements Iterable<ResultEntry> {

  private final ResultEntry[] entries;

  /**
   * The constructor.
   *
   * @param entries the {@link #getEntry(int) entries}.
   */
  public Result(ResultEntry... entries) {

    super();
    this.entries = entries;
  }

  /**
   * @param <V> type of the value.
   * @param i the index of the requested {@link ResultEntry}. Has to be in the range from {@code 0} to {@link #getSize()
   *        size-1}.
   * @return the requested {@link ResultEntry}.
   */
  public <V> ResultEntry<V> getEntry(int i) {

    return this.entries[i];
  }

  /**
   * @param <V> type of the value.
   * @param i the index of the requested {@link ResultEntry}. Has to be in the range from {@code 0} to {@link #getSize()
   *        size-1}.
   * @return the requested result value.
   */
  public <V> V getValue(int i) {

    return (V) this.entries[i].value;
  }

  /**
   * @return the number of {@link #getEntry(int) entries}.
   */
  public int getSize() {

    return this.entries.length;
  }

  @Override
  public Iterator<ResultEntry> iterator() {

    return new ArrayIterator<>(this.entries);
  }

  /**
   * Container for {@link #getSelection() selection} and {@link #getValue() value}.
   *
   * @param <V> type of the {@link #getValue() value}.
   */
  public static class ResultEntry<V> {

    private final Supplier<V> selection;

    private final V value;

    /**
     * The constructor.
     *
     * @param selection the {@link #getSelection() selection}.
     * @param value the result {@link #getValue() value} from the DB.
     */
    public ResultEntry(Supplier<V> selection, V value) {

      super();
      this.selection = selection;
      this.value = value;
    }

    /**
     * @return selection
     */
    public Supplier<V> getSelection() {

      return this.selection;
    }

    /**
     * @return a brief {@link String} representation of the {@link #getSelection() selection} (excluding aliases,
     *         compliant with simple naming so without expression syntax).
     */
    public String getSelectionSimpleName() {

      StringBuilder sb = new StringBuilder();
      getSimpleName(this.selection, sb);
      return sb.toString();
    }

    private void getSimpleName(Supplier<?> supplier, StringBuilder sb) {

      if (supplier instanceof PropertyPath) {
        sb.append(((PropertyPath<?>) supplier).getName());
      } else if (supplier instanceof CriteriaExpression) {
        CriteriaExpression<?> expression = (CriteriaExpression<?>) supplier;
        String op = expression.getOperator().getName();
        int count = expression.getArgCount();
        if (count == 0) {
          sb.append(op);
        } else if (count == 1) {
          sb.append(op);
          sb.append('_');
          getSimpleName(expression.getFirstArg(), sb);
        } else {
          String infix = "";
          for (Supplier<?> arg : expression.getArgs()) {
            sb.append(infix);
            getSimpleName(arg, sb);
            if (infix.isEmpty()) {
              infix = "_" + op + "_";
            }
          }
        }
      } else {
        sb.append(supplier.toString());
      }
    }

    /**
     * @return the result value from the database. May be {@code null}.
     */
    public V getValue() {

      return this.value;
    }

  }

}
