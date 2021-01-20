/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql.select;

import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.Person;
import io.github.mmm.entity.bean.Song;
import io.github.mmm.entity.bean.sql.SqlFormatter;
import io.github.mmm.entity.bean.sql.StatementTest;
import io.github.mmm.property.criteria.CriteriaAggregation;
import io.github.mmm.property.criteria.CriteriaSqlFormatter;
import io.github.mmm.property.criteria.CriteriaSqlParametersNamed;
import io.github.mmm.property.temporal.DurationInSecondsProperty;

/**
 * Test of {@link Select} and {@link SelectStatement}.
 */
public class SelectTest extends StatementTest {

  /** Test creation of {@link SelectStatement} and verifying resulting pseudo-SQL. */
  @Test
  public void testSelectSqlAndJson() {

    // given
    Person p = Person.of();
    String json = "{\"select\":{}," //
        + "\"from\":{\"entity\":\"Person\",\"as\":\"p\"}," //
        + "\"where\":{\"and\":[{\"op\":\">=\",\"args\":[{\"path\":\"p.Age\"},18]},"
        + "{\"op\":\"OR\",\"args\":[{\"op\":\"LIKE\",\"args\":[{\"path\":\"p.Name\"},\"John*\"]},{\"op\":\"=\",\"args\":[{\"path\":\"p.Single\"},true]}]}]}," //
        + "\"orderBy\":{\"o\":[{\"path\":\"p.Name\",\"order\":\"ASC\"}]}}";
    String sql = "SELECT * FROM Person p WHERE p.Age >= 18 AND (p.Name LIKE 'John%' OR p.Single = TRUE) ORDER BY p.Name ASC";
    // when
    SelectStatement<Person> query = new Select().from(p).as("p")
        .where(p.Age().ge(18).and(p.Name().like("John*").or(p.Single().eq(true)))).orderBy(p.Name().asc()).get();
    // then
    check(query, sql, json);
    // and when
    SqlFormatter sqlFormatter = new SqlFormatter(CriteriaSqlFormatter.ofNamedParameters(null));
    sqlFormatter.setSelectAllByAlias(true);
    sqlFormatter.setUseAsbeforeAlias(true);
    assertThat(sqlFormatter.onStatement(query).toString()).isEqualTo(
        "SELECT p FROM Person AS p WHERE p.Age >= :Age AND (p.Name LIKE :Name OR p.Single = :Single) ORDER BY p.Name ASC");
    CriteriaSqlParametersNamed parameters = sqlFormatter.getCriteriaFormatter().getParameters().cast();
    assertThat(parameters.getParameters()).containsEntry("Age", 18).containsEntry("Name", "John%")
        .containsEntry("Single", Boolean.TRUE).hasSize(3);
  }

  /** Test creation of {@link SelectStatement} and verifying resulting pseudo-SQL. */
  @Test
  public void testSelectAll() {

    // given
    Person p = Person.of();
    // when
    SelectStatement<Person> query = new Select().from(p).as("p").get();
    // then
    assertThat(query).hasToString("SELECT * FROM Person p");
  }

  /** Test creation of {@link SelectStatement} and verifying resulting pseudo-SQL. */
  @Test
  public void testSelectComplex() {

    // given
    Person p = Person.of();
    Song s = Song.of();
    DurationInSecondsProperty duration2 = s.Duration();
    // when
    CriteriaAggregation<Integer> count = s.Id().count();
    SelectStatement<Song> query = new Select(s.Genre()).and(count, duration2.avg()) //
        .from(s).as("song").and(p, "p") //
        .where(s.Composer().eq(p.Id().cast()).and(duration2.le(3 * 60 * 60L))) //
        .groupBy(s.Genre()).orderBy(s.Genre().asc()).get();
    // then
    query.toString();
    assertThat(query).hasToString(
        "SELECT (song.Genre, COUNT(song.Id), AVG(song.Duration)) FROM Song song, Person p WHERE song.Composer = p.Id AND song.Duration <= 10800 ORDER BY song.Genre ASC");
  }

}
