package io.github.mmm.entity.bean.sql.dialect;

import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.entity.impl.SecuredSqlTypeMapper;

/**
 * Abstract base implementation of {@link SqlDialect}.
 *
 * @since 1.0.0
 */
public abstract class AbstractSqlDialect implements SqlDialect {

  private final TypeMapping typeMapper;

  /**
   * The constructor.
   *
   * @param typeMapper the {@link #getTypeMapper() type mapper}.
   */
  public AbstractSqlDialect(TypeMapping typeMapper) {

    super();
    this.typeMapper = new SecuredSqlTypeMapper(typeMapper);
  }

  @Override
  public TypeMapping getTypeMapper() {

    return this.typeMapper;
  }

  @Override
  public String toString() {

    return getName() + "[" + getClass().getSimpleName() + "]";
  }

}
