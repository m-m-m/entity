package io.github.mmm.entity.bean.db.statement.create;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.AbstractEntityClause;
import io.github.mmm.entity.bean.db.statement.AliasMap;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link CreateIndexOn}-{@link DbClause} of an SQL {@link CreateIndexStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class CreateIndexOn<E extends EntityBean> extends AbstractEntityClause<E, E, CreateTable<E>> {

  /** Name of {@link CreateIndexOn} for marshaling. */
  public static final String NAME_ON = "on";

  private final CreateIndexStatement<E> statement;

  /**
   * The constructor.
   *
   * @param createIndex the {@link CreateIndex}-{@link DbClause}.
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public CreateIndexOn(CreateIndex createIndex, E entity) {

    this(createIndex, entity, null);
  }

  /**
   * The constructor.
   *
   * @param createIndex the {@link CreateIndex}-{@link DbClause}.
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getEntityName() entity name}.
   */
  public CreateIndexOn(CreateIndex createIndex, E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new CreateIndexStatement<>(createIndex, this);
  }

  /**
   * @param property the {@link PropertyPath} of the column to create an index on.
   * @return the {@link CreateIndexColumns} for fluent API calls.
   */
  public CreateIndexColumns<E> column(PropertyPath<?> property) {

    CreateIndexColumns<E> column = this.statement.getColumn();
    column.and(property);
    return column;
  }

  /**
   * @param properties the {@link PropertyPath}s of the columns to create an index on.
   * @return the {@link CreateIndexColumns} for fluent API calls.
   */
  public CreateIndexColumns<E> column(PropertyPath<?>... properties) {

    CreateIndexColumns<E> column = this.statement.getColumn();
    column.and(properties);
    return column;
  }

  @Override
  protected String getMarshallingName() {

    return NAME_ON;
  }

}
