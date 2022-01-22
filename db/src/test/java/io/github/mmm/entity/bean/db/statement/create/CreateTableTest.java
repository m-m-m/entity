package io.github.mmm.entity.bean.db.statement.create;

import org.junit.jupiter.api.Test;

import io.github.mmm.entity.bean.db.statement.DbStatementTest;
import io.github.mmm.entity.bean.db.statement.Person;
import io.github.mmm.entity.bean.db.statement.Song;
import io.github.mmm.entity.id.LongId;
import io.github.mmm.entity.link.Link;

/**
 * Test of {@link CreateTable} and {@link CreateTableStatement}.
 */
public class CreateTableTest extends DbStatementTest {

  /** Test of {@link CreateTable} that automatically creates all columns. */
  @Test
  public void testAuto() {

    // given
    Song s = Song.of();
    // temporary workaround
    s.Composer().set(Link.of(LongId.of(4711L, Person.class)));
    // when
    CreateTableStatement<Song> createTableStatement = new CreateTable<>(s).columns().get();
    // then
    check(createTableStatement, "CREATE TABLE Song (\n" //
        + "  Composer Link,\n" //
        + "  Duration Long,\n" //
        + "  Genre String,\n" //
        + "  Id Id,\n" //
        + "  Title String,\n" //
        + "  TrackNo Integer,\n" //
        + "  CONSTRAINT FK_Song_Composer FOREIGN KEY (Composer) REFERENCES Person(Id),\n" //
        + "  CONSTRAINT NN_Song_Title NOT NULL (Title),\n" //
        + "  CONSTRAINT PK_Song PRIMARY KEY (Id)\n" //
        + ")");
  }

}
