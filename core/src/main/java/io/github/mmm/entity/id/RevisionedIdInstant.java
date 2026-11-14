/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;

/// Implementation of [RevisionedId] with [Instant] as [revision type][#getRevisionType()]. On
/// [update][#updateRevision()] the [current timestamp][Instant#now()] is used as [revision][#getRevision()].
///
/// @param <E> type of the identified entity.
/// @param <P> type of the [primary key][#getPk()].
/// @since 1.0.0
public final class RevisionedIdInstant<E, P> extends RevisionedId<E, P, Instant, RevisionedIdInstant<E, P>> {

  private final Instant revision;

  /// The constructor.
  ///
  /// @param id the wrapped [PkId] containing [entity class][#getEntityClass()] and [primary key][#getPk()].
  /// @param revision the [revision][#getRevision()].
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
