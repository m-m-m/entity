/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.typemapping;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.IdentityTypeMapper;
import io.github.mmm.value.converter.TypeMapper;

/// Implementation of [SingleTypeMapping] for a simple static mapping.
///
/// @param <V> the [source type][TypeMapper#getSourceType()].
/// @since 1.0.0
public class SingleTypeMappingStatic<V> extends SingleTypeMapping<V> {

  private final TypeMapper<V, ?> typeMapper;

  /// The constructor for an [IdentityTypeMapper].
  ///
  /// @param type the [source and target type][IdentityTypeMapper#getSourceType()].
  public SingleTypeMappingStatic(Class<V> type) {

    this(new IdentityTypeMapper<>(type));
  }

  /// The constructor.
  ///
  /// @param sqlType the [TypeMapper] to return as static mapping.
  ///        [mapping][#getTypeMapper(io.github.mmm.property.ReadableProperty)].
  public SingleTypeMappingStatic(TypeMapper<V, ?> sqlType) {

    super();
    this.typeMapper = sqlType;
  }

  @Override
  public TypeMapper<V, ?> getTypeMapper() {

    return this.typeMapper;
  }

  @Override
  public TypeMapper<V, ?> getTypeMapper(ReadableProperty<?> property) {

    return this.typeMapper;
  }

}
