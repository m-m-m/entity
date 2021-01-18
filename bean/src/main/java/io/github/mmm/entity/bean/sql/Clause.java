/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.sql;

import io.github.mmm.marshall.MarshallingObject;

/**
 * A {@link Clause} is a top-level fragment (part) of an SQL {@link Statement}.
 *
 * @since 1.0.0
 */
public abstract interface Clause extends MarshallingObject {

  /**
   * @return {@code true} if this {@link Clause} should be omitted as it is optional and entirely empty, {@code false}
   *         otherwise.
   */
  default boolean isOmit() {

    return false;
  }

}
