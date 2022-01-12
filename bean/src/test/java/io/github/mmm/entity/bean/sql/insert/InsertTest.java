package io.github.mmm.entity.bean.sql.insert;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.Person;
import io.github.mmm.entity.id.LongId;

/**
 * Test of {@link Insert} and {@link InsertStatement}.
 */
public class InsertTest extends Assertions {

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

  /** Test {@link Insert} with simple literal values. */
  @Test
  public void testInsertBeanValues() {

    // given
    Person p = Person.of();
    p.Name().set("John Doe");
    p.Single().setValue(true);
    p.Id().set(LongId.of(4711L));
    // when
    InsertStatement<Person> insertStatement = new Insert().into(p).values().get();
    // then
    assertThat(insertStatement).hasToString("INSERT INTO Person(Single, Id, Name) VALUES (TRUE, 4711, 'John Doe')");
  }

}
