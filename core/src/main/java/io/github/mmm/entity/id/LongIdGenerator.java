/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.function.Function;

/**
 * {@link IdGenerator} for {@link LongId}.
 */
public class LongIdGenerator implements IdGenerator {

  private final Function<Id<?>, Long> sequence;

  /**
   * The constructor.
   *
   * @param sequence the {@link Function} doing the actual job. Typically via a sequence.
   */
  public LongIdGenerator(Function<Id<?>, Long> sequence) {

    super();
    this.sequence = sequence;
  }

  @Override
  public <E> LongId<E, ?> generate(Id<E> template) {

    Class<E> entityType = template.getEntityClass();
    Long pk = this.sequence.apply(template);
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
