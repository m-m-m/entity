package io.github.mmm.entity.bean.typemapping;

import io.github.mmm.base.range.Range;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Abstract base class similar to {@link TypeMapping} but only for a single {@link TypeMapper#getSourceType() source
 * type}.
 *
 * @param <V> the {@link TypeMapper#getSourceType() source type}.
 * @since 1.0.0
 */
public abstract class SingleTypeMapping<V> {

  /**
   * @return the static or default {@link TypeMapper} instance.
   */
  public abstract TypeMapper<V, ?> getTypeMapper();

  /**
   * @param <V> type of the {@link Range} bounds.
   * @param property the {@link ReadableProperty}. May be {@code null}.
   * @return the {@link Range}.
   */
  protected static <V extends Comparable<?>> Range<V> getRange(ReadableProperty<?> property) {

    if (property != null) {
      return property.getMetadata().getValidator().getRange();
    }
    return Range.unbounded();
  }

  /**
   * @param property the optional {@link ReadableProperty} containing the type (directly or indirectly). May be
   *        {@code null}.
   * @return the {@link TypeMapper} for the given {@link ReadableProperty}. Will be {@code null} if the given
   *         {@link ReadableProperty#getValueClass() value type} is not mapped.
   */
  public abstract TypeMapper<V, ?> getTypeMapper(ReadableProperty<?> property);

  @Override
  public String toString() {

    return getClass().getSimpleName() + "[" + getTypeMapper() + "]";
  }

}
