/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * An abstract base implementation of {@link Id} using {@link Instant} as type for the {@link #getRevision() revision}.
 *
 * @param <E> type of the identified entity.
 * @param <P> type of the {@link #getPk() primary key}.
 *
 * @since 1.0.0
 */
public abstract class AbstractInstantId<E, P> extends AbstractId<E, P, Instant> {

  /** @see #getRevision() */
  protected final Instant revision;

  /**
   * The constructor.
   *
   * @param type - see {@link #getEntityClass()}.
   * @param revision - see {@link #getRevision()}.
   */
  public AbstractInstantId(Class<E> type, Instant revision) {

    super(type);
    this.revision = revision;
  }

  @Override
  public Instant getRevision() {

    return this.revision;
  }

  @Override
  public boolean hasRevisionField() {

    return true;
  }

  @Override
  public Class<Instant> getRevisionType() {

    return Instant.class;
  }

  @Override
  public Instant parseRevision(String revisionString) {

    return Instant.parse(revisionString);
  }

  @Override
  public Instant updateRevision(Instant currentRevision) {

    return Instant.now();
  }

  @Override
  public String getMarshalPropertyRevision() {

    return PROPERTY_INSTANT_REVISION;
  }

}
