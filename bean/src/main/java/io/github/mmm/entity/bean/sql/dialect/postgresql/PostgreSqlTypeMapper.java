package io.github.mmm.entity.bean.sql.dialect.postgresql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import io.github.mmm.entity.bean.sql.type.SqlTypeMapping;

/**
 * {@link SqlTypeMapping} for <a href="https://www.postgresql.org/docs/9.5/datatype.html">PostgreSQL</a>.
 *
 * @since 1.0.0
 */
public class PostgreSqlTypeMapper extends SqlTypeMapping {

  /**
   * The constructor.
   */
  public PostgreSqlTypeMapper() {

    super();
    add(Long.class, "int8");
    add(Integer.class, "int4");
    add(Short.class, "int2");
    add(Byte.class, "int2");
    add(Double.class, "float8");
    add(Float.class, "float4");
    add(BigDecimal.class, "numeric");
    add(BigInteger.class, "numeric(1000)");
    add(Boolean.class, "bool");
    add(Character.class, "char(1)");
    add(UUID.class, "uuid");
    add(Instant.class, "timestamp");
    add(OffsetDateTime.class, "timestamp with time zone");
    add(ZonedDateTime.class, "timestamp with time zone");
    add(LocalDate.class, "date");
    add(LocalTime.class, "time");
    add(OffsetTime.class, "time with time zone");
    add(LocalDateTime.class, "timestamp");
    add(Duration.class, "interval");
    addBinary("bytea", "bytea");
    addString("text", "varchar(%s)", "char(%s)");
  }

  @Override
  protected String getDeclarationDecimalFormat() {

    return "numeric(%s, %s)";
  }

}
