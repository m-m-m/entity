/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import org.assertj.core.api.Assertions;

import io.github.mmm.marshall.JsonFormat;
import io.github.mmm.marshall.MarshallingConfig;
import io.github.mmm.marshall.StructuredFormat;

/**
 * Abstract base class for tests of {@link Statement}s.
 */
public abstract class StatementTest extends Assertions {

  /**
   * Formats the given {@link Statement} to SQL and compares with the given SQL, then marshals to JSON and compares with
   * the given JSON, the unmarshals from that JSON back to {@link Statement} and checks that this again results in the
   * same SQL.
   *
   * @param statement the {@link Statement} to check.
   * @param sql the expected SQL corresponding to the {@link Statement}.
   * @param json the expected JSON corresponding to the {@link Statement}.
   */
  protected void check(Statement<?> statement, String sql, String json) {

    assertThat(statement).isNotNull().hasToString(sql);
    StructuredFormat format = JsonFormat.of(MarshallingConfig.NO_INDENTATION);
    StringBuilder sb = new StringBuilder();
    statement.writeObject(format.writer(sb), statement);
    String actualJson = sb.toString();
    assertThat(actualJson).isEqualTo(json);
    Statement<?> statement2 = StatementMarshalling.get().readObject(format.reader(json));
    assertThat(statement2).isNotNull().hasToString(sql).isInstanceOf(statement.getClass());
  }

}
