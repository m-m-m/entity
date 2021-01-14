/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.booleans.BooleanProperty;
import io.github.mmm.property.number.integers.IntegerProperty;
import io.github.mmm.property.string.StringProperty;
import io.github.mmm.property.temporal.localdate.LocalDateProperty;

/**
 *
 */
public interface Person extends EntityBean {

  StringProperty Name();

  LocalDateProperty Birthday();

  BooleanProperty Single();

  IntegerProperty Age();

}
