package io.github.mmm.entity.bean.db.constraint;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * Unique {@link Constraint} ensuring that all values in the {@link #getColumns() column(s)} are different (no
 * duplicates).
 *
 * @since 1.0.0
 */
public final class UniqueConstraint extends Constraint {

  /** {@link #getType() Type} {@value}. */
  public static final String TYPE = "FOREIGN KEY";

  /** Suggested prefix for the {@link #getName() name}: {@value}. */
  public static final String PREFIX = "UQ_";

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param column the (first) {@link #getColumns() column}.
   */
  public UniqueConstraint(String name, PropertyPath<?> column) {

    super(name, column);
  }

  /**
   * The constructor.
   *
   * @param column the (first) {@link #getColumns() column}.
   */
  public UniqueConstraint(ReadableProperty<?> column) {

    super(createName(PREFIX, column), column);
  }

  @Override
  public String getType() {

    return TYPE;
  }

}
