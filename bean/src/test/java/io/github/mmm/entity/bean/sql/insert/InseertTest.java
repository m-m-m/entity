package io.github.mmm.entity.bean.sql.insert;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.Person;

/**
 * Test of {@link Insert} and {@link InsertStatement}.
 */
public class InseertTest extends Assertions {

  /** Test {@link Insert} with simple literal values. */
  @Test
  public void testInsertValues() {

    // given
    Person p = Person.of();
    // when
    InsertStatement<Person> insertStatement = new Insert().into(p).values(p.Name(), "John Doe").and(p.Single(), true)
        .andId(4711).get();
    // then
    assertThat(insertStatement).hasToString("INSERT INTO Person(Name, Single, Id) VALUES ('John Doe', TRUE, 4711)");
  }

}
