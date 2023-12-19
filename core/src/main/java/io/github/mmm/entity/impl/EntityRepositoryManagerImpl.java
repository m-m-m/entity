/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import io.github.mmm.base.collection.ReadOnlyIterator;
import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.base.service.ServiceHelper;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.repository.EntityRepository;
import io.github.mmm.entity.repository.EntityRepositoryManager;

/**
 * Implementation of {@link EntityRepositoryManager}.
 *
 * @since 1.0.0
 */
public class EntityRepositoryManagerImpl implements EntityRepositoryManager {

  /** The singleton instance. */
  public static final EntityRepositoryManagerImpl INSTANCE = new EntityRepositoryManagerImpl();

  private final Map<Class<?>, EntityRepository<?>> repositoryMap;

  /**
   * The constructor.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public EntityRepositoryManagerImpl() {

    super();
    this.repositoryMap = new HashMap<>();
    ServiceLoader<EntityRepository<?>> serviceLoader = (ServiceLoader) ServiceLoader.load(EntityRepository.class);
    ServiceHelper.all(serviceLoader, this.repositoryMap, EntityRepositoryManagerImpl::getEntityClass);
  }

  private static Class<?> getEntityClass(EntityRepository<?> repository) {

    Class<?> entityClass = repository.getEntityClass();
    if (entityClass == null) {
      throw new ObjectNotFoundException("EntityClass", repository);
    }
    return entityClass;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <E extends Entity> EntityRepository<E> getRepository(Class<E> entityClass) {

    return (EntityRepository) this.repositoryMap.get(entityClass);
  }

  @Override
  public Iterator<EntityRepository<?>> iterator() {

    return new ReadOnlyIterator<>(this.repositoryMap.values().iterator());
  }
}
