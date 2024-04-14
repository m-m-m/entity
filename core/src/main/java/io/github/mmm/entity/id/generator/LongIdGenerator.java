/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.generator;

import java.time.Instant;

import io.github.mmm.entity.id.AbstractVersionId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongId;
import io.github.mmm.entity.id.LongInstantId;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.entity.id.sequence.IdSequence;

/**
 * {@link IdGenerator} for {@link LongId}.
 *
 * @since 1.0.0
 */
public class LongIdGenerator implements IdGenerator {

  private final IdSequence sequence;

  /**
   * The constructor.
   *
   * @param sequence the {@link IdSequence}.
   */
  public LongIdGenerator(IdSequence sequence) {

    super();
    this.sequence = sequence;
  }

  @Override
  public <E> LongId<E, ?> generate(Id<E> template) {

    Class<E> entityType = template.getEntityClass();
    Long pk = Long.valueOf(this.sequence.next(template));
    Class<? extends Comparable<?>> revisionType = template.getRevisionType();
    if (revisionType == Long.class) {
      return new LongVersionId<>(entityType, pk, AbstractVersionId.INSERT_REVISION);
    } else if (revisionType == Instant.class) {
      return new LongInstantId<>(entityType, pk, Instant.now());
    } else {
      throw new IllegalArgumentException("Unsupported revision type " + revisionType + "(" + template.getClass() + ")");
    }
  }

}
