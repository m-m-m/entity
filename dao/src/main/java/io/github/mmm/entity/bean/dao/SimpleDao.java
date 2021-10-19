/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.dao;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.link.Link;
import reactor.core.publisher.Mono;

/**
 * Interface for a DAO or repository to manage a specific {@link EntityBean} and map it to a persistent store
 * (database).
 *
 * @param <E> type of the {@link EntityBean}.
 * @since 1.0.0
 */
public interface SimpleDao<E extends EntityBean> {

  /**
   * @param id the {@link Id} (primary key) of the requested {@link EntityBean}.
   * @return the {@link EntityBean} for the given {@link Id} or {@code null} if no such entity exists.
   */
  Mono<E> find(Id<E> id);

  /**
   * @param bean the {@link EntityBean} to save.
   * @return {@link Mono} of {@link Void} to distinguish success or error.
   */
  Mono<Void> save(E bean);

  /**
   * @param id the {@link Id} (primary key) of the {@link EntityBean} to delete.
   * @return {@link true} if deleted, {@code false} if no such {@link Id} exists.
   */
  Mono<Boolean> delete(Id<E> id);

  /**
   * @param bean the {@link EntityBean} to delete.
   * @return {@link true} if deleted, {@code false} otherwise.
   */
  default Mono<Boolean> delete(E bean) {

    if (bean != null) {
      Id<E> id = Id.from(bean);
      if (id != null) {
        return delete(id);
      }
    }
    return Mono.just(Boolean.FALSE);
  }

  /**
   * @param link the {@link Link} pointing to the {@link EntityBean} to delete.
   * @return {@link true} if deleted, {@code false} otherwise.
   */
  default Mono<Boolean> delete(Link<E> link) {

    if (link != null) {
      Id<E> id = link.getId();
      if (id != null) {
        return delete(id);
      }
    }
    return Mono.just(Boolean.FALSE);
  }

}
