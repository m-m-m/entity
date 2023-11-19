/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.mmm.marshall.AbstractMarshallingObject;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.marshall.id.StructuredIdMapping;

/**
 * Abstract base implementation of an SQL {@link DbStatement} that may be executed to the database.
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public abstract class AbstractDbStatement<E> extends AbstractMarshallingObject implements DbStatement<E> {

  private List<AbstractDbClause> clauses;

  @Override
  public List<? extends DbClause> getClauses() {

    if (this.clauses == null) {
      List<AbstractDbClause> list = new ArrayList<>();
      addClauses(list);
      this.clauses = Collections.unmodifiableList(list);
    }
    return this.clauses;
  }

  /**
   * @param list the {@link List} where to {@link List#add(Object) add} the {@link DbClause}s.
   * @see #getClauses()
   */
  protected abstract void addClauses(List<AbstractDbClause> list);

  @Override
  protected void writeProperties(StructuredWriter writer) {

    getClauses(); // lazy init
    for (AbstractDbClause clause : this.clauses) {
      if (!clause.isOmit()) {
        String name = clause.getMarshallingName();
        writer.writeName(name);
        clause.write(writer);
      }
    }
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    getClauses(); // lazy init
    for (AbstractDbClause clause : this.clauses) {
      String clauseName = clause.getMarshallingName();
      if (reader.isNameMatching(name, clauseName)) {
        clause.read(reader);
        return;
      }
    }
    reader.skipValue();
  }

  /**
   * @return the {@link AliasMap} of this statement.
   */
  protected abstract AliasMap getAliasMap();

  @Override
  public StructuredIdMapping defineIdMapping() {

    String[] names = new String[getClauses().size()];
    int i = 0;
    for (AbstractDbClause clause : this.clauses) {
      names[i++] = clause.getMarshallingName();
    }
    return StructuredIdMapping.of(names);
  }

  @Override
  public String toString() {

    return new DbStatementFormatter().onStatement(this).toString();
  }

}
