package io.github.mmm.entity.bean.db.statement.update;

import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.db.statement.DbStatementTest;
import io.github.mmm.entity.bean.db.statement.Person;
import io.github.mmm.entity.id.LongId;

/**
 * Test of {@link Update} and {@link UpdateStatement}.
 */
public class UpdateTest extends DbStatementTest {

  /** Test of {@link Update} for entire table. */
  @Test
  public void testUpdateAll() {

    // given
    Person p = Person.of();
    // when
    UpdateStatement<Person> updateStatement = new Update<>(p).as("p").set(p.Single(), Boolean.TRUE).get();
    // then
    check(updateStatement, "UPDATE Person p SET p.Single=TRUE");
  }

  /** Test of {@link Update} for with {@link UpdateWhere} clause. */
  @Test
  public void testUpdateWhere() {

    // given
    Person p = Person.of();
    // when
    UpdateStatement<Person> updateStatement = new Update<>(p).as("p").set(p.Single(), Boolean.FALSE)
        .where(p.Id().eq(LongId.of(4711L))).get();
    // then
    check(updateStatement, "UPDATE Person p SET p.Single=FALSE WHERE p.Id = 4711");
  }

}
