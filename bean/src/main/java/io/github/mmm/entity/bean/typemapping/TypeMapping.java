/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.typemapping;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/// Interface to [retrieve][#getTypeMapper(Class, ReadableProperty)] a [TypeMapper] for a given [Class]
/// or [property][ReadableProperty].
///
/// @since 1.0.0
public interface TypeMapping {

  /// @param <V> type of the Java value.
  /// @param property the [ReadableProperty] to map.
  /// @return the [TypeMapper] for the given `property`. Will be `null` if the
  ///         [value class][ReadableProperty#getValueClass()] is not mapped.
  @SuppressWarnings({ "unchecked", "rawtypes" })
  default <V> TypeMapper<V, ?> getTypeMapper(ReadableProperty<V> property) {

    TypeMapper typeMapper = getTypeMapper(property.getValueClass(), property);
    if (typeMapper == null) {
      typeMapper = property.getTypeMapper();
    }
    return typeMapper;
  }

  /// @param <V> type of the Java value.
  /// @param valueType the [Class] reflecting the value type.
  /// @param property the optional [ReadableProperty] containing the type (directly or indirectly). May be
  ///        `null`.
  /// @return the [TypeMapper] for the given `Class`. Will be `null` if the given [Class] is not
  ///         mapped.
  default <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType) {

    return getTypeMapper(valueType, null);
  }

  /// @param <V> type of the Java value.
  /// @param valueType the [Class] reflecting the value type.
  /// @param property the optional [ReadableProperty] containing the type (directly or indirectly). May be
  ///        `null`.
  /// @return the [TypeMapper] for the given `Class`. Will be `null` if the given [Class] is not
  ///         mapped. If the [ReadableProperty] is not `null` it may be used for fine-tuning of the mapping.
  <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property);

}
