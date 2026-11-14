/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.generator;

import io.github.mmm.entity.id.Id;

/// Interface for a generator of [Id]s that are unique (for a type of [io.github.mmm.entity.Entity]).
///
/// @since 1.0.0
public interface IdGenerator {

  /// @param <E> type of the [io.github.mmm.entity.Entity].
  /// @param template the [transient][Id#isTransient()] [Id] of the [entity][io.github.mmm.entity.Entity]
  ///        to insert.
  /// @return the new unique [Id].
  <E> Id<E> generate(Id<E> template);

}
