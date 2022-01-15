package io.github.mmm.entity.bean.db.dialect.h2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import io.github.mmm.entity.bean.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for <a href="http://www.h2database.com/html/datatypes.html">H2 database</a>.
 *
 */
public class H2TypeMapper extends DbTypeMapping {

  /**
   * The constructor.
   */
  public H2TypeMapper() {

    super();
    add(Long.class, "BIGINT");
    add(Integer.class, "INTEGER");
    add(Short.class, "SMALLINT");
    add(Byte.class, "TINYINT");
    add(Double.class, "float8");
    add(Float.class, "float4");
    add(BigDecimal.class, "NUMERIC");
    add(BigInteger.class, "NUMERIC(100000)");
    add(Boolean.class, "BOOLEAN");
    add(Character.class, "CHAR");
    add(UUID.class, "UUID");
    add(Instant.class, "TIMESTAMP");
    add(OffsetDateTime.class, "TIMESTAMP WITH TIME ZONE");
    add(ZonedDateTime.class, "TIMESTAMP WITH TIME ZONE");
    add(LocalDate.class, "DATE");
    add(LocalTime.class, "TIME");
    add(OffsetTime.class, "TIME WITH TIME ZONE");
    add(LocalDateTime.class, "DATETIME");
    addBinary("BINARY", "BINARY(%s)");
    addString("VARCHAR", "VARCHAR(%s)", "CHAR(%s)");
  }

  @Override
  protected String getDeclarationDecimalFormat() {

    return "NUMERIC(%s, %s)";
  }

}
