/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.entity.bean.db.statement.delete.Delete;
import io.github.mmm.entity.bean.db.statement.delete.DeleteFrom;
import io.github.mmm.entity.bean.db.statement.insert.Insert;
import io.github.mmm.entity.bean.db.statement.insert.InsertInto;
import io.github.mmm.entity.bean.db.statement.merge.Merge;
import io.github.mmm.entity.bean.db.statement.select.Select;
import io.github.mmm.entity.bean.db.statement.select.SelectFrom;
import io.github.mmm.entity.bean.db.statement.update.Update;
import io.github.mmm.entity.db.impl.GenericSelect;
import io.github.mmm.marshall.Marshalling;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredReader.State;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.PropertyAssignment;

/**
 * {@link Marshalling} for {@link DbStatement}s.
 *
 * @since 1.0.0
 */
public class DbStatementMarshalling implements Marshalling<DbStatement<?>> {

  private static final DbStatementMarshalling INSTANCE = new DbStatementMarshalling();

  /**
   * The constructor.
   */
  protected DbStatementMarshalling() {

    super();
  }

  @Override
  public void writeObject(StructuredWriter writer, DbStatement<?> statement) {

    if (statement == null) {
      writer.writeValueAsNull();
      return;
    }
    statement.write(writer);
  }

  @Override
  public DbStatement<?> readObject(StructuredReader reader) {

    reader.require(State.START_OBJECT, true);
    AbstractStatement<?> statement = null;
    while (!reader.readEnd()) {
      String name = reader.readName();
      if (statement == null) {
        statement = createStatement(name);
      }
      statement.readProperty(reader, name);
    }
    return statement;
  }

  /**
   * @param name the name of the first property of the statement. This should always correspond to the
   *        {@link StartClause} and therefore identify the {@link DbStatement}.
   * @return the new {@link AbstractStatement} with the given {@code name}.
   */
  @SuppressWarnings("unchecked")
  protected AbstractStatement<?> createStatement(String name) {

    if (Select.NAME_SELECT.equals(name)) {
      return new SelectFrom<>(new GenericSelect<>(), null).get();
    } else if (Update.NAME_UPDATE.equals(name)) {
      return new Update<>(null).get();
    } else if (Insert.NAME_INSERT.equals(name)) {
      return new InsertInto<>(new Insert(), null).values((PropertyAssignment[]) PropertyAssignment.EMPTY_ARRAY).get();
    } else if (Delete.NAME_DELETE.equals(name)) {
      return new DeleteFrom<>(new Delete(), null).get();
    } else if (Merge.NAME_MERGE.equals(name)) {
      return new DeleteFrom<>(new Delete(), null).get();
    } else {
      throw new IllegalStateException("Unknown statement: " + name);
    }
  }

  /**
   * @return the singleton instance of this {@link DbStatementMarshalling}.
   */
  public static DbStatementMarshalling get() {

    return INSTANCE;
  }

}
