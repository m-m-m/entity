/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.repository;

import io.github.mmm.bean.ReadableBean;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdGenerator;
import io.github.mmm.entity.repository.EntityRepository;

/**
 * Abstract base implementation of {@link EntityRepository}.
 *
 * @param <E> type of the managed {@link Entity}.
 * @since 1.0.0
 */
public abstract class AbstractEntityRepository<E extends EntityBean> implements EntityRepository<E> {

  /** @see #getPrototype() */
  protected final E prototype;

  /** @see #getEntityClass() */
  protected final Class<E> entityClass;

  /** {@link IdGenerator} used to {@link IdGenerator#generate(Id) generate} new unique {@link Id}s. */
  protected final IdGenerator idGenerator;

  /**
   * The constructor.
   *
   * @param prototype the {@link #getPrototype() prototype}.
   * @param idGenerator the {@link IdGenerator} used to {@link IdGenerator#generate(Id) generate} new unique
   *        {@link Id}s.
   */
  public AbstractEntityRepository(E prototype, IdGenerator idGenerator) {

    super();
    this.prototype = prototype;
    this.entityClass = ReadableBean.getJavaClass(this.prototype);
    this.idGenerator = idGenerator;
  }

  /**
   * @return an instance of the managed {@link EntityBean entity} to be used as template. Please note that this is an
   *         internal method of the implementation that should only be used by framework code. Mutations on the
   *         prototype by callers of this method are strictly forbidden and will lead to severe bugs.
   */
  public E getPrototype() {

    return this.prototype;
  }

  /**
   * @return the {@link Class} reflecting the managed {@link EntityBean}.
   */
  @Override
  public Class<E> getEntityClass() {

    return this.entityClass;
  }

  @Override
  public void save(E entity) {

    Id<?> id = entity.getId();
    if (id.isTransient()) {
      id = this.idGenerator.generate(id);
      entity.setId(id);
      insert(entity);
    } else {
      update(entity);
    }
  }

  /**
   * @param entity the {@link EntityBean} to insert.
   */
  protected abstract void insert(E entity);

  /**
   * @param entity the {@link EntityBean} to update.
   */
  protected abstract void update(E entity);

  /**
   * @return {@code true} to verify and update the {@link io.github.mmm.entity.id.Id#getRevision() revision} on
   *         {@link #save(EntityBean) save}.
   */
  protected boolean isUseRevision() {

    return true;
  }

}
