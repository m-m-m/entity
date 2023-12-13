/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AdvancedBean;
import io.github.mmm.bean.Bean;
import io.github.mmm.bean.BeanClass;
import io.github.mmm.bean.StandardPropertyBuilders;
import io.github.mmm.entity.bean.impl.EntityPropertyBuildersImpl;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.entity.property.builder.EntityPropertyBuilders;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.property.PropertyMetadata;

/**
 * Implementation of {@link EntityBean} as simple {@link Bean}.
 *
 * @since 1.0.0
 */
public class AdvancedEntityBean extends AdvancedBean implements EntityBean {

  /** The {@link IdProperty property} with the {@link Id primary key}. */
  public final PkProperty Id;

  /**
   * The constructor.
   */
  public AdvancedEntityBean() {

    this(null, null);
  }

  /**
   * The constructor.
   *
   * @param pkProperty the {@link #Id() ID property}.
   */
  public AdvancedEntityBean(PkProperty pkProperty) {

    this(null, pkProperty);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   */
  public AdvancedEntityBean(BeanClass type) {

    this(type, null);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param pkProperty the {@link #Id() ID property}.
   */
  private AdvancedEntityBean(BeanClass type, PkProperty pkProperty) {

    super(type);
    if (pkProperty == null) {
      // default
      Id<?> id = LongVersionId.getEmpty().withEntityType(getClass());
      pkProperty = new PkProperty(id, PropertyMetadata.of(this));
    } else {
      assert pkProperty.getName().equals(PkProperty.NAME);
    }
    this.Id = add(pkProperty);
  }

  @Override
  public PkProperty Id() {

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
