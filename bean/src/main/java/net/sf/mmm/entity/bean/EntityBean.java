/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.bean;

import net.sf.mmm.bean.WritableBean;
import net.sf.mmm.entity.Entity;
import net.sf.mmm.entity.id.Id;
import net.sf.mmm.entity.property.id.IdProperty;

/**
 * {@link WritableBean} for an entity that can be loaded from or saved to a database. Can be identified uniquely by its
 * {@link #Id() primary key}.
 *
 * @since 1.0.0
 */
public interface EntityBean extends WritableBean, Entity {

  /**
   * @return the {@link IdProperty property} with the {@link Id} (primary key) of this entity.
   */
  IdProperty<? extends EntityBean> Id();

  @Override
  default Id<? extends EntityBean> getId() {

    return Id().get();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  default void setId(Id<? extends Entity> id) {

    Id().set((Id) id);
  }

  /**
   * @param <B> type of {@link EntityBean}.
   * @param bean the {@link EntityBean} instance. May be {@code null}.
   * @return the {@link Id} of the given {@link EntityBean}. May be {@code null} if given {@link EntityBean} was
   *         {@code null} or its {@link #Id() Id property} has {@code null} as {@link Id} {@link IdProperty#getValue()
   *         value}.
   */
  @SuppressWarnings("unchecked")
  static <B extends EntityBean> Id<B> getId(B bean) {

    if (bean == null) {
      return null;
    }
    return (Id<B>) bean.Id().get();
  }

}
