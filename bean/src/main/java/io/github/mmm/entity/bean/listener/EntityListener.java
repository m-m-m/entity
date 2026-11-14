package io.github.mmm.entity.bean.listener;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;

/// Interface for a listener of changes to an [EntityBean]. Methods are implemented to do nothing by default. You
/// need to override the according methods to react on the events.
/// **ATTENTION:**
/// An [EntityListener] should typically never modify an [EntityBean]. If you decide to do so, there will be
/// the risk that future updates may cause problems or errors.
/// In case an [EntityListener] causes [any kind of exception][Throwable], it will not be catched and causes
/// the operation to fail.
///
/// @param <E> type of the [EntityBean].
/// @since 1.0.0
public interface EntityListener<E extends EntityBean> {

  /// Called before the [EntityBean] is inserted into the database.
  ///
  /// @param entity the [EntityBean] to be inserted.
  default void preInsert(E entity) {

  }

  /// Called before the [EntityBean] is updated in the database.
  ///
  /// @param entity the [EntityBean] to update.
  default void preUpdate(E entity) {

  }

  /// Called before the [EntityBean] is deleted from the database.
  ///
  /// @param id the [ID][EntityBean#getId()] of the [EntityBean] to remove.
  default void preDelete(Id<E> id) {

  }

  /// Called before a batch delete of [EntityBean]s from the database.
  ///
  /// @param ids the [Iterable] of the [ID][EntityBean#getId()]s to remove.
  default void preDelete(Iterable<Id<E>> ids) {

    for (Id<E> id : ids) {
      preDelete(id);
    }
  }

  /// Called after an [EntityBean] has been loaded from the database.
  ///
  /// @param entity the [EntityBean] that has been loaded.
  default void postLoad(E entity) {

  }

}
