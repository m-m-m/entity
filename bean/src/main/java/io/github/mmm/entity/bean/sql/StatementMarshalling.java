/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.entity.bean.sql.delete.Delete;
import io.github.mmm.entity.bean.sql.delete.DeleteFrom;
import io.github.mmm.entity.bean.sql.insert.Insert;
import io.github.mmm.entity.bean.sql.insert.InsertInto;
import io.github.mmm.entity.bean.sql.merge.Merge;
import io.github.mmm.entity.bean.sql.select.Select;
import io.github.mmm.entity.bean.sql.select.SelectFrom;
import io.github.mmm.entity.bean.sql.update.Update;
import io.github.mmm.entity.impl.GenericSelect;
import io.github.mmm.marshall.Marshalling;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredReader.State;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.PropertyAssignment;

/**
 * {@link Marshalling} for SQL {@link Statement}s.
 *
 * @since 1.0.0
 */
public class StatementMarshalling implements Marshalling<Statement<?>> {

  private static final StatementMarshalling INSTANCE = new StatementMarshalling();

  /**
   * The constructor.
   */
  protected StatementMarshalling() {

    super();
  }

  @Override
  public void writeObject(StructuredWriter writer, Statement<?> statement) {

    if (statement == null) {
      writer.writeValueAsNull();
      return;
    }
    statement.write(writer);
  }

  @Override
  public Statement<?> readObject(StructuredReader reader) {

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
   *        {@link StartClause} and therefore identify the {@link Statement}.
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
   * @return the singleton instance of this {@link StatementMarshalling}.
   */
  public static StatementMarshalling get() {

    return INSTANCE;
  }

}
