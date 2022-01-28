package io.github.mmm.entity.bean.db.typemapping;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.github.mmm.entity.bean.db.dialect.DbDialect;
import io.github.mmm.entity.bean.typemapping.ComposedTypeMapping;
import io.github.mmm.entity.id.IdMapper;
import io.github.mmm.entity.id.LongInstantId;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.entity.id.StringInstantId;
import io.github.mmm.entity.id.StringVersionId;
import io.github.mmm.entity.id.UuidInstantId;
import io.github.mmm.entity.id.UuidVersionId;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.IdentityTypeMapper;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Extends {@link ComposedTypeMapping} for {@link DbDialect database dialect} specific type mappings.
 *
 * @since 1.0.0
 */
public class DbTypeMapping extends ComposedTypeMapping {

  /**
   * The constructor.
   */
  public DbTypeMapping() {

    super();
    add(IdMapper.of(LongVersionId.getEmpty()));
    add(IdMapper.of(LongInstantId.getEmpty()));
    add(IdMapper.of(StringVersionId.getEmpty()));
    add(IdMapper.of(StringInstantId.getEmpty()));
    add(IdMapper.of(UuidVersionId.getEmpty()));
    add(IdMapper.of(UuidInstantId.getEmpty()));
  }

  /**
   * @param declarationAny the {@link TypeMapper#getDeclaration() declaration} for {@link String}s of any length (e.g.
   *        "CLOB").
   * @param declarationVariable the {@link TypeMapper#getDeclaration() declaration} for length limited {@link String}s
   *        (e.g. "VARCHAR(%s)" or "VARCHAR2(%s CHAR)").
   * @param declarationFixed the {@link TypeMapper#getDeclaration() declaration} for fixed length {@link String}s (e.g.
   *        "CHAR(%s)").
   */
  protected void addString(String declarationAny, String declarationVariable, String declarationFixed) {

    add(new SingleTypeMappingString(declarationAny, declarationVariable, declarationFixed));
  }

  /**
   * @param declarationAny the {@link TypeMapper#getDeclaration() declaration} for BLOBs of any length (e.g. "BLOB").
   * @param declarationVariable the {@link TypeMapper#getDeclaration() declaration} for length limited BLOBs (e.g.
   *        "BLOB(%s)").
   */
  protected void addBinary(String declarationAny, String declarationVariable) {

    add(new SingleTypeMappingBinary(declarationAny, declarationVariable));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property) {

    TypeMapper<V, ?> typeMapper = super.getTypeMapper(valueType, property);
    if (typeMapper == null) {

      if (BigDecimal.class.equals(valueType)) {
        typeMapper = new IdentityTypeMapper<>(valueType, getDeclarationDecimal(30, 10));
      } else if (BigInteger.class.equals(valueType)) {
        typeMapper = new IdentityTypeMapper<>(valueType, getDeclarationDecimal(36));
      }
    }
    return typeMapper;
  }

  /**
   * @param precision the total number of digits that can be stored.
   * @return the according {@link TypeMapper#getDeclaration() declaration}.
   */
  public final String getDeclarationDecimal(int precision) {

    return getDeclarationDecimal(precision, 0);
  }

  /**
   * @param precision the total number of digits that can be stored (on both sides of the decimal point). Has to be at
   *        least {@code 1}.
   * @param scale the number of digits that can be stored after the decimal point.
   * @return the according {@link TypeMapper#getDeclaration() declaration}.
   */
  public String getDeclarationDecimal(int precision, int scale) {

    if (!isValidDecimalPrecision(precision)) {
      throw new IllegalArgumentException("precision:" + precision);
    } else if (!isValidDecimalScale(scale, precision)) {
      throw new IllegalArgumentException("scale:" + scale);
    }
    return String.format(getDeclarationDecimalFormat(), precision, scale);
  }

  /**
   * @return the {@link String#format(String, Object...) String format} for {@link #getDeclarationDecimal(int, int)}
   *         (e.g. "DECIMAL(%s, %s)").
   */
  protected String getDeclarationDecimalFormat() {

    return "DECIMAL(%s, %s)";
  }

  /**
   * @param precision the precision.
   * @return {@code true} if the given {@code precision} is valid.
   * @see #getDeclarationDecimal(int, int)
   */
  protected boolean isValidDecimalPrecision(int precision) {

    return (precision >= 1);
  }

  /**
   * @param scale the number of digits that can be stored after the decimal point.
   * @param precision the precision.
   * @return {@code true} if the given {@code scale} is valid.
   * @see #getDeclarationDecimal(int, int)
   */
  protected boolean isValidDecimalScale(int scale, int precision) {

    return (scale >= 0) && (scale <= precision);
  }

}
