/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AdvancedBean;
import io.github.mmm.bean.Bean;
import io.github.mmm.bean.BeanClass;
import io.github.mmm.bean.StandardPropertyBuilders;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.impl.EntityPropertyBuildersImpl;
import io.github.mmm.entity.id.Id;
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

    this(null, null, null);
  }

  /**
   * The constructor.
   *
   * @param writable the {@link WritableBean} to wrap as {@link #isReadOnly() read-only} bean or {@code null} to create
   *        a mutable bean.
   */
  public AdvancedEntityBean(WritableBean writable) {

    this(writable, null, null);
  }

  /**
   * The constructor.
   *
   * @param pkProperty the {@link #Id() ID property}.
   */
  public AdvancedEntityBean(PkProperty pkProperty) {

    this(null, null, pkProperty);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   */
  public AdvancedEntityBean(BeanClass type) {

    this(null, type, null);
  }

  /**
   * The constructor.
   *
   * @param writable the {@link WritableBean} to wrap as {@link #isReadOnly() read-only} bean or {@code null} to create
   *        a mutable bean.
   * @param type the {@link #getType() type}.
   * @param pkProperty the {@link #Id() ID property}.
   */
  private AdvancedEntityBean(WritableBean writable, BeanClass type, PkProperty pkProperty) {

    super(writable, type);
    if (pkProperty == null) {
      if (writable == null) {
        // default
        Id<?> id = io.github.mmm.entity.id.Id.of(getClass(), null);
        pkProperty = new PkProperty(id, PropertyMetadata.of(this));
      } else {
        pkProperty = ((AdvancedEntityBean) writable).Id;
      }
    } else {
      assert pkProperty.getName().equals(PkProperty.NAME);
      assert (writable == null);
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
