/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.id;

import java.util.HashMap;
import java.util.Map;

/**
 * Accessor to {@link #get(Class) get} {@link IdFactory} for {@link Id} implementation {@link Class}.
 *
 * @since 1.0.0
 * @see #get()
 */
public class IdFactories {

  private final Map<Class<? extends Id<?>>, IdFactory<?, ?, ?>> factoryMap;

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
    register(LongVersionId.FACTORY);
    register(StringInstantId.FACTORY);
    register(StringVersionId.FACTORY);
    register(UuidInstantId.FACTORY);
    register(UuidVersionId.FACTORY);
  }

  /**
   * @param factory the {@link IdFactory} to register.
   */
  protected void register(IdFactory<?, ?, ?> factory) {

    IdFactory<?, ?, ?> duplicate = this.factoryMap.putIfAbsent(factory.getIdClass(), factory);
    if (duplicate != null) {
      throw new IllegalArgumentException("Duplicate IdFactory for " + factory.getIdClass());
    }
  }

  /**
   * @param idClass {@link Class} of {@link Id} implementation.
   * @return the {@link IdFactory} {@link IdFactory#getIdClass() responsible for the given idClass}.
   */
  public IdFactory<?, ?, ?> get(Class<? extends Id<?>> idClass) {

    return this.factoryMap.get(idClass);
  }

  /**
   * @param <I> type of the {@link Id#getId() primary key}.
   * @param <V> type of the {@link Id#getVersion() version}.
   * @param <ID> type of {@link Id} implementation.
   * @param idClass {@link Class} of {@link Id} implementation.
   * @return the {@link IdFactory} {@link IdFactory#getIdClass() responsible for the given idClass}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <I, V extends Comparable<?>, ID extends AbstractId<?, I, V>> IdFactory<I, V, ID> getGeeneric(
      Class<ID> idClass) {

    IdFactory factory = this.factoryMap.get(idClass);
    return factory;
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
