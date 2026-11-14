/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.typemapping;

import io.github.mmm.base.range.Range;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/// Abstract base class similar to [TypeMapping] but only for a single [source type][TypeMapper#getSourceType()].
///
/// @param <V> the [source type][TypeMapper#getSourceType()].
/// @since 1.0.0
public abstract class SingleTypeMapping<V> {

  /// @return the static or default [TypeMapper] instance.
  public abstract TypeMapper<V, ?> getTypeMapper();

  /// @param property the optional [ReadableProperty] containing the type (directly or indirectly). May be `null`.
  /// @return the [TypeMapper] for the given [ReadableProperty]. Will be `null` if the given
  ///         [value type][ReadableProperty#getValueClass()] is not mapped.
  public abstract TypeMapper<V, ?> getTypeMapper(ReadableProperty<?> property);

  @Override
  public String toString() {

    return getClass().getSimpleName() + "[" + getTypeMapper() + "]";
  }

  /// @param <V> type of the [Range] bounds.
  /// @param property the [ReadableProperty]. May be `null`.
  /// @return the [Range].
  protected static <V extends Comparable<?>> Range<V> getRange(ReadableProperty<?> property) {

    if (property != null) {
      return property.getMetadata().getValidator().getRange();
    }
    return Range.unbounded();
  }

}
