package io.github.mmm.entity.bean.db.constraint;

import java.util.function.Supplier;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.criteria.CriteriaPredicate;

/**
 * Foreign key {@link Constraint} uniquely identifying a different {@link io.github.mmm.entity.bean.EntityBean entity}
 * (row from another table).
 *
 * @since 1.0.0
 */
public final class CheckConstraint extends Constraint {

  /** {@link #getType() Type} {@value}. */
  public static final String TYPE = "CHECK";

  /** Suggested prefix for the {@link #getName() name}: {@value}. */
  public static final String PREFIX = "CK_";

  private final CriteriaPredicate predicate;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param predicate the {@link #getPredicate() predicate} to check.
   */
  public CheckConstraint(String name, CriteriaPredicate predicate) {

    this(name, getColumn(predicate), predicate);
    assert (name != null);
  }

  /**
   * The constructor.
   *
   * @param predicate the {@link #getPredicate() predicate} to check.
   */
  public CheckConstraint(CriteriaPredicate predicate) {

    this(null, getColumn(predicate), predicate);
  }

  private CheckConstraint(String name, ReadableProperty<?> property, CriteriaPredicate predicate) {

    super((name == null) ? createName(PREFIX, property) : name, property);
    this.predicate = predicate;
  }

  private static ReadableProperty<?> getColumn(CriteriaPredicate predicate) {

    Supplier<?> arg = predicate.getFirstArg();
    if (arg instanceof ReadableProperty) {
      return (ReadableProperty<?>) arg;
    } else if (arg instanceof CriteriaPredicate) {
      return getColumn((CriteriaPredicate) arg);
    } else {
      arg = predicate.getSecondArg();
      if (arg instanceof ReadableProperty) {
        return (ReadableProperty<?>) arg;
      }
    }
    return null;
  }

  /**
   * @return the {@link CriteriaPredicate} to check.
   */
  public CriteriaPredicate getPredicate() {

    return this.predicate;
  }

  @Override
  public String getType() {

    return TYPE;
  }

  @Override
  protected void toStringColumns(StringBuilder sb) {

    sb.append(this.predicate.toString());
  }

}
