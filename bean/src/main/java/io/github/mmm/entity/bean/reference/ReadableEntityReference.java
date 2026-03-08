/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.reference;

import io.github.mmm.entity.bean.EntityBean;

/**
 * Interface to read the reference to an {@link EntityBean entity}. The reference shall always be available as
 * {@link #getEntityName() name} but typically also as {@link #getEntity() entity prototype}.
 *
 * @param <E> type of the referenced {@link EntityBean}.
 */
public interface ReadableEntityReference<E extends EntityBean> {

  /**
   * @return the optional {@link EntityBean} from where to get the data and to operate on.
   */
  E getEntity();

  /**
   * @return the name of the {@link #getEntity() entity} (or the database table).
   */
  String getEntityName();

}
