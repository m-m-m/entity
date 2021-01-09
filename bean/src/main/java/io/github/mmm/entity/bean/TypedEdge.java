/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.property.string.StringProperty;

/**
 * An {@link Edge} that has a {@link #Type() type} that classifies the relation. A {@link TypedEdge} is a very powerful
 * concept to make a data-model flexible and extensible.
 *
 * @param <I> type of {@link #In()}.
 * @param <O> type of {@link #Out()}.
 *
 * @since 1.0.0
 */
@AbstractInterface
public interface TypedEdge<I, O> extends Edge<I, O> {

  /**
   * @return the type of this edge. E.g. for an edge that connects a <em>person</em> with <em>contact data</em> such as
   *         phone number and email, the type could be "home" or "work" to distinguish between private and business
   *         contact data. For generic edges it is a good practice to use types starting with "Has". So e.g. a
   *         <em>person</em> can be connected with some <em>food</em> using the type "HasEaten".
   */
  StringProperty Type();

}
