package io.github.mmm.entity.bean.db.columnmapping;

import io.github.mmm.entity.bean.db.DbNamingStrategy;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * TODO hohwille This type ...
 *
 */
public class DbColumnMapping {

  private final TypeMapping typeMapping;

  private final DbNamingStrategy namingStrategy;

  /**
   * The constructor.
   */
  public DbColumnMapping(TypeMapping typeMapping, DbNamingStrategy namingStrategy) {

    super();
    this.typeMapping = typeMapping;
    this.namingStrategy = namingStrategy;
  }

  public <V> void java2db(ReadableProperty<V> property, ColumnReceiver receiver, boolean receiveValue) {

    V value = null;
    if (receiveValue) {
      value = property.get();
    }
    java2dbRecursive(property, this.namingStrategy.getColumnName(property), receiver, value, null);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <T> void java2dbRecursive(ReadableProperty<?> property, String columnName, ColumnReceiver receiver, T value,
      Class<T> valueType) {

    TypeMapper<T, ?> typeMapper;
    if (valueType == null) {
      typeMapper = (TypeMapper) this.typeMapping.getTypeMapper(property);
      if (typeMapper == null) {
        valueType = (Class) property.getValueClass();
      }
    } else {
      typeMapper = this.typeMapping.getTypeMapper(valueType, property);
    }
    if (typeMapper == null) {
      throw new IllegalStateException("No type mapping could be found for ");
    }

  }

  /**
   * Callback-Interface to receive the results of
   * {@link DbColumnMapping#java2db(ReadableProperty, ColumnReceiver, boolean)}.
   */
  public static interface ColumnReceiver {

    /**
     * @param name the name of the column.
     * @param value the (optional) value for the column. Will always be {@code null} if {@code receiveValue} has been
     *        false on {@link DbColumnMapping#java2db(ReadableProperty, ColumnReceiver, boolean)}. May also be
     *        {@code null} because the value is {@code null}.
     * @param typeMapper the {@link TypeMapper} for this column.
     * @param property the original {@link ReadableProperty} containing the source value (that may be converted or
     *        decomposed).
     */
    void onColumn(String name, Object value, TypeMapper<?, ?> typeMapper, ReadableProperty<?> property);

  }

}
