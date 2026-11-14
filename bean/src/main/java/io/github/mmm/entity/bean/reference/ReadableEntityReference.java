/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.reference;

import io.github.mmm.entity.bean.EntityBean;

/// Interface to read the reference to an [entity][EntityBean]. The reference shall always be available as
/// [name][#getEntityName()] but typically also as [entity prototype][#getEntity()].
///
/// @param <E> type of the referenced [EntityBean].
public interface ReadableEntityReference<E extends EntityBean> {

  /// @return the optional [EntityBean] from where to get the data and to operate on.
  E getEntity();

  /// @return the name of the [entity][#getEntity()] (or the database table).
  String getEntityName();

}
