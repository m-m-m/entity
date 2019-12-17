/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.impl;

import io.github.mmm.bean.AbstractBean;
import io.github.mmm.bean.PropertyBuilders;
import io.github.mmm.entity.property.builder.EntityPropertyBuilders;

/**
 * Implementation of {@link EntityPropertyBuilders}.
 * 
 * @since 1.0.0
 */
public class EntityPropertyBuildersImpl extends PropertyBuilders implements EntityPropertyBuilders {

  /**
   * The constructor.
   *
   * @param bean the
   */
  public EntityPropertyBuildersImpl(AbstractBean bean) {

    super(bean);
  }

}
