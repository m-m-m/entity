/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/// Implementation of [RevisionedId] with [Long] as [revision type][#getRevisionType()]. With every
/// [update][#updateRevision()] the revision is incremented.
///
/// @param <E> type of the identified entity.
/// @param <P> type of the [primary key][#getPk()].
/// @since 1.0.0
public final class RevisionedIdVersion<E, P> extends RevisionedId<E, P, Long, RevisionedIdVersion<E, P>> {

  /// [Revision][#getRevision()] of a newly inserted [entity][io.github.mmm.entity.Entity].
  public static final Long INSERT_REVISION = Long.valueOf(1);

  private final Long revision;

  /// The constructor.
  ///
  /// @param id the wrapped [PkId] containing [entity class][#getEntityClass()] and [primary key][#getPk()].
  /// @param revision the [revision][#getRevision()].
  public RevisionedIdVersion(PkId<E, P, ?> id, Long revision) {

    super(id);
    this.revision = revision;
  }

  @Override
  protected RevisionedIdVersion<E, P> newId(PkId<E, P, ?> newId, Long newRevision) {

    return new RevisionedIdVersion<>(newId, newRevision);
  }

  @Override
  public Long getRevision() {

    return this.revision;
  }

  @Override
  public Class<Long> getRevisionType() {

    return Long.class;
  }

  @Override
  public Long parseRevision(String revisionString) {

    return Long.valueOf(revisionString);
  }

  @Override
  public Long updateRevision(Long currentRevision) {

    long r = 0;
    if (currentRevision != null) {
      r = currentRevision.longValue();
    }
    return Long.valueOf(r + 1);
  }

  @Override
  public String getMarshalPropertyRevision() {

    return PROPERTY_REVISION_VERSION;
  }

}
