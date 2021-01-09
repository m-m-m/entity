/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.Bean;
import io.github.mmm.bean.StandardPropertyBuilders;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.impl.EntityPropertyBuildersImpl;
import io.github.mmm.entity.property.builder.EntityPropertyBuilders;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.property.PropertyMetadata;

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

    this(null);
  }

  /**
   * The constructor.
   *
   * @param idProperty the {@link #Id() ID property}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SimpleEntityBean(IdProperty<? extends SimpleEntityBean> idProperty) {

    super();
    if (idProperty == null) {
      idProperty = new IdProperty<>(getClass(), PropertyMetadata.of(this));
    }
    this.Id = add((IdProperty) idProperty);
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
  protected StandardPropertyBuilders createPropertyBuilders() {

    return new EntityPropertyBuildersImpl(this);
  }

}
