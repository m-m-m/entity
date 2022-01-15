/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.example;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.property.number.integers.IntegerProperty;
import io.github.mmm.property.string.StringProperty;
import io.github.mmm.property.temporal.DurationInSecondsProperty;

/**
 * {@link WritableBean} for the result of a projection example.
 */
public interface Result extends WritableBean {

  /**
   * @return the genre of the song (e.g. "pop" or "rock").
   */
  StringProperty Genre();

  /**
   * @return the average duration in seconds.
   */
  IntegerProperty Count();

  /**
   * @return the average duration in seconds.
   */
  DurationInSecondsProperty Duration();

  /**
   * @return a new instance of {@link Result}.
   */
  static Result of() {

    return BeanFactory.get().create(Result.class);
  }

}
