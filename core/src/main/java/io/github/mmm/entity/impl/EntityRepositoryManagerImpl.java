/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import io.github.mmm.base.collection.ReadOnlyIterator;
import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.repository.EntityRepository;
import io.github.mmm.entity.repository.EntityRepositoryManager;

/**
 * Implementation of {@link EntityRepositoryManager}.
 *
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public class EntityRepositoryManagerImpl implements EntityRepositoryManager {

  /** The singleton instance. */
  public static final EntityRepositoryManagerImpl INSTANCE = new EntityRepositoryManagerImpl();

  private final Map<Class, EntityRepository<?>> repositoryMap;

  /**
   * The constructor.
   */
  public EntityRepositoryManagerImpl() {

    super();
    this.repositoryMap = new HashMap<>();
    ServiceLoader<EntityRepository> serviceLoader = ServiceLoader.load(EntityRepository.class);
    for (EntityRepository<?> repository : serviceLoader) {
      registerRepository(repository);
    }
  }

  /**
   * @param repository the {@link EntityRepository} to register.
   */
  private void registerRepository(EntityRepository<?> repository) {

    Class<?> entityClass = repository.getEntityClass();
    if (entityClass == null) {
      throw new ObjectNotFoundException("EntityClass", repository);
    }
    this.repositoryMap.put(entityClass, repository);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E extends Entity> EntityRepository<E> getRepository(Class<E> entityClass) {

    return (EntityRepository) this.repositoryMap.get(entityClass);
  }

  @Override
  public Iterator<EntityRepository<?>> iterator() {

    return new ReadOnlyIterator<>(this.repositoryMap.values().iterator());
  }
}
