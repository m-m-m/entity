/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.delete;

import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.db.statement.DbStatementTest;
import io.github.mmm.entity.bean.db.statement.Person;

/**
 * Test of {@link Delete} and {@link DeleteStatement}.
 */
public class DeleteTest extends DbStatementTest {

  /** Test of {@link Delete} for entire table. */
  @Test
  public void testDeleteAll() {

    // given
    Person p = Person.of();
    // when
    DeleteStatement<Person> deleteStatement = new Delete().from(p).get();
    // then
    check(deleteStatement, "DELETE FROM Person p");
  }

  /** Test of {@link Delete} with where clause. */
  @Test
  public void testDeleteWhere() {

    // given
    Person p = Person.of();
    // when
    DeleteStatement<Person> deleteStatement = new Delete().from(p).as("p").where(p.Single().eq(Boolean.TRUE)).get();
    // then
    check(deleteStatement, "DELETE FROM Person p WHERE p.Single = TRUE");
  }

}
