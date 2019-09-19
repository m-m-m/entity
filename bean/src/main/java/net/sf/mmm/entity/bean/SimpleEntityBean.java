/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.bean;

import net.sf.mmm.bean.AbstractBean;
import net.sf.mmm.bean.Bean;
import net.sf.mmm.entity.id.Id;
import net.sf.mmm.entity.id.LongVersionId;
import net.sf.mmm.entity.property.id.IdProperty;

/**
 * Implementation of {@link EntityBean} as simple {@link Bean}.
 *
 * @since 1.0.0
 */
public class SimpleEntityBean extends Bean implements EntityBean {

  /** The {@link IdProperty property} with the {@link Id primary key}. */
  public final IdProperty<? extends SimpleEntityBean> Id;

  /**
   * The constructor.
   */
  public SimpleEntityBean() {

    this(null, null, false);
  }

  /**
   * The constructor.
   *
   * @param writable the writable {@link Bean} to create a {@link #isReadOnly() read-only} view on or {@code null} to
   *        create a regular mutable {@link Bean}.
   * @param dynamic the {@link #isDynamic() dynamic flag}.
   */
  public SimpleEntityBean(AbstractBean writable, boolean dynamic) {

    this(writable, LongVersionId.class, dynamic);
  }

  /**
   * The constructor.
   *
   * @param writable the writable {@link Bean} to create a {@link #isReadOnly() read-only} view on or {@code null} to
   *        create a regular mutable {@link Bean}.
   * @param idClass the {@link Class} of the {@link Id} implementation.
   * @param dynamic the {@link #isDynamic() dynamic flag}.
   */
  @SuppressWarnings("rawtypes")
  public SimpleEntityBean(AbstractBean writable, Class<? extends Id> idClass, boolean dynamic) {

    super(writable, dynamic);
    this.Id = add(new IdProperty<>(idClass, getClass()));
  }

  @Override
  public IdProperty<? extends SimpleEntityBean> Id() {

    return this.Id;
  }

}
