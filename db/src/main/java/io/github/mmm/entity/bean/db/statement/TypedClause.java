/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/**
 * A {@link DbClause} that is typed by the {@link EntityBean}.
 *
 * @param <E> type of this {@link DbClause} (typically the {@link AbstractEntityClause#getEntity() entity}).
 * @since 1.0.0
 */
public interface TypedClause<E> extends DbClause {

}
