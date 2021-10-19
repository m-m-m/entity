/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.dao;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.sql.delete.DeleteStatement;
import io.github.mmm.entity.bean.sql.insert.InsertStatement;
import io.github.mmm.entity.bean.sql.select.SelectStatement;
import io.github.mmm.entity.bean.sql.update.UpdateStatement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface for a DAO or repository to manage a specific {@link EntityBean} and map it to a persistent store
 * (database).
 *
 * @param <E> type of the {@link EntityBean}.
 * @since 1.0.0
 */
public interface AdvancedDao<E extends EntityBean> extends SimpleDao<E> {

  /**
   * @param select the {@link SelectStatement} used as query.
   * @return the matching {@link EntityBean}s.
   */
  Flux<E> find(SelectStatement<E> select);

  /**
   * @param update the {@link UpdateStatement} to perform.
   * @return the number of records updated.
   */
  Mono<Long> save(UpdateStatement<E> update);

  /**
   * @param insert the {@link InsertStatement} to perform.
   * @return {@link Mono} of {@link Void} to distinguish success or error.
   */
  Mono<Void> save(InsertStatement<E> insert);

  /**
   * @param delete the {@link DeleteStatement} to perform.
   * @return the number of records deleted.
   */
  Mono<Long> delete(DeleteStatement<E> delete);

}
