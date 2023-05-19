/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.typemapping;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.IdentityTypeMapper;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link SingleTypeMapping} for a simple static mapping.
 *
 * @param <V> the {@link TypeMapper#getSourceType() source type}.
 * @since 1.0.0
 */
public class SingleTypeMappingStatic<V> extends SingleTypeMapping<V> {

  private final TypeMapper<V, ?> typeMapper;

  @Override
  public TypeMapper<V, ?> getTypeMapper() {

    return this.typeMapper;
  }

  /**
   * The constructor for an {@link IdentityTypeMapper}.
   *
   * @param type the {@link IdentityTypeMapper#getSourceType() source and target type}.
   * @param declaration the {@link TypeMapper#getDeclaration() type declaration}.
   */
  public SingleTypeMappingStatic(Class<V> type, String declaration) {

    this(new IdentityTypeMapper<>(type, declaration));
  }

  /**
   * The constructor.
   *
   * @param sqlType the {@link TypeMapper} to return as static mapping.
   *        {@link #getTypeMapper(io.github.mmm.property.ReadableProperty) mapping}.
   */
  public SingleTypeMappingStatic(TypeMapper<V, ?> sqlType) {

    super();
    this.typeMapper = sqlType;
  }

  @Override
  public TypeMapper<V, ?> getTypeMapper(ReadableProperty<?> property) {

    return this.typeMapper;
  }

}
