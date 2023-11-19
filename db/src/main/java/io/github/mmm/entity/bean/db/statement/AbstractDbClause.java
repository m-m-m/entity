/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.entity.bean.db.statement.select.OrderBy;
import io.github.mmm.entity.bean.db.statement.select.Select;
import io.github.mmm.marshall.AbstractMarshallingObject;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.marshall.id.StructuredIdMapping;

/**
 * Abstract base class implementing {@link DbClause}.
 *
 * @since 1.0.0
 */
public abstract class AbstractDbClause extends AbstractMarshallingObject implements DbClause {

  /**
   * The constructor.
   */
  public AbstractDbClause() {

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
  public StructuredIdMapping defineIdMapping() {

    return StructuredIdMapping.of(AbstractEntitiesClause.NAME_ENTITY, AbstractEntitiesClause.NAME_ALIAS,
        AbstractEntitiesClause.NAME_ADDITIONAL_ENTITIES, AssignmentClause.NAME_ASSIGNMENTS, OrderBy.NAME_ORDER_BY,
        OrderBy.NAME_ORDERINGS, PredicateClause.NAME_PREDICATES, PropertyClause.NAME_PROPERTIES, Select.NAME_DISTINCT,
        Select.NAME_RESULT, Select.NAME_SELECT, Select.NAME_SELECTIONS);
  }

  @Override
  public Object asTypeKey() {

    return DbClause.class;
  }

  @Override
  public String toString() {

    return new DbStatementFormatter().onClause(this).toString();
  }
}
