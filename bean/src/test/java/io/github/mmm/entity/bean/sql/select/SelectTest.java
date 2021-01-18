/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import org.junit.jupiter.api.Test;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.sql.Person;
import io.github.mmm.entity.bean.sql.StatementTest;

/**
 * Test of {@link Select} and {@link SelectStatement}.
 */
public class SelectTest extends StatementTest {

  /** Test creation of {@link SelectStatement} and verifying resulting pseudo-SQL. */
  @Test
  public void testSelect() {

    // given
    String json = "{\"select\":{}," //
        + "\"from\":{\"entity\":\"Person\",\"as\":\"p\"}," //
        + "\"where\":{\"and\":[{\"op\":\">=\",\"args\":[{\"path\":\"p.Age\"},18]},"
        + "{\"op\":\"OR\",\"args\":[{\"op\":\"LIKE\",\"args\":[{\"path\":\"p.Name\"},\"John*\"]},{\"op\":\"=\",\"args\":[{\"path\":\"p.Single\"},true]}]}]}," //
        + "\"orderBy\":{\"o\":[{\"path\":\"p.Name\",\"order\":\"ASC\"}]}}";
    String sql = "SELECT * FROM Person p WHERE p.Age >= 18 AND (p.Name LIKE 'John*' OR p.Single = TRUE) ORDER BY p.Name ASC";
    // when
    Person p = BeanFactory.get().create(Person.class);
    SelectStatement<Person> query = new Select().from(p).as("p")
        .where(p.Age().ge(18).and(p.Name().like("John*").or(p.Single().eq(true)))).orderBy(p.Name().asc()).get();
    // then
    check(query, sql, json);
  }

}
