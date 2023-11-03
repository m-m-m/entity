package io.github.mmm.entity.bean.db.repository;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.db.statement.select.SelectStatement;
import io.github.mmm.entity.repository.EntityRepository;

/**
 * {@link EntityRepository} allowing {@link #findByQuery(SelectStatement)}
 *
 * @param <E> type of the managed {@link EntityBean}.
 */
public interface DbRepository<E extends EntityBean> extends EntityRepository<E> {

  /**
   * @param query the {@link SelectStatement} to query the requested entities.
   * @return an {@link Iterable} with the matching {@link EntityBean entities}.
   */
  Iterable<E> findByQuery(SelectStatement<E> query);

}
