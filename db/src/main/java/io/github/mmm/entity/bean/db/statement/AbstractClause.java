/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.marshall.AbstractMarshallingObject;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/**
 * Abstract base class implementing {@link DbClause}.
 *
 * @since 1.0.0
 */
public abstract class AbstractClause extends AbstractMarshallingObject implements DbClause {

  /**
   * The constructor.
   */
  public AbstractClause() {

    super();
  }

  /**
   * @return the name of the property for this clause.
   */
  protected abstract String getMarshallingName();

  @Override
  protected void writeProperties(StructuredWriter writer) {

    // nothing by default, override to extend.
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    // nothing by default, override to extend.
    reader.skipValue();
  }

  @Override
  public String toString() {

    return new StatementFormatter().onClause(this).toString();
  }
}
