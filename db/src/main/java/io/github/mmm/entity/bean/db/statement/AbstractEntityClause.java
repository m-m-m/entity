/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import java.util.Map;
import java.util.Objects;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/**
 * A {@link AbstractEntityClause} is a {@link DbClause} of an SQL {@link DbStatement} that specifies the
 * {@link #getEntity() entity} and/or {@link #getEntityName() entity name} (table) to operate on.
 *
 * @param <R> type of the result. Only different from {@literal <E>} for complex selects.
 * @param <E> type of the {@link #getEntity() entity}.
 * @param <SELF> type of this class itself.
 * @since 1.0.0
 */
public abstract class AbstractEntityClause<R, E extends EntityBean, SELF extends AbstractEntityClause<R, E, SELF>>
    extends AbstractTypedClause<R, SELF> {

  /** Name of property {@link #getEntityName()} for marshalling. */
  public static final String NAME_ENTITY = "entity";

  /** Name of property {@link #getAlias() alias} for marshaling. */
  public static final String NAME_ALIAS = "as";

  private final AliasMap aliasMap;

  /** @see #getEntityName() */
  protected transient E entity;

  private String entityName;

  private String alias;

  /**
   * The constructor.
   *
   * @param aliasMap the {@link AliasMap}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  protected AbstractEntityClause(AliasMap aliasMap, E entity, String entityName) {

    super();
    Objects.requireNonNull(aliasMap);
    this.aliasMap = aliasMap;
    setEntity(entity, entityName);
  }

  /**
   * @return the {@link Map} to {@link Map#get(Object) map} from {@link #getAlias() alias} to {@link #getEntity()
   *         entity}. A single {@link Map} instance is used per {@link DbStatement} to ensure unique {@link #getAlias()
   *         aliases}.
   */
  protected AliasMap getAliasMap() {

    return this.aliasMap;
  }

  /**
   * @return the optional {@link EntityBean} from where to get the data and to operate on.
   */
  public E getEntity() {

    return this.entity;
  }

  void setEntity(E entity) {

    setEntity(entity, null);
  }

  void setEntity(E entity, String entityName) {

    if ((entityName == null) && (entity != null)) {
      this.entityName = entity.getType().getStableName();
    }
    this.entity = entity;
  }

  /**
   * @return the name of the {@link #getEntity() entity} in the database (e.g. table name).
   */
  public String getEntityName() {

    return this.entityName;
  }

  /**
   * @param entityName new value of {@link #getEntityName()}.
   */
  protected void setEntityName(String entityName) {

    this.entityName = entityName;
  }

  /**
   * @return alias the alias (variable name) for the {@link EntityBean} to query.
   * @see #as(String)
   */
  public String getAlias() {

    if (this.alias == null) {
      setAlias(this.aliasMap.createAlias(this));
    }
    return this.alias;
  }

  /**
   * @param entityAlias the alias (variable name) for the {@link EntityBean} to query.
   * @return this {@link DbClause} itself for fluent API calls.
   * @see #getAlias()
   */
  public SELF as(String entityAlias) {

    setAlias(entityAlias);
    return self();
  }

  private void setAlias(String entityAlias) {

    if (this.alias != null) {
      EntityBean old = this.aliasMap.remove(this.alias);
      assert (old == this.entity);
    }
    this.alias = entityAlias;
    if (entityAlias != null) {
      this.aliasMap.put(entityAlias, this.entity);
    }
    if (this.entity != null) {
      this.entity.pathSegment(entityAlias);
    }
  }

  @Override
  protected void writeProperties(StructuredWriter writer) {

    writer.writeName(NAME_ENTITY);
    writer.writeValueAsString(this.entityName);
    if (this.alias != null) {
      writer.writeName(NAME_ALIAS);
      writer.writeValueAsString(this.alias);
    }
    super.writeProperties(writer);
  }

  @Override
  protected void readProperty(StructuredReader reader, String name) {

    if (NAME_ENTITY.equals(name)) {
      this.entityName = reader.readValueAsString();
    } else if (NAME_ALIAS.equals(name)) {
      this.alias = reader.readValueAsString();
    } else {
      super.readProperty(reader, name);
    }
  }

}
