package io.github.mmm.entity.bean.db.repository;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.repository.AbstractEntityRepository;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdGenerator;

/**
 * Abstract base implementation of {@link DbRepository}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 */
public abstract class AbstractDbRepository<E extends EntityBean> extends AbstractEntityRepository<E>
    implements DbRepository<E> {

  /**
   * The constructor.
   *
   * @param idGenerator the {@link IdGenerator} used to {@link IdGenerator#generate(Id) generate} new unique
   *        {@link Id}s.
   */
  public AbstractDbRepository(E prototype, IdGenerator idGenerator) {

    super(prototype, idGenerator);
  }

}
