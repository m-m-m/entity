/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import io.github.mmm.entity.bean.EntityBean;

/**
 * Entry class to build a regular {@code SELECT} {@link Query}.
 *
 * @since 1.0.0
 * @see #from(EntityBean)
 */
public final class Select {

  private Select() {

  }

  public static <E extends EntityBean> Query<E> from(E entity) {

    return new Query<>(entity);
  }

}
