package io.github.mmm.entity.impl;

import java.util.Objects;

import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link TypeMapping} wrapping and delegating to another instance to prevent mutation or unwrapping.
 *
 * @since 1.0.0
 */
public class SecuredSqlTypeMapper implements TypeMapping {

  private final TypeMapping delegate;

  /**
   * The constructor.
   *
   * @param delegate the {@link TypeMapping} to wrap as delegate to prevent mutation or unwrapping.
   */
  public SecuredSqlTypeMapper(TypeMapping delegate) {

    super();
    Objects.requireNonNull(delegate);
    this.delegate = delegate;
  }

  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType) {

    return this.delegate.getTypeMapper(valueType);
  }

  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(ReadableProperty<V> property) {

    return this.delegate.getTypeMapper(property);
  }

  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property) {

    return this.delegate.getTypeMapper(valueType, property);
  }

  @Override
  public String toString() {

    return this.delegate.toString();
  }

}
