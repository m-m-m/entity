/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.sequence;

import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.generator.IdGenerator;

/// Interface for a sequence that can generate the next unique [primary key][Id#getPk()] of type [Long] for a
/// new [io.github.mmm.entity.id.PkIdLong].
///
/// @see IdGenerator#generate(Id)
public interface IdSequence {

  /// @param template the [transient][Id#isTransient()] [Id] of the [entity][io.github.mmm.entity.Entity]
  ///        to insert. May happily be ignored by implementations.
  /// @return the next long value guaranteed to be unique for this sequence. Proper implementations delegate to a
  ///         database sequence.
  long next(Id<?> template);

}
