/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * An abstract base implementation of {@link Id} using {@link Long} as type for the {@link #getVersion() version}.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #getId() ID}.
 *
 * @since 1.0.0
 */
public abstract class AbstractVersionId<E, I> extends AbstractId<E, I, Long> {

  private final Long version;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param version - see {@link #getVersion()}.
   */
  public AbstractVersionId(Class<E> type, Long version) {

    super(type);
    this.version = version;
  }

  @Override
  public Long getVersion() {

    return this.version;
  }

}
