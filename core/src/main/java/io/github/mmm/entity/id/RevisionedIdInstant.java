/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/**
 * Implementation of {@link RevisionedId} with {@link Instant} as {@link #getRevisionType() revision type}. On
 * {@link #updateRevision() update} the {@link Instant#now() current timestamp} is used as {@link #getRevision()
 * revision}.
 *
 * @param <E> type of the identified entity.
 * @param <P> type of the {@link #getPk() primary key}.
 * @since 1.0.0
 */
public final class RevisionedIdInstant<E, P> extends RevisionedId<E, P, Instant, RevisionedIdInstant<E, P>> {

  private final Instant revision;

  /**
   * The constructor.
   *
   * @param id the wrapped {@link PkId} containing {@link #getEntityClass() entity class} and {@link #getPk() primary
   *        key}.
   * @param revision the {@link #getRevision() revision}.
   */
  public RevisionedIdInstant(PkId<E, P, ?> id, Instant revision) {

    super(id);
    this.revision = revision;
  }

  @Override
  public Instant getRevision() {

    return this.revision;
  }

  @Override
  public Class<Instant> getRevisionType() {

    return Instant.class;
  }

  @Override
  public String getMarshalPropertyRevision() {

    return PROPERTY_REVISION_INSTANT;
  }

  @Override
  protected RevisionedIdInstant<E, P> newId(PkId<E, P, ?> newId, Instant newRevision) {

    return new RevisionedIdInstant<>(newId, newRevision);
  }

  @Override
  public Instant parseRevision(String revisionString) {

    return Instant.parse(revisionString);
  }

  @Override
  public Instant updateRevision(Instant currentRevision) {

    return Instant.now();
  }

}
