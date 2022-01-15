package io.github.mmm.entity.bean.db.dialect;

import io.github.mmm.entity.bean.db.typemapping.DbTypeMapping;

/**
 * Abstract base implementation of {@link DbDialect}.
 *
 * @since 1.0.0
 */
public abstract class AbstractDbDialect implements DbDialect {

  private final DbTypeMapping typeMapping;

  /**
   * The constructor.
   *
   * @param typeMapper the {@link #getTypeMapping() type mapper}.
   */
  public AbstractDbDialect(DbTypeMapping typeMapper) {

    super();
    this.typeMapping = typeMapper;
    this.typeMapping.secure();
  }

  @Override
  public DbTypeMapping getTypeMapping() {

    return this.typeMapping;
  }

  @Override
  public String toString() {

    return getName() + "[" + getClass().getSimpleName() + "]";
  }

}
