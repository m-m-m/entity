/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.Bean;
import io.github.mmm.bean.PropertyBuilders;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.entity.impl.EntityPropertyBuildersImpl;
import io.github.mmm.entity.property.builder.EntityPropertyBuilders;
import io.github.mmm.entity.property.id.IdProperty;

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

    this(LongVersionId.FACTORY);
  }

  /**
   * The constructor.
   *
   * @param idFactory the {@link IdFactory} to marshal data.
   */
  public SimpleEntityBean(IdFactory<?, ?, ?> idFactory) {

    super();
    this.Id = add(new IdProperty<>(IdProperty.NAME, idFactory, getClass()));
  }

  /**
   * The constructor.
   *
   * @param idProperty the {@link #Id() ID property}.
   */
  public SimpleEntityBean(IdProperty<? extends SimpleEntityBean> idProperty) {

    super();
    this.Id = add(idProperty);
  }

  @Override
  public IdProperty<? extends SimpleEntityBean> Id() {

    return this.Id;
  }

  @Override
  protected EntityPropertyBuilders add() {

    return (EntityPropertyBuilders) super.add();
  }

  @Override
  protected PropertyBuilders createPropertyBuilders() {

    return new EntityPropertyBuildersImpl(this);
  }

}
