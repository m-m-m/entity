/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * An abstract base implementation of {@link Id} using {@link Long} as type for the {@link #getRevision() revision}.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #get() ID}.
 *
 * @since 1.0.0
 */
public abstract class AbstractVersionId<E, I> extends AbstractId<E, I, Long> {

  /** {@link #getRevision() Revision} of a newly inserted {@link io.github.mmm.entity.Entity entity}. */
  public static final Long INSERT_REVISION = Long.valueOf(1);

  private final Long revision;

  /**
   * The constructor.
   *
   * @param type - see {@link #getEntityClass()}.
   * @param revision - see {@link #getRevision()}.
   */
  public AbstractVersionId(Class<E> type, Long revision) {

    super(type);
    this.revision = revision;
  }

  @Override
  public Long getRevision() {

    return this.revision;
  }

  @Override
  public boolean hasRevisionField() {

    return true;
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

    return PROPERTY_LONG_REVISION;
  }

}
