/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.property.id.IdProperty;

/**
 * {@link WritableBean} for an entity that can be loaded from or saved to a database. Can be identified uniquely by its
 * {@link #Id() primary key}.
 *
 * @since 1.0.0
 */
@AbstractInterface
public interface EntityBean extends WritableBean, Entity {

  /**
   * @return the {@link IdProperty property} with the {@link Id} (primary key) of this entity.
   */
  default IdProperty Id() {

    return new IdProperty(getType().getJavaClass());
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  default Id<? extends EntityBean> getId() {

    return (Id) Id().get();
  }

  @Override
  default void setId(Id<? extends Entity> id) {

    Id().set(id);
  }

}
