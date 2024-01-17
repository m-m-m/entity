/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.sequence;

import io.github.mmm.entity.id.Id;

/**
 * {@link IdSequence} implementation using an in-memory counter that is NOT persistent. By design this implementation
 * can not guarantee correctness. Only use this for pragmatic scenarios e.g. for tests or if you are not using a
 * database at all (e.g. also all data is hold in memory for simple apps that persist all data to JSON or the like).
 */
public final class IdSequenceMemory implements IdSequence {

  private volatile long sequence;

  /**
   * The constructor.
   */
  public IdSequenceMemory() {

    this(10_000_000L); // enough room for your own master data
  }

  /**
   * The constructor.
   *
   * @param sequenceStart the start value of the sequence.
   */
  public IdSequenceMemory(long sequenceStart) {

    super();
    this.sequence = sequenceStart;
  }

  /**
   * @return the current sequence value.
   */
  public long getSequence() {

    return this.sequence;
  }

  /**
   * @param sequence new value of {@link #getSequence()}.
   */
  public void setSequence(long sequence) {

    this.sequence = sequence;
  }

  @Override
  public long next(Id<?> template) {

    return this.sequence++;
  }

}