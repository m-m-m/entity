/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.attribute;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.string.StringProperty;

/**
 * {@link EntityBean} that has a {@link #Code()}.
 *
 * @since 1.0.0
 */
public abstract interface EntityWithCode extends EntityBean {

  /** @return the code that acts as an externally given identifier. */
  StringProperty Code();

}
