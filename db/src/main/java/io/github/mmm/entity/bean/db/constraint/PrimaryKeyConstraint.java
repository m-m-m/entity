package io.github.mmm.entity.bean.db.constraint;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * Foreign key {@link DbConstraint} uniquely identifying a different {@link io.github.mmm.entity.bean.EntityBean entity}
 * (row from another table).
 *
 * @since 1.0.0
 */
public final class PrimaryKeyConstraint extends DbConstraint {

  /** {@link #getType() Type} {@value}. */
  public static final String TYPE = "PRIMARY KEY";

  /** Suggested prefix for the {@link #getName() name}: {@value}. */
  public static final String PREFIX = "PK_";

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param column the (first) {@link #getColumns() column}.
   */
  public PrimaryKeyConstraint(String name, PropertyPath<?> column) {

    super(name, column);
  }

  /**
   * The constructor.
   *
   * @param column the (first) {@link #getColumns() column}.
   */
  public PrimaryKeyConstraint(ReadableProperty<?> column) {

    super(createName(PREFIX, column, false), column);
  }

  @Override
  public String getType() {

    return TYPE;
  }

}
