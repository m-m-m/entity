/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * An abstract base implementation of {@link Id} using {@link Instant} as type for the {@link #getVersion() version}.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #get() ID}.
 *
 * @since 1.0.0
 */
public abstract class AbstractInstantId<E, I> extends AbstractId<E, I, Instant> {

  private final Instant version;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param version - see {@link #getVersion()}.
   */
  public AbstractInstantId(Class<E> type, Instant version) {

    super(type);
    this.version = version;
  }

  @Override
  public Instant getVersion() {

    return this.version;
  }

  @Override
  protected String getMarshalPropertyVersion() {

    return PROPERTY_INSTANT_VERSION;
  }

}
