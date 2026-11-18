/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.generator;

import io.github.mmm.entity.id.Id;

/// [IdGenerator] that will always fail and ensures the primary key must always be assigned manually.
///
/// @since 1.0.0
public class IdGeneratorNone implements IdGenerator {

  private static final IdGeneratorNone INSTANCE = new IdGeneratorNone();

  @Override
  public <E> Id<E> generate(Id<E> template) {

    Class<E> entityType = template.getEntityClass();
    throw new IllegalStateException("Cannot generate primary key for entity " + entityType + ". Please properly configure a sequence name or an ID generator.");
  }

  /**
   * @return the singleton instance.
   */
  public static IdGeneratorNone get() {

    return INSTANCE;
  }

}
