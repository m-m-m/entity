/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.typemapping;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Interface to {@link #getTypeMapper(Class, ReadableProperty) retrieve} a {@link TypeMapper} for a given {@link Class}
 * or {@link ReadableProperty property}.
 *
 * @since 1.0.0
 */
public interface TypeMapping {

  /**
   * @param <V> type of the Java value.
   * @param property the {@link ReadableProperty} to map.
   * @return the {@link TypeMapper} for the given {@code property}. Will be {@code null} if the
   *         {@link ReadableProperty#getValueClass() value class} is not mapped.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  default <V> TypeMapper<V, ?> getTypeMapper(ReadableProperty<V> property) {

    TypeMapper typeMapper = getTypeMapper(property.getValueClass(), property);
    if (typeMapper == null) {
      typeMapper = property.getTypeMapper();
    }
    return typeMapper;
  }

  /**
   * @param <V> type of the Java value.
   * @param valueType the {@link Class} reflecting the value type.
   * @param property the optional {@link ReadableProperty} containing the type (directly or indirectly). May be
   *        {@code null}.
   * @return the {@link TypeMapper} for the given {@code Class}. Will be {@code null} if the given {@link Class} is not
   *         mapped.
   */
  default <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType) {

    return getTypeMapper(valueType, null);
  }

  /**
   * @param <V> type of the Java value.
   * @param valueType the {@link Class} reflecting the value type.
   * @param property the optional {@link ReadableProperty} containing the type (directly or indirectly). May be
   *        {@code null}.
   * @return the {@link TypeMapper} for the given {@code Class}. Will be {@code null} if the given {@link Class} is not
   *         mapped. If the {@link ReadableProperty} is not {@code null} it may be used for fine-tuning of the mapping.
   */
  <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property);

}
