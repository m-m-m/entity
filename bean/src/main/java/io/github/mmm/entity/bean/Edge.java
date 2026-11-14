/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.property.link.LinkProperty;

/// An [Edge] connects two [entities][EntityBean] as a directed many-to-many relation. It links from an
/// [incoming][#In()] to an [outgoing][#Out()] [entity][EntityBean].
///
/// @param <I> type of [#In()].
/// @param <O> type of [#Out()].
///
/// @since 1.0.0
@AbstractInterface
public interface Edge<I extends EntityBean, O extends EntityBean> extends EntityBean {

  /// @return the [Id] of the incoming [entity][EntityBean] (source).
  LinkProperty<I> In();

  /// @return the [Id] of the outgoing [entity][EntityBean] (target).
  LinkProperty<O> Out();

}
