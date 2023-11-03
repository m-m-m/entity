package io.github.mmm.entity.bean.db.statement.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.bean.mapping.ClassNameMapper;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.constraint.DbConstraint;
import io.github.mmm.entity.bean.db.constraint.ForeignKeyConstraint;
import io.github.mmm.entity.bean.db.constraint.NotNullConstraint;
import io.github.mmm.entity.bean.db.constraint.PrimaryKeyConstraint;
import io.github.mmm.entity.bean.db.constraint.UniqueConstraint;
import io.github.mmm.entity.bean.db.statement.DbClause;
import io.github.mmm.entity.bean.db.statement.MainDbClause;
import io.github.mmm.entity.bean.db.statement.PropertyClause;
import io.github.mmm.entity.property.id.FkProperty;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.property.Property;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * A {@link CreateTableColumns}-{@link DbClause} of an SQL {@link CreateTableStatement}.
 *
 * @param <E> type of the {@link io.github.mmm.entity.bean.db.statement.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class CreateTableColumns<E extends EntityBean> extends PropertyClause<E, CreateTableColumns<E>>
    implements MainDbClause<E> {

  /** Name of {@link CreateTableColumns} for marshaling. */
  public static final String NAME_COLUMNS = "cols";

  private final CreateTableStatement<E> statement;

  private final List<DbConstraint> constraints;

  /**
   * The constructor.
   *
   * @param statement the {@link CreateTableStatement}.
   */
  public CreateTableColumns(CreateTableStatement<E> statement) {

    super();
    this.statement = statement;
    this.constraints = new ArrayList<>();
  }

  @Override
  public CreateTableStatement<E> get() {

    return this.statement;
  }

  /**
   * @return the {@link List} of {@link DbConstraint}s.
   */
  public List<DbConstraint> getConstraints() {

    return this.constraints;
  }

  @Override
  public CreateTableColumns<E> and(PropertyPath<?> property) {

    return and(property, true);
  }

  /**
   * @param property the {@link PropertyPath} to add.
   * @param autoConstraints - {@code true} to automatically add constraints, {@code false} otherwise (to only add the
   *        property as column).
   * @return this {@link DbClause} itself for fluent API calls.
   */
  public CreateTableColumns<E> and(PropertyPath<?> property, boolean autoConstraints) {

    Objects.requireNonNull(property, "properety");
    if ((autoConstraints) && (property instanceof ReadableProperty)) {
      ReadableProperty<?> p = (ReadableProperty<?>) property;
      if (p.getMetadata().getValidator().isMandatory()) {
        return andNotNull(property);
      } else if (p instanceof PkProperty) {
        this.constraints.add(
            new PrimaryKeyConstraint(PrimaryKeyConstraint.PREFIX + this.statement.getCreateTable().getEntityName(), p));
      } else if (p instanceof FkProperty) {
        return andForeignKey((FkProperty<?>) p);
      } else if (p instanceof LinkProperty) {
        return andForeignKey((LinkProperty<?>) p);
      }
    }
    this.properties.add(property);
    return self();
  }

  /**
   * @param constraint the {@link DbConstraint} to add.
   * @return this {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> constraint(DbConstraint constraint) {

    this.constraints.add(constraint);
    return this;
  }

  /**
   * @param property the {@link PropertyPath} to add as column with {@link NotNullConstraint}.
   * @return this {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> andNotNull(PropertyPath<?> property) {

    and(property, false);
    this.constraints.add(new NotNullConstraint(constraintName(NotNullConstraint.PREFIX, property), property));
    return this;
  }

  /**
   * @param property the {@link PropertyPath} to add as column with {@link UniqueConstraint}.
   * @return this {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> andUnique(PropertyPath<?> property) {

    and(property, false);
    this.constraints.add(new UniqueConstraint(constraintName(UniqueConstraint.PREFIX, property), property));
    return this;
  }

  /**
   * @param property the {@link IdProperty} to add as column with {@link ForeignKeyConstraint}.
   * @return this {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> andForeignKey(FkProperty<?> property) {

    and(property, false);
    return andForeignKey(property, property.get().getEntityClass());
  }

  /**
   * @param property the {@link LinkProperty} to add as column with {@link ForeignKeyConstraint}.
   * @return this {@link CreateTableColumns} for fluent API calls.
   */
  public CreateTableColumns<E> andForeignKey(LinkProperty<?> property) {

    and(property, false);
    return andForeignKey(property, property.getEntityClass());
  }

  private CreateTableColumns<E> andForeignKey(Property<?> property, Class<?> referencedEntity) {

    String name = constraintName(ForeignKeyConstraint.PREFIX, property);
    String referenceTable;
    if (referencedEntity != null) {
      referenceTable = ClassNameMapper.get().getName(referencedEntity);
    } else {
      // TODO how to handle? Skip constraint? Exception?
      referenceTable = "UNDEFINED";
    }
    this.constraints.add(new ForeignKeyConstraint(name, property, referenceTable));
    return this;
  }

  private String constraintName(String prefix, PropertyPath<?> property) {

    return prefix + this.statement.getCreateTable().getEntityName() + "_" + property.path();
  }

  @Override
  protected String getMarshallingName() {

    return NAME_COLUMNS;
  }

}
