/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import org.assertj.core.api.Assertions;

import io.github.mmm.entity.bean.db.statement.DbStatement;
import io.github.mmm.entity.bean.db.statement.StatementMarshalling;
import io.github.mmm.marshall.JsonFormat;
import io.github.mmm.marshall.MarshallingConfig;
import io.github.mmm.marshall.StructuredTextFormat;

/**
 * Abstract base class for tests of {@link DbStatement}s.
 */
public abstract class StatementTest extends Assertions {

  /**
   * Formats the given {@link DbStatement} to SQL and compares with the given SQL.
   *
   * @param statement the {@link DbStatement} to check.
   * @param sql the expected SQL corresponding to the {@link DbStatement}.
   */
  protected void check(DbStatement<?> statement, String sql) {

    assertThat(statement).isNotNull().hasToString(sql);
  }

  /**
   * Formats the given {@link DbStatement} to SQL and compares with the given SQL, then marshals to JSON and compares with
   * the given JSON, the unmarshals from that JSON back to {@link DbStatement} and checks that this again results in the
   * same SQL.
   *
   * @param statement the {@link DbStatement} to check.
   * @param sql the expected SQL corresponding to the {@link DbStatement}.
   * @param json the expected JSON corresponding to the {@link DbStatement}.
   */
  protected void check(DbStatement<?> statement, String sql, String json) {

    check(statement, sql);
    StructuredTextFormat format = JsonFormat.of(MarshallingConfig.NO_INDENTATION);
    StringBuilder sb = new StringBuilder();
    statement.writeObject(format.writer(sb), statement);
    String actualJson = sb.toString();
    assertThat(actualJson).isEqualTo(json);
    DbStatement<?> statement2 = StatementMarshalling.get().readObject(format.reader(json));
    assertThat(statement2).isNotNull().hasToString(sql).isInstanceOf(statement.getClass());
  }

}
