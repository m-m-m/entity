/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

/**
 * {@link IdGenerator} for {@link UuidId}.
 */
public class UuidIdGenerator implements IdGenerator {

  @Override
  public <E> UuidId<E, ?> generate(Id<E> template) {

    Class<E> entityType = template.getEntityClass();
    UUID pk = UUID.randomUUID();
    Class<? extends Comparable<?>> revisionType = template.getRevisionType();
    if (revisionType == Long.class) {
      return new UuidVersionId<>(entityType, pk, AbstractVersionId.INSERT_REVISION);
    } else if (revisionType == Instant.class) {
      return new UuidInstantId<>(entityType, pk, Instant.now());
    } else {
      throw new IllegalArgumentException("Unsupported revision type " + revisionType + "(" + template.getClass() + ")");
    }
  }

}
