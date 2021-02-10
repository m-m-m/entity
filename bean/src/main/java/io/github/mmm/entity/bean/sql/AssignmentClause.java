/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongLatestId;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredReader.State;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.criteria.CriteriaMarshalling;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;

/**
 * {@link Clause} containing {@link #getAssignments() assignments} like a
 * {@link io.github.mmm.entity.bean.sql.insert.InsertValues}-clause.
 *
 * @param <E> type of the {@link EntityBean} to query.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class AssignmentClause<E extends EntityBean, SELF extends AssignmentClause<E, SELF>>
    extends AbstractTypedClause<E, SELF> {

  /** Name of the property {@link #getAssignments()} for marshaling. */
  public static final String NAME_ASSIGNMENTS = "set";

  private final List<PropertyAssignment<?>> assignments;

  /**
   * The constructor.
   */
  public AssignmentClause() {

    super();
    this.assignments = new ArrayList<>();
  }

  /**
   * @param assignment the {@link PropertyAssignment} to add.
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(PropertyAssignment<?> assignment) {

    Objects.requireNonNull(assignment, "assignment");
    this.assignments.add(assignment);
    return self();
  }

  /**
   * @param <V> type of the {@link PropertyPath#get() value}.
   * @param property the {@link PropertyPath property} to set.
   * @param value the {@link io.github.mmm.property.criteria.Literal} value to insert (assign the {@code property} to).
   * @return this {@link Clause} itself for fluent API calls.
   */
  public <V> SELF and(PropertyPath<V> property, V value) {

    return and(PropertyAssignment.of(property, value));
  }

  /**
   * @param propertyAssignments the {@link PropertyAssignment}s to add.
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(PropertyAssignment<?>... propertyAssignments) {

    for (PropertyAssignment<?> assignment : propertyAssignments) {
      and(assignment);
    }
    return self();
  }

  /**
   * @return the {@link EntityBean}.
   */
  protected abstract E getEntity();

  /**
   * @param id the {@link Id} value to set as {@link EntityBean#Id() primary key}.
   * @return this {@link Clause} itself for fluent API calls.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SELF andId(Id<? extends E> id) {

    // bug in Eclipse compiler
    // return and(getEntity().Id(), (Id) id);
    and(getEntity().Id(), (Id) id);
    return self();
  }

  /**
   * @param id the {@link EntityBean#Id() primary key} as {@link Long} value.
   * @return this {@link Clause} itself for fluent API calls.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SELF andId(long id) {

    Class<?> javaClass = getEntity().getType().getJavaClass();
    Id id2 = LongLatestId.of(javaClass, id);
    // bug in Eclipse compiler
    // return andId(id2);
    andId(id2);
    return self();
  }

  /**
   * @return the {@link List} of {@link PropertyAssignment}s.
   */
  public List<PropertyAssignment<?>> getAssignments() {

    return this.assignments;
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    if (this.assignments.isEmpty()) {
      writer.writeName(NAME_ASSIGNMENTS);
      writer.writeStartArray();
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      for (PropertyAssignment<?> assignment : this.assignments) {
        marshalling.writeAssignment(writer, assignment);
      }
      writer.writeEnd();
    }
    super.writeProperties(writer);
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    if (NAME_ASSIGNMENTS.equals(name)) {
      reader.require(State.START_ARRAY);
      CriteriaMarshalling marshalling = CriteriaMarshalling.get();
      while (!reader.readEnd()) {
        PropertyAssignment<?> assignment = marshalling.readAssignment(reader);
        this.assignments.add(assignment);
      }
    } else {
      super.readProperty(reader, name);
    }
  }

}
