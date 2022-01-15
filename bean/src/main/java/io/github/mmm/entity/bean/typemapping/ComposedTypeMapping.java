package io.github.mmm.entity.bean.typemapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link TypeMapping} that is composed out of multiple {@link SingleTypeMapping}s.
 *
 * @since 1.0.0
 */
public class ComposedTypeMapping implements TypeMapping {

  private static final Logger LOG = LoggerFactory.getLogger(ComposedTypeMapping.class);

  private Map<Class<?>, SingleTypeMapping<?>> typeMap;

  private final Map<Class<?>, Class<?>> classMap;

  /**
   * The constructor.
   */
  public ComposedTypeMapping() {

    super();
    this.typeMap = new HashMap<>();
    this.classMap = new ConcurrentHashMap<>();
  }

  /**
   * @param source the source {@link Class} to remap.
   * @param target the corresponding basic standard Java datatype {@link Class}.
   */
  private void remap(Class<?> source, Class<?> target) {

    assert (source != target);
    Objects.requireNonNull(source);
    Objects.requireNonNull(target);
    Class<?> duplicate = this.classMap.put(source, target);
    if ((duplicate != null) && (duplicate != target)) {
      LOG.warn(
          "Overriding mapping of " + source.getName() + " to " + duplicate.getName() + " with " + target.getName());
    }
  }

  /**
   * @param singleMapper the {@link SingleTypeMapping} to register.
   */
  protected void add(SingleTypeMapping<?> singleMapper) {

    SingleTypeMapping<?> duplicate = this.typeMap.put(singleMapper.getTypeMapper().getSourceType(), singleMapper);
    if (duplicate != null) {
      LOG.warn("Overriding " + duplicate + " with " + singleMapper);
    }
  }

  /**
   * @param type the {@link TypeMapper#getSourceType() source type} to map.
   * @param declaration the {@link TypeMapper#getDeclaration() declaration}.
   */
  protected void add(Class<?> type, String declaration) {

    add(new SingleTypeMappingStatic<>(type, declaration));
  }

  /**
   * @param typeMapper the {@link TypeMapper} to add.
   */
  protected void add(TypeMapper<?, ?> typeMapper) {

    add(new SingleTypeMappingStatic<>(typeMapper));
  }

  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property) {

    SingleTypeMapping<V> singleMapping = getSingleMapping(valueType);
    if (singleMapping == null) {
      return null;
    }
    return singleMapping.getTypeMapper(property);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private <V> SingleTypeMapping<V> getSingleMapping(Class<V> valueType) {

    SingleTypeMapping<V> singleMapping = (SingleTypeMapping) this.typeMap.get(valueType);
    if (singleMapping == null) {
      Class<?> remappedClass = this.classMap.get(valueType);
      if (remappedClass == null) {
        remappedClass = Object.class;
        for (Class<?> type : this.typeMap.keySet()) {
          if (type.isAssignableFrom(valueType)) {
            remappedClass = type;
            break;
          }
        }
        remap(valueType, remappedClass);
      }
      if (remappedClass == Object.class) {
        return null;
      }
      singleMapping = (SingleTypeMapping) this.typeMap.get(remappedClass);
    }
    return singleMapping;
  }

  /**
   * Secures this class to avoid further undesired manipulations.
   */
  public void secure() {

    this.typeMap = Collections.unmodifiableMap(this.typeMap);
  }

}
