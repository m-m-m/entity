/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AdvancedBean;
import io.github.mmm.bean.Bean;
import io.github.mmm.bean.BeanClass;
import io.github.mmm.bean.StandardPropertyBuilders;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.impl.EntityPropertyBuildersImpl;
import io.github.mmm.entity.property.builder.EntityPropertyBuilders;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.property.PropertyMetadata;

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

    this((BeanClass) null, null);
  }

  /**
   * The constructor.
   *
   * @param idFactory the {@link IdFactory} to marshal data.
   */
  public AdvancedEntityBean(IdFactory<?, ?> idFactory) {

    this(idFactory, null);
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

    this(null, type);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param idFactory the {@link IdFactory} to marshal data.
   */
  public AdvancedEntityBean(IdFactory<?, ?> idFactory, BeanClass type) {

    this(type, null);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param idProperty the {@link #Id() ID property}.
   */
  public AdvancedEntityBean(BeanClass type, IdProperty<? extends AdvancedEntityBean> idProperty) {

    this(null, type, idProperty);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private AdvancedEntityBean(IdFactory<?, ?> idFactory, BeanClass type,
      IdProperty<? extends AdvancedEntityBean> idProperty) {

    super(type);
    if (idProperty == null) {
      idProperty = new IdProperty(getClass(), PropertyMetadata.of(this), idFactory);
    } else {
      assert idProperty.getName().equals(IdProperty.NAME);
    }
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
