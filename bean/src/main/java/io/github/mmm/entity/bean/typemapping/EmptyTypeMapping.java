/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.typemapping;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Empty implementation of {@link TypeMapping}.
 *
 * @since 1.0.0
 */
public class EmptyTypeMapping implements TypeMapping {

  private static final EmptyTypeMapping INSTANCE = new EmptyTypeMapping();

  private EmptyTypeMapping() {

    super();
  }

  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property) {

    return null;
  }

  /**
   * @return the singleton instance.
   */
  public static TypeMapping get() {

    return INSTANCE;
  }

}
