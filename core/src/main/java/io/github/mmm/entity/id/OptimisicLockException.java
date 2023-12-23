/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import io.github.mmm.base.exception.ApplicationException;
import io.github.mmm.entity.Entity;

/**
 * {@link RuntimeException} thrown if optimistic locking failed. When a persistent {@link io.github.mmm.entity.Entity}
 * is {@link io.github.mmm.entity.repository.EntityRepository#save(io.github.mmm.entity.Entity) updated}, optimistic
 * locking will check that the {@link Id#getRevision() revision} of its {@link Id} matches the one from the
 * {@link Entity} in the persistent store (e.g. database). If they do not match the update will fail with this
 * exception.<br>
 * This will prevent that two users that want to save changes to the same {@link Entity} concurrently may override each
 * others changes.
 */
public class OptimisicLockException extends ApplicationException {

  /**
   * The constructor.
   *
   * @param id the {@link Id} of the entity that was to be saved but had an outdated {@link Id#getRevision() revision}.
   * @param entityName the name of the entity to be saved.
   */
  public OptimisicLockException(Id<?> id, String entityName) {

    super("Cound not save entity '" + entityName + "' with primary key '" + id.getAsString()
        + "' because the revision '" + id.getRevisionAsString() + "' is outdated!");
  }

  @Override
  public String getCode() {

    return "DB-LOCK";
  }

}
