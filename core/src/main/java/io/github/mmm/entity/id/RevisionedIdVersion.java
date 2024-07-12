/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Implementation of {@link RevisionedId} with {@link Long} as {@link #getRevisionType() revision type}. With every
 * {@link #updateRevision() update} the revision is incremented.
 *
 * @param <E> type of the identified entity.
 * @param <P> type of the {@link #getPk() primary key}.
 * @since 1.0.0
 */
public final class RevisionedIdVersion<E, P> extends RevisionedId<E, P, Long, RevisionedIdVersion<E, P>> {

  /** {@link #getRevision() Revision} of a newly inserted {@link io.github.mmm.entity.Entity entity}. */
  public static final Long INSERT_REVISION = Long.valueOf(1);

  /** The empty default instance using {@link PkIdLong}. */
  public static final RevisionedIdVersion<Object, Long> DEFAULT = new RevisionedIdVersion<>(PkIdLong.getEmpty(), null);

  private final Long revision;

  /**
   * The constructor.
   *
   * @param id the wrapped {@link PkId} containing {@link #getEntityClass() entity class} and {@link #getPk() primary
   *        key}.
   * @param revision the {@link #getRevision() revision}.
   */
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
