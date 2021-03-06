package io.github.mmm.entity.bean.sql.delete;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.Person;

/**
 * Test of {@link Delete} and {@link DeleteStatement}.
 */
public class DeleteTest extends Assertions {

  /** Test of {@link Delete} for entire table. */
  @Test
  public void testDeleteAll() {

    // given
    Person p = Person.of();
    // when
    DeleteStatement<Person> deleteStatement = new Delete().from(p).get();
    // then
    assertThat(deleteStatement).hasToString("DELETE FROM Person");
  }

  /** Test of {@link Delete} with where clause. */
  @Test
  public void testDeleteWhere() {

    // given
    Person p = Person.of();
    // when
    DeleteStatement<Person> deleteStatement = new Delete().from(p).as("p").where(p.Single().eq(Boolean.TRUE)).get();
    // then
    assertThat(deleteStatement).hasToString("DELETE FROM Person p WHERE p.Single = TRUE");
  }

}
