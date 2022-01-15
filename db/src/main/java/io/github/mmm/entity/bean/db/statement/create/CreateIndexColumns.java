package io.github.mmm.entity.bean.db.statement.create;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.MainClause;
import io.github.mmm.entity.bean.db.statement.PropertyClause;

/**
 * A {@link CreateIndexColumns}-{@link DbClause} of an SQL {@link CreateIndexStatement}.
 *
 * @param <E> type of the {@link io.github.mmm.entity.bean.db.statement.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class CreateIndexColumns<E extends EntityBean> extends PropertyClause<E, CreateIndexColumns<E>>
    implements MainClause<E> {

  /** Name of {@link CreateIndexColumns} for marshaling. */
  public static final String NAME_ON = "on";

  private final CreateIndexStatement<E> statement;

  /**
   * The constructor.
   *
   * @param statement the {@link CreateIndexStatement}.
   */
  public CreateIndexColumns(CreateIndexStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  public CreateIndexStatement<E> get() {

    return this.statement;
  }

  @Override
  protected String getMarshallingName() {

    return NAME_ON;
  }

}
