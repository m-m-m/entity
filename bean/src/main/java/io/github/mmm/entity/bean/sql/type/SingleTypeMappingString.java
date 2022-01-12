package io.github.mmm.entity.bean.sql.type;

import io.github.mmm.base.range.Range;
import io.github.mmm.entity.bean.typemapping.SingleTypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.IdentityTypeMapper;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link SingleTypeMapping} for a {@link String}.
 *
 * @since 1.0.0
 */
public class SingleTypeMappingString extends SingleTypeMapping<String> {

  private final IdentityTypeMapper<String> typeMapper;

  private final String sqlTypeVariable;

  private final String sqlTypeFixed;

  /**
   * The constructor.
   *
   * @param declarationAny the SQL type for {@link String}s of any length (e.g. "TEXT" or "VARCHAR(MAX)").
   * @param declarationVariable the SQL type for length limited {@link String}s (e.g. "VARCHAR(%s)" or "VARCHAR2(%s
   *        CHAR)").
   * @param declarationFixed the SQL type for fixed length {@link String}s (e.g. "ChAR(%s)").
   */
  public SingleTypeMappingString(String declarationAny, String declarationVariable, String declarationFixed) {

    super();
    this.typeMapper = new IdentityTypeMapper<>(String.class, declarationAny);
    this.sqlTypeVariable = declarationVariable;
    this.sqlTypeFixed = declarationFixed;
  }

  @Override
  public TypeMapper<String, ?> getTypeMapper() {

    return this.typeMapper;
  }

  @Override
  public TypeMapper<String, ?> getTypeMapper(ReadableProperty<?> property) {

    Range<Integer> range = getRange(property);
    Integer max = range.getMax();
    if (max == null) {
      return this.typeMapper;
    } else {
      Integer min = range.getMin();
      if (max.equals(min)) {
        return new IdentityTypeMapper<>(String.class, String.format(this.sqlTypeFixed, max));
      }
      return new IdentityTypeMapper<>(String.class, String.format(this.sqlTypeVariable, max));
    }
  }

}
