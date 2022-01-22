package io.github.mmm.entity.bean.db.constraint;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.AttributeReadOnly;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link DbConstraint} on one or multiple columns.
 *
 * @since 1.0.0
 */
public abstract class DbConstraint {

  private final String name;

  private final List<PropertyPath<?>> columns;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param column the (first) {@link #getColumns() column}.
   */
  public DbConstraint(String name, PropertyPath<?> column) {

    super();
    this.name = name;
    this.columns = new ArrayList<>();
    if (column != null) {
      this.columns.add(column);
    }
  }

  /**
   * @return the name of the predicate (e.g. "FK_Person_Order").
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return the type of this {@link DbConstraint} (e.g. "CHECK" or "FOREIGN KEY").
   */
  public abstract String getType();

  /**
   * @return the {@link List} with the {@link PropertyPath properties} representing the columns this
   *         {@link DbConstraint} applies to.
   */
  public List<? extends PropertyPath<?>> getColumns() {

    return this.columns;
  }

  /**
   * @param column the {@link PropertyPath property} to add.
   */
  protected void add(PropertyPath<?> column) {

    this.columns.add(column);
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder(32);
    toString(sb);
    return sb.toString();
  }

  /**
   * @param sb the {@link StringBuilder} to append to.
   */
  protected void toString(StringBuilder sb) {

    if ((this.name != null) && !this.name.isEmpty()) {
      sb.append("CONSTRAINT ");
      sb.append(this.name);
      sb.append(' ');
    }
    sb.append(getType());
    toStringColumns(sb);
  }

  /**
   * @param sb the {@link StringBuilder} to append to.
   */
  protected void toStringColumns(StringBuilder sb) {

    sb.append(" (");
    assert !this.columns.isEmpty();
    String s = "";
    for (PropertyPath<?> column : this.columns) {
      sb.append(s);
      sb.append(column.path());
      s = ", ";
    }
    sb.append(')');
  }

  /**
   * @param prefix the constraint prefix (XX_).
   * @param property the {@link ReadableProperty} representing the column.
   * @return the {@link DbConstraint} {@link #getName() name}.
   */
  protected static final String createName(String prefix, ReadableProperty<?> property) {

    return createName(prefix, property, true);
  }

  /**
   * @param prefix the constraint prefix (XX_).
   * @param property the {@link ReadableProperty} representing the column.
   * @param addProperty - {@code true} to add the {@link ReadableProperty#path() property path}, {@code false}
   *        otherwise.
   * @return the {@link DbConstraint} {@link #getName() name}.
   */
  protected static final String createName(String prefix, ReadableProperty<?> property, boolean addProperty) {

    AttributeReadOnly lock = property.getMetadata().getLock();
    if (lock instanceof EntityBean) {
      EntityBean bean = (EntityBean) lock;
      if (addProperty) {
        return prefix + bean.getType().getSimpleName() + "_" + property.path();
      } else {
        return prefix + bean.getType().getSimpleName();
      }
    }
    return prefix + property.path();
  }

}
