package io.github.mmm.entity.id;

import java.util.function.Function;

/**
 * Sequence {@link Function} for {@link LongIdGenerator} using an in-memory counter that is NOT persistent.
 */
public final class LongIdMemorySequence implements Function<Id<?>, Long> {

  private volatile long sequence;

  /**
   * The constructor.
   */
  public LongIdMemorySequence() {

    this(10_000_000L); // enough room for your own master data
  }

  /**
   * The constructor.
   *
   * @param sequenceStart the start value of the sequence.
   */
  public LongIdMemorySequence(long sequenceStart) {

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
  public Long apply(Id<?> t) {

    Long value = Long.valueOf(this.sequence++);
    return value;
  }

}