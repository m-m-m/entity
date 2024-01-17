/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.sequence;

import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongId;
import io.github.mmm.entity.id.generator.IdGenerator;

/**
 * Interface for a sequence that can generate the next unique {@link Id#get() primary key} of type {@link Long} for a
 * new {@link LongId}.
 *
 * @see IdGenerator#generate(Id)
 */
public interface IdSequence {

  /**
   * @param template the {@link Id#isTransient() transient} {@link Id} of the {@link io.github.mmm.entity.Entity entity}
   *        to insert.
   * @return the next long value guaranteed to be unique for this sequence. Proper implementations delegate to a
   *         database sequence.
   */
  long next(Id<?> template);

}
