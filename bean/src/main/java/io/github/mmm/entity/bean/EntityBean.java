/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.RevisionedIdVersion;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.id.PkProperty;

/**
 * {@link WritableBean} for an entity that can be loaded from or saved to a database. Can be identified uniquely by its
 * {@link #Id() primary key}.
 *
 * @since 1.0.0
 */
@AbstractInterface
public interface EntityBean extends WritableBean, Entity {

  /**
   * {@link io.github.mmm.base.metainfo.MetaInfo#get(String) Meta-key} to {@link io.github.mmm.base.metainfo.MetaInfos
   * annotate} {@link EntityBean} interface with the name of the database table. If not defined via annotation, the
   * table name defaults to the {@link Class#getSimpleName() simple name} of the interface. The value of this constant
   * will never change. You do not have to use this constant to build your annotated key-value pairs.<br>
   * Example:
   *
   * <pre>
   * {@code @}{@link io.github.mmm.base.metainfo.MetaInfos MetaInfos}("table=CONTACT")
   * public interface ContactEntity extends EntityBean {
   *   // ...
   * }
   * </pre>
   *
   * For further details see {@code io.github.mmm.orm.naming.DbNamingStrategy}.
   */
  public static final String META_KEY_TABLE = "table";

  /**
   * {@link io.github.mmm.base.metainfo.MetaInfo#get(String) Meta-key} to {@link io.github.mmm.base.metainfo.MetaInfos
   * annotate} {@link io.github.mmm.property.Property} methods (such as {@link #Id()}) with the name of the database
   * column. If not defined via annotation, the column name defaults to the
   * {@link io.github.mmm.property.Property#getName() property name}. The value of this constant will never change. You
   * do not have to use this constant to build your annotated key-value pairs.<br>
   * Example:
   *
   * <pre>
   * {@code @}{@link io.github.mmm.base.metainfo.MetaInfos MetaInfos}("column=DATE_OF_BIRTH")
   * LocalDateProperty Birthday();
   * </pre>
   *
   * For further details see {@code io.github.mmm.orm.naming.DbNamingStrategy}.
   */
  public static final String META_KEY_COLUMN = "column";

  /**
   * {@link io.github.mmm.base.metainfo.MetaInfo#get(String) Meta-key} to {@link io.github.mmm.base.metainfo.MetaInfos
   * annotate} {@link io.github.mmm.property.Property} methods (e.g.
   * {@link io.github.mmm.entity.bean.property.EntityWithTitle#Title() title} or
   * {@link io.github.mmm.entity.bean.property.EntityWithName#Name() name}) with a score. Such score is a double value
   * in the range [0, 1] to rank the property for full-text-search. To activate full-text search you typically annotate
   * a score on the {@link EntityBean} itself that defines the default score for all
   * {@link io.github.mmm.property.string.StringProperty String properties}. A reasonable default score is e.g.
   * {@code 0.5} or {@code 0.8} allowing to override specific properties to boost them using a higher score or to reduce
   * their impact with a lower score. A score of zero ({@code 0}) excludes the property from indexing.
   */
  public static final String META_KEY_SCORE = "score";

  /**
   * @return the {@link IdProperty property} with the {@link Id} (primary key) of this entity.
   */
  default PkProperty Id() {

    return new PkProperty(RevisionedIdVersion.DEFAULT.withEntityTypeGeneric(getType().getJavaClass()));
  }

  @Override
  default Id<?/* extends EntityBean */> getId() {

    return Id().get();
  }

  @Override
  default void setId(Id<?> id) {

    Id().set(id);
  }

  @Override
  default Class<?> getJavaClass() {

    return WritableBean.super.getJavaClass();
  }
}
