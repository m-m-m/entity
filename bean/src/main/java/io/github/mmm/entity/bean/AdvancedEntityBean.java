/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AdvancedBean;
import io.github.mmm.bean.Bean;
import io.github.mmm.bean.BeanClass;
import io.github.mmm.bean.StandardPropertyBuilders;
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
public class AdvancedEntityBean extends AdvancedBean implements EntityBean {

  /** The {@link IdProperty property} with the {@link Id primary key}. */
  public final IdProperty<? extends AdvancedEntityBean> Id;

  /**
   * The constructor.
   */
  public AdvancedEntityBean() {

    this(LongVersionId.FACTORY);
  }

  /**
   * The constructor.
   *
   * @param idFactory the {@link IdFactory} to marshal data.
   */
  public AdvancedEntityBean(IdFactory<?, ?, ?> idFactory) {

    this(null, idFactory);
  }

  /**
   * The constructor.
   *
   * @param idProperty the {@link #Id() ID property}.
   */
  public AdvancedEntityBean(IdProperty<? extends AdvancedEntityBean> idProperty) {

    this(null, idProperty);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   */
  public AdvancedEntityBean(BeanClass type) {

    this(type, LongVersionId.FACTORY);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param idFactory the {@link IdFactory} to marshal data.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public AdvancedEntityBean(BeanClass type, IdFactory<?, ?, ?> idFactory) {

    super(type);
    this.Id = add(new IdProperty(IdProperty.NAME, idFactory, getClass()));
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param idProperty the {@link #Id() ID property}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public AdvancedEntityBean(BeanClass type, IdProperty<? extends AdvancedEntityBean> idProperty) {

    super(type);
    this.Id = add((IdProperty) idProperty);
  }

  @Override
  public IdProperty<? extends AdvancedEntityBean> Id() {

    return this.Id;
  }

  @Override
  protected EntityPropertyBuilders add() {

    return (EntityPropertyBuilders) super.add();
  }

  @Override
  protected StandardPropertyBuilders createPropertyBuilders() {

    return new EntityPropertyBuildersImpl(this);
  }

}
