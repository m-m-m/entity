package io.github.mmm.entity.bean.sql;

import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.PredicateOperator;

/**
 * A {@link TypedClause} that supports a {@link #where(CriteriaPredicate) WHERE} {@link Clause}.
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public interface TypedClauseWithWhere<E> extends TypedClause<E> {

  /**
   * @param predicate the {@link CriteriaPredicate} to add as {@link Where}-clause.
   * @return the {@link Where}-{@link Clause} for fluent API calls.
   */
  Where<E, ?> where(CriteriaPredicate predicate);

  /**
   * @param predicates the {@link CriteriaPredicate}s to add as {@link Where}-clause. They will be combined with
   *        {@link PredicateOperator#AND AND}.
   * @return the {@link Where}-{@link Clause} for fluent API calls.
   */
  Where<E, ?> where(CriteriaPredicate... predicates);

}
