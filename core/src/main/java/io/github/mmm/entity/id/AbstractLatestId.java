/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * An abstract base implementation of {@link Id} without {@link #getVersion() version}. It will always point to the
 * latest {@link #getVersion() version}.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #get() ID}.
 *
 * @since 1.0.0
 */
public abstract class AbstractLatestId<E, I> extends AbstractId<E, I, LatestVersion> {

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   */
  public AbstractLatestId(Class<E> type) {

    super(type);
  }

  @Override
  public LatestVersion getVersion() {

    return null;
  }

  @Override
  protected String getMarshalPropertyVersion() {

    return null;
  }

}
