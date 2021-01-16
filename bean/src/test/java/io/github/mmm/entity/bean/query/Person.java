/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.query;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.booleans.BooleanProperty;
import io.github.mmm.property.number.integers.IntegerProperty;
import io.github.mmm.property.string.StringProperty;
import io.github.mmm.property.temporal.localdate.LocalDateProperty;

/**
 * {@link EntityBean} for a human person used for testing.
 */
public interface Person extends EntityBean {

  /**
   * @return full name of the person including first and last name.
   */
  StringProperty Name();

  /**
   * @return date of birth.
   */
  LocalDateProperty Birthday();

  /**
   * @return {@code true} if single, {@code false} if married. Not a good example - just for testing.
   */
  BooleanProperty Single();

  /**
   * @return the current age of the person. Should be computed from {@link #Birthday()} but just for testing.
   */
  IntegerProperty Age();

}
