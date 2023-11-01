/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * An abstract base implementation of {@link Id} using {@link Instant} as type for the {@link #getRevision() revision}.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #get() ID}.
 *
 * @since 1.0.0
 */
public abstract class AbstractInstantId<E, I> extends AbstractId<E, I, Instant> {

  private final Instant revision;

  /**
   * The constructor.
   *
   * @param type - see {@link #getEntityType()}.
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
  public boolean hasRevision() {

    return (this.revision != null);
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
