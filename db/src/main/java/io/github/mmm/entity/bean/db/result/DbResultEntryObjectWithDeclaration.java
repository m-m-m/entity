package io.github.mmm.entity.bean.db.result;

import io.github.mmm.value.CriteriaObject;

/**
 * Extends {@link DbResultEntryObject} with {@link #getDeclaration() declaration}.
 *
 * @param <V> type of the {@link #getValue() value}.
 * @since 1.0.0
 */
public class DbResultEntryObjectWithDeclaration<V> extends DbResultEntryObject<V> {

  private final String declaration;

  /**
   * The constructor.
   *
   * @param selection the {@link #getSelection() selection}.
   * @param value the result {@link #getValue() value} from the DB.
   * @param dbName the {@link #getDbName() database name}.
   * @param declaration the {@link #getDeclaration() declaration}.
   */
  public DbResultEntryObjectWithDeclaration(CriteriaObject<?> selection, V value, String dbName,
      String declaration) {

    super(selection, value, dbName);
    this.declaration = declaration;
  }

  @Override
  public String getDeclaration() {

    return this.declaration;
  }

  @Override
  public DbResultEntryObject<V> withValue(V newValue) {

    if (this.value == newValue) {
      return this;
    }
    return new DbResultEntryObjectWithDeclaration<>(this.selection, newValue, this.dbName, this.declaration);
  }

}
