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

/**
 * A {@link AbstractEntitiesClause} is a {@link Clause} of an SQL {@link Statement} that specifies the
 * {@link #getEntity() entity} and/or {@link #getEntityName() entity name} (table) to operate on.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class AbstractEntitiesClause<E extends EntityBean, SELF extends AbstractEntitiesClause<E, SELF>>
    extends AbstractEntityClause<E, SELF> {

  /** Name of property {@link #getEntityName()} for marshalling. */
  public static final String NAME_ADDITIONAL_ENTITIES = "and";

  private final List<EntitySubClause<?>> additionalEntities;

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity}.
   */
  protected AbstractEntitiesClause(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  protected AbstractEntitiesClause(E entity, String entityName) {

    super(entity, entityName);
    this.additionalEntities = new ArrayList<>();
  }

  /**
   * @param additionalEntity the additional {@link EntitySubClause#getEntity() entity} to access (similar to a JOIN).
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(EntityBean additionalEntity) {

    return and(new EntitySubClause<>(additionalEntity, null, null));
  }

  /**
   * @param additionalEntity the additional {@link EntitySubClause#getEntity() entity} to access (similar to a JOIN).
   * @param alias the {@link EntitySubClause#getAlias() alias}.
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(EntityBean additionalEntity, String alias) {

    return and(new EntitySubClause<>(additionalEntity, null, alias));
  }

  /**
   * @param entityName the {@link EntitySubClause#getEntityName() entity name}.
   * @param additionalEntity the additional {@link EntitySubClause#getEntity() entity} to access (similar to a JOIN).
   * @param alias the {@link EntitySubClause#getAlias() alias}.
   * @return this {@link Clause} itself for fluent API calls.
   */
  public SELF and(String entityName, EntityBean additionalEntity, String alias) {

    return and(new EntitySubClause<>(additionalEntity, entityName, alias));
  }

  /**
   * @param entityFragment the {@link EntitySubClause} to add.
   * @return this {@link Clause} itself for fluent API calls.
   */
  protected SELF and(EntitySubClause<?> entityFragment) {

    Objects.requireNonNull(entityFragment);
    this.additionalEntities.add(entityFragment);
    return self();
  }

  /**
   * @return the {@link List} of {@link EntitySubClause}s in addition to the {@link #getEntity() main entity}.
   */
  public List<EntitySubClause<?>> getAdditionalEntities() {

    return this.additionalEntities;
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    super.writeProperties(writer);
    if (!this.additionalEntities.isEmpty()) {
      writer.writeName(NAME_ADDITIONAL_ENTITIES);
      writer.writeStartArray();
      for (EntitySubClause<?> additionalEntity : this.additionalEntities) {
        additionalEntity.write(writer);
        this.additionalEntities.add(additionalEntity);
      }
      writer.writeEnd();
    }
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    if (NAME_ADDITIONAL_ENTITIES.equals(name)) {
      reader.require(State.START_ARRAY);
      while (!reader.readEnd()) {
        EntitySubClause<EntityBean> additionalEntity = new EntitySubClause<>(null);
        additionalEntity.read(reader);
      }
    } else {
      super.readProperty(reader, name);
    }
  }

}
