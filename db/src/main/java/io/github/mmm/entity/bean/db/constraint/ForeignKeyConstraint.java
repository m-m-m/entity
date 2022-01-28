package io.github.mmm.entity.bean.db.constraint;

import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * Foreign key {@link DbConstraint} uniquely identifying a different {@link io.github.mmm.entity.bean.EntityBean entity}
 * (row from another table).
 *
 * @since 1.0.0
 */
public final class ForeignKeyConstraint extends DbConstraint {

  /** {@link #getType() Type} {@value}. */
  public static final String TYPE = "FOREIGN KEY";

  /** Suggested prefix for the {@link #getName() name}: {@value}. */
  public static final String PREFIX = "FK_";

  private final String referenceTable;

  private final String referenceColumn;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param column the (first) {@link #getColumns() column}.
   * @param referenceTable the {@link #getReferenceTable() referenced table}.
   * @param referenceColumn the {@link #getReferenceColumn() referenced column}.
   */
  public ForeignKeyConstraint(String name, PropertyPath<?> column, String referenceTable, String referenceColumn) {

    super(name, column);
    this.referenceTable = referenceTable;
    this.referenceColumn = referenceColumn;
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param column the (first) {@link #getColumns() column}.
   * @param referenceTable the {@link #getReferenceTable() referenced table}.
   */
  public ForeignKeyConstraint(String name, PropertyPath<?> column, String referenceTable) {

    this(name, column, referenceTable, PkProperty.NAME);
  }

  /**
   * The constructor.
   *
   * @param column the (first) {@link #getColumns() column}.
   * @param referenceTable the {@link #getReferenceTable() referenced table}.
   */
  public ForeignKeyConstraint(ReadableProperty<?> column, String referenceTable) {

    this(createName(PREFIX, column), column, referenceTable);
  }

  /**
   * The constructor.
   *
   * @param column the (first) {@link #getColumns() column}.
   * @param referenceTable the {@link #getReferenceTable() referenced table}.
   * @param referenceColumn the {@link #getReferenceColumn() referenced column}.
   */
  public ForeignKeyConstraint(ReadableProperty<?> column, String referenceTable, String referenceColumn) {

    this(createName(PREFIX, column), column, referenceTable, referenceColumn);
  }

  /**
   * @return the name of the referenced {@link io.github.mmm.entity.bean.EntityBean entity} and table.
   */
  public String getReferenceTable() {

    return this.referenceTable;
  }

  /**
   * @return the name of the referenced {@link IdProperty primary key column}.
   */
  public String getReferenceColumn() {

    return this.referenceColumn;
  }

  @Override
  public String getType() {

    return TYPE;
  }

  @Override
  protected void toString(StringBuilder sb) {

    super.toString(sb);
    sb.append(" REFERENCES ");
    sb.append(this.referenceTable);
    sb.append('(');
    sb.append(this.referenceColumn);
    sb.append(')');
  }

}
