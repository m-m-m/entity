/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.reference;

import io.github.mmm.entity.bean.EntityBean;

/// Extends [ReadableEntityReference] with write operations to the reference on an [entity][EntityBean].
///
/// @param <E> type of the referenced [EntityBean].
public interface WritableEntityReference<E extends EntityBean> extends ReadableEntityReference<E> {

  /// @param entity the [entity reference][#getEntity()] to set.
  default void setEntity(E entity) {

    setEntity(entity, null);
  }

  /// @param entity the [entity reference][#getEntity()] to set.
  /// @param entityName the [entity name][#getEntityName()] to set.
  void setEntity(E entity, String entityName);

  /// @param entityName the [entity name][#getEntityName()] to set.
  void setEntityName(String entityName);

}
