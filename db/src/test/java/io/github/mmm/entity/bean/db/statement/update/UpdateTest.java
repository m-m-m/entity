/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement.update;

import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.db.statement.DbStatementTest;
import io.github.mmm.entity.bean.db.statement.Person;
import io.github.mmm.entity.bean.db.statement.Song;
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

  /** Test of {@link Update} for with data from other entity. */
  @Test
  public void testUpdateFromJoinTable() {

    // given
    Song s = Song.of();
    Person p = Person.of();
    // when
    UpdateStatement<Song> updateStatement = new Update<>(s).as("s").and(p).as("p").set(s.Title(), p.Name())
        .where(s.Composer().eq(p.Id())).get();
    // then
    check(updateStatement, "UPDATE Song s, Person p SET s.Title=p.Name WHERE s.Composer = p.Id");
  }

}
