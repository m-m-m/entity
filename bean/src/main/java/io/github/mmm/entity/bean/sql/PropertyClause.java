/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredReader.State;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.SimplePath;
import io.github.mmm.value.PropertyPath;

/**
 * {@link Clause} containing {@link #getProperties() properties} like a
 * {@link io.github.mmm.entity.bean.sql.select.GroupBy}-clause.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class PropertyClause<E, SELF extends PropertyClause<E, SELF>> extends AbstractTypedClause<E, SELF> {

  /** Name of {@link #getProperties()} for marshaling. */
  public static final String NAME_PROPERTIES = "p";

  /** @see #getProperties() */
  protected final List<PropertyPath<?>> properties;

  /**
   * The constructor.
   */
  public PropertyClause() {

    super();
    this.properties = new ArrayList<>();
  }

  /**
   * @param property the {@link PropertyPath} to add.
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(PropertyPath<?> property) {

    Objects.requireNonNull(property, "properety");
    this.properties.add(property);
    return self();
  }

  /**
   * @param paths the {@link PropertyPath}s to add.
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(PropertyPath<?>... paths) {

    for (PropertyPath<?> property : paths) {
      and(property);
    }
    return self();
  }

  /**
   * @return the {@link List} of {@link CriteriaPredicate}s.
   */
  public List<PropertyPath<?>> getProperties() {

    return this.properties;
  }

  @Override
  public boolean isOmit() {

    return getProperties().isEmpty();
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    super.writeProperties(writer);
    if (!this.properties.isEmpty()) {
      writer.writeName(NAME_PROPERTIES);
      writer.writeStartArray();
      for (PropertyPath<?> property : this.properties) {
        writer.writeValueAsString(property.path());
      }
      writer.writeEnd();
    }
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    if (NAME_PROPERTIES.equals(name)) {
      reader.require(State.START_ARRAY, true);
      while (!reader.readEnd()) {
        String path = reader.readValueAsString();
        this.properties.add(new SimplePath(path));
      }
    } else {
      super.readProperty(reader, name);
    }
  }

}
