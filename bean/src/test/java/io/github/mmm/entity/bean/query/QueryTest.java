/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.bean.BeanFactory;

/**
 *
 */
public class QueryTest extends Assertions {

  @Test
  public void testSelectQuery() {

    Person p = BeanFactory.get().create(Person.class);
    Query<Person> query = Select.from(p).as("p").where(p.Age().ge(18));
    assertThat(query.toString()).isEqualTo("SELECT p FROM Person p WHERE p.Age >= 18");
  }

}
