/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.generator;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkIdLong;
import io.github.mmm.entity.id.sequence.IdSequence;

/**
 * {@link IdGenerator} based on an {@link IdSequence} for {@link PkIdLong}.
 *
 * @since 1.0.0
 */
public class SequenceIdGenerator implements IdGenerator {

  private final IdSequence sequence;

  /**
   * The constructor.
   *
   * @param sequence the {@link IdSequence}.
   */
  public SequenceIdGenerator(IdSequence sequence) {

    super();
    this.sequence = sequence;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <E> Id<E> generate(Id<E> template) {

    Class<E> entityType = template.getEntityClass();
    Long pk = Long.valueOf(this.sequence.next(template));
    GenericId gid = (GenericId) template;
    return gid.create(entityType, pk, null).updateRevision();
  }

}
