/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.HashMap;
import java.util.Map;

/**
 * Accessor to {@link #get(Class) get} {@link IdFactory} for {@link Id} implementation {@link Class}.
 *
 * @since 1.0.0
 * @see #get()
 */
public class IdFactories {

  private final Map<Class<? extends Id<?>>, IdFactory<?, ?>> factoryMap;

  private static IdFactories instance;

  /**
   * The constructor.
   */
  protected IdFactories() {

    super();
    this.factoryMap = new HashMap<>();
    init();
    if (instance == null) {
      instance = this;
    }
  }

  /**
   * Initializes the defaults. May be overridden to opt out or replace.
   */
  protected void init() {

    register(LongInstantId.FACTORY);
    register(LongLatestId.FACTORY);
    register(LongVersionId.FACTORY);
    register(StringInstantId.FACTORY);
    register(StringLatestId.FACTORY);
    register(StringVersionId.FACTORY);
    register(UuidInstantId.FACTORY);
    register(UuidLatestId.FACTORY);
    register(UuidVersionId.FACTORY);
  }

  /**
   * @param factory the {@link IdFactory} to register.
   */
  protected void register(IdFactory<?, ?> factory) {

    IdFactory<?, ?> duplicate = this.factoryMap.putIfAbsent(factory.getIdClass(), factory);
    if (duplicate != null) {
      throw new IllegalArgumentException("Duplicate IdFactory for " + factory.getIdClass());
    }
  }

  /**
   * @param idClass {@link Class} of {@link Id} implementation.
   * @return the {@link IdFactory} {@link IdFactory#getIdClass() responsible for the given idClass}.
   */
  public IdFactory<?, ?> get(Class<? extends Id<?>> idClass) {

    return this.factoryMap.get(idClass);
  }

  /**
   * @return the singleton instance of {@link IdFactories}.
   */
  public static IdFactories get() {

    if (instance == null) {
      new IdFactories();
    }
    return instance;
  }

}
