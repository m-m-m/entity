package io.github.mmm.entity.bean.db.typemapping;

import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.IdentityTypeMapper;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of an empty {@link TypeMapping} for default database syntax.
 *
 * @since 1.0.0
 */
public class DbEmptyTypeMapping implements TypeMapping {

  private static final DbEmptyTypeMapping INSTANCE = new DbEmptyTypeMapping();

  @SuppressWarnings("rawtypes")
  private static final TypeMapper DEFAULT = new IdentityTypeMapper<>(Object.class, "");

  private DbEmptyTypeMapping() {

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
