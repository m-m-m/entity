package io.github.mmm.entity.bean.sql.type;

import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.IdentityTypeMapper;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link TypeMapping}.
 *
 * @since 1.0.0
 */
public class NoSqlTypeMapping implements TypeMapping {

  private static final NoSqlTypeMapping INSTANCE = new NoSqlTypeMapping();

  @SuppressWarnings("rawtypes")
  private static final TypeMapper DEFAULT = new IdentityTypeMapper<>(Object.class, "");

  private NoSqlTypeMapping() {

    super();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property) {

    return DEFAULT;
  }

  /**
   * @return the singleton instance.
   */
  public static TypeMapping get() {

    return INSTANCE;
  }

}
