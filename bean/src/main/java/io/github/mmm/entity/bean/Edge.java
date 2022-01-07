/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.property.link.LinkProperty;

/**
 * An {@link Edge} connects two {@link EntityBean entities} as a directed many-to-many relation. It links from an
 * {@link #In() incoming} to an {@link #Out() outgoing} {@link EntityBean entity}.
 *
 * @param <I> type of {@link #In()}.
 * @param <O> type of {@link #Out()}.
 *
 * @since 1.0.0
 */
@AbstractInterface
public interface Edge<I extends EntityBean, O extends EntityBean> extends EntityBean {

  /**
   * @return {@link Id} of the incoming {@link EntityBean entity} (source).
   */
  LinkProperty<I> In();

  /**
   * @return {@link Id} of the outgoing {@link EntityBean entity} (target).
   */
  LinkProperty<O> Out();

}
