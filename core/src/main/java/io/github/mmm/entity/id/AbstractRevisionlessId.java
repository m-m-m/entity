/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * An abstract base implementation of {@link Id} that has no {@link #getRevision() revision} and will neither support
 * optimistic locking nor reading audit trails.
 *
 * @param <E> type of the identified entity.
 * @param <P> type of the {@link #getPk() primary key}.
 *
 * @since 1.0.0
 */
public abstract class AbstractRevisionlessId<E, P> extends AbstractId<E, P, NoRevision> {

  /**
   * The constructor.
   *
   * @param type - see {@link #getEntityClass()}.
   */
  public AbstractRevisionlessId(Class<E> type) {

    super(type);
  }

  @Override
  public NoRevision getRevision() {

    return null;
  }

  @Override
  public boolean hasRevisionField() {

    return false;
  }

  @Override
  public Class<NoRevision> getRevisionType() {

    return NoRevision.class;
  }

  @Override
  public NoRevision parseRevision(String revisionString) {

    return null;
  }

  @Override
  public NoRevision updateRevision(NoRevision currentRevision) {

    return null;
  }

  @Override
  public String getMarshalPropertyRevision() {

    return null;
  }

}
