/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.mmm.marshall.AbstractMarshallingObject;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/**
 * Abstract base implementation of an SQL {@link DbStatement} that may be executed to the database.
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public abstract class AbstractStatement<E> extends AbstractMarshallingObject implements DbStatement<E> {

  private List<DbClause> clauses;

  @Override
  public List<DbClause> getClauses() {

    if (this.clauses == null) {
      List<DbClause> list = new ArrayList<>();
      addClauses(list);
      this.clauses = Collections.unmodifiableList(list);
    }
    return this.clauses;
  }

  /**
   * @param list the {@link List} where to {@link List#add(Object) add} the {@link DbClause}s.
   * @see #getClauses()
   */
  protected abstract void addClauses(List<DbClause> list);

  @Override
  protected void writeProperties(StructuredWriter writer) {

    for (DbClause clause : getClauses()) {
      if (!clause.isOmit()) {
        String name = ((AbstractClause) clause).getMarshallingName();
        writer.writeName(name);
        clause.write(writer);
      }
    }
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    for (DbClause clause : getClauses()) {
      String clauseName = ((AbstractClause) clause).getMarshallingName();
      if (clauseName.equals(name)) {
        clause.read(reader);
        return;
      }
    }
    reader.skipValue();
  }

  @Override
  public String toString() {

    return new StatementFormatter().onStatement(this).toString();
  }

}
