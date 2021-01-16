/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.marshall.JsonFormat;
import io.github.mmm.marshall.StructuredFormat;

/**
 * Test of {@link Select} and {@link Query}.
 */
public class QueryTest extends Assertions {

  /** Test creation of {@link Query} and verifying resulting pseudo-SQL. */
  @Test
  public void testSelectQuery() {

    Person p = BeanFactory.get().create(Person.class);
    Query<Person> query = Select.from(p).as("p")
        .where(p.Age().ge(18).and(p.Name().like("John*").or(p.Single().eq(true)))).orderBy(p.Name().asc());
    assertThat(query.toString()).isEqualTo(
        "SELECT p FROM Person p WHERE p.Age >= 18 AND (p.Name LIKE 'John*' OR p.Single = TRUE) ORDER BY p.Name ASC");

    QueryMarshalling marshalling = QueryMarshalling.get();
    StructuredFormat format = JsonFormat.of();
    StringBuilder sb = new StringBuilder();
    marshalling.writeObject(format.writer(sb), query);
    String json = sb.toString();
    assertThat(json).isEqualTo("{\n" //
        + "  \"from\": \"Person\",\n" //
        + "  \"as\": \"p\",\n" //
        + "  \"where\": [{\n" //
        + "      \"op\": \">=\",\n" //
        + "      \"args\": [{\n" //
        + "          \"path\": \"p.Age\"\n" //
        + "        },\n" //
        + "        18\n" //
        + "      ]\n" //
        + "    },\n" //
        + "    {\n" //
        + "      \"op\": \"OR\",\n" //
        + "      \"args\": [{\n" //
        + "          \"op\": \"LIKE\",\n" //
        + "          \"args\": [{\n" //
        + "              \"path\": \"p.Name\"\n" //
        + "            },\n" //
        + "            \"John*\"\n" //
        + "          ]\n" //
        + "        },\n" //
        + "        {\n" //
        + "          \"op\": \"=\",\n" //
        + "          \"args\": [{\n" //
        + "              \"path\": \"p.Single\"\n" //
        + "            },\n" //
        + "            true\n" //
        + "          ]\n" //
        + "        }\n" //
        + "      ]\n" //
        + "    }\n" //
        + "  ],\n" //
        + "  \"orderBy\": [{\n" //
        + "      \"path\": \"p.Name\",\n" //
        + "      \"order\": \"ASC\"\n" //
        + "    }\n" //
        + "  ]\n" //
        + "}");
    Query<?> query2 = marshalling.readObject(format.reader(json));
    assertThat(query2).isNotNull();
    assertThat(query2.toString()).isEqualTo(query.toString());
  }

}
