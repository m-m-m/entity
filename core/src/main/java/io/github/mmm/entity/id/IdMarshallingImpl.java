/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Default implementation of {@link IdMarshalling}.
 */
final class IdMarshallingImpl implements IdMarshalling {

  /** The signleton instance. */
  static final IdMarshallingImpl INSTANCE = new IdMarshallingImpl();

  private IdMarshallingImpl() {

  }

}
