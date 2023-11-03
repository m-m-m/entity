/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.dialect;

import io.github.mmm.entity.bean.db.naming.DbNamingStrategy;
import io.github.mmm.entity.bean.db.orm.Orm;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.entity.db.impl.OrmImpl;

/**
 * Abstract base implementation of {@link DbDialect}.
 *
 * @param <SELF> this type itself for fluent API calls.
 * @since 1.0.0
 */
public abstract class AbstractDbDialect<SELF extends AbstractDbDialect<SELF>> implements DbDialect {

  private final OrmImpl orm;

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected AbstractDbDialect(Orm orm) {

    super();
    this.orm = (OrmImpl) orm;
  }

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   */
  protected AbstractDbDialect(TypeMapping typeMapping) {

    super();
    this.orm = new OrmImpl(typeMapping, getDefaultNamingStrategy());
  }

  /**
   * @return the default {@link DbNamingStrategy}.
   */
  protected DbNamingStrategy getDefaultNamingStrategy() {

    return DbNamingStrategy.ofRdbms();
  }

  /**
   * @return the {@link Orm}.
   */
  public Orm getOrm() {

    return this.orm;
  }

  /**
   * @param namingStrategy the new {@link DbNamingStrategy} to use.
   * @return this dialect for the given {@link DbNamingStrategy}.
   */
  @SuppressWarnings("unchecked")
  public SELF withNamingStrategy(DbNamingStrategy namingStrategy) {

    OrmImpl newOrm = this.orm.withNamingStrategy(namingStrategy);
    if (newOrm == this.orm) {
      return (SELF) this;
    }
    return withOrm(newOrm);
  }

  /**
   * @param newOrm the new {@link Orm} to use.
   * @return a new instance of this dialect with the given {@link Orm}.
   */
  protected abstract SELF withOrm(Orm newOrm);

  @Override
  public String toString() {

    return getName() + "[" + getClass().getSimpleName() + "]";
  }

}
