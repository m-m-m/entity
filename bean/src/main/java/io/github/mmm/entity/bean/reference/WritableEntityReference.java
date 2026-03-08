/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.reference;

import io.github.mmm.entity.bean.EntityBean;

/**
 * Extends {@link ReadableEntityReference} with write operations to the reference on an {@link EntityBean entity}.
 *
 * @param <E> type of the referenced {@link EntityBean}.
 */
public interface WritableEntityReference<E extends EntityBean> extends ReadableEntityReference<E> {

  /**
   * @param entity the {@link #getEntity() entity reference} to set.
   */
  default void setEntity(E entity) {

    setEntity(entity, null);
  }

  /**
   * @param entity the {@link #getEntity() entity reference} to set.
   * @param entityName the {@link #getEntityName() entity name} to set.
   */
  void setEntity(E entity, String entityName);

  /**
   * @param entityName the {@link #getEntityName() entity name} to set.
   */
  void setEntityName(String entityName);

}
