/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.listener;

import java.util.Arrays;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;

/**
 * Adapter for {@link EntityListener}s.
 *
 * @param <E> type of the {@link EntityBean}s.
 * @since 1.0.0
 * @see EntityListener
 */
public abstract class EntityListenerAdapter<E extends EntityBean> implements EntityListener<E> {

  private static final Empty EMPTY = new Empty();

  EntityListenerAdapter() {

    super();
  }

  /**
   * @param listener the {@link EntityListener} to add.
   * @return this adapter itself or a new instance capable to handle more listeners.
   */
  public abstract EntityListenerAdapter<E> add(EntityListener<? super E> listener);

  /**
   * @param listener the {@link EntityListener} to remove.
   * @return {@code null} if the given {@link EntityListener} was not registered and nothing changed, otherwise this
   *         adapter itself or a new instance capable to handle less listeners.
   */
  public abstract EntityListenerAdapter<E> remove(EntityListener<? super E> listener);

  /**
   * @return the number of {@link #add(EntityListener) registered} {@link EntityListener}s.
   * @see #get(int)
   */
  abstract int size();

  /**
   * @param index the index of the requested {@link EntityListener} in the range from {@code 0} to
   *        <code>{@link #size()} - 1</code>.
   * @return the requested {@link EntityListener} or {@code null} if index is out of bounds.
   * @see #size()
   */
  abstract EntityListener<? super E> get(int index);

  /**
   * @param <E> type of the {@link EntityBean}s.
   * @return the empty {@link EntityListenerAdapter}. Is used as a singleton instance for best efficiency allocating
   *         absolutely no resources until a listener gets {@link #add(EntityListener) added}.
   */
  @SuppressWarnings("unchecked")
  public static <E extends EntityBean> EntityListenerAdapter<E> empty() {

    return EMPTY;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static class Empty extends EntityListenerAdapter {

    private Empty() {

      super();
    }

    @Override
    public EntityListenerAdapter add(EntityListener listener) {

      return new Single<>(listener);
    }

    @Override
    public EntityListenerAdapter remove(EntityListener listener) {

      return null;
    }

    @Override
    public int size() {

      return 0;
    }

    @Override
    public EntityListener get(int index) {

      return null;
    }

    @Override
    public void preDelete(Iterable ids) {

    }
  }

  private static class Single<E extends EntityBean> extends EntityListenerAdapter<E> {

    private final EntityListener<? super E> listener;

    private Single(EntityListener<? super E> listener) {

      super();
      this.listener = listener;
    }

    @Override
    public EntityListenerAdapter<E> add(EntityListener<? super E> l) {

      return new Multi<>(this.listener, l);
    }

    @SuppressWarnings("unchecked")
    @Override
    public EntityListenerAdapter<E> remove(EntityListener<? super E> l) {

      if (l == this.listener) {
        return EMPTY;
      }
      return null;
    }

    @Override
    public int size() {

      return 1;
    }

    @Override
    public EntityListener<? super E> get(int index) {

      if (index == 0) {
        return this.listener;
      }
      return null;
    }

    @Override
    public void preInsert(E entity) {

      this.listener.preInsert(entity);
    }

    @Override
    public void preUpdate(E entity) {

      this.listener.preUpdate(entity);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void preDelete(Id<E> id) {

      this.listener.preDelete((Id) id);
    }
  }

  @SuppressWarnings("unchecked")
  private static class Multi<E extends EntityBean> extends EntityListenerAdapter<E> {

    private EntityListener<? super E>[] listeners;

    private int listenerCount;

    private boolean locked;

    private Multi(EntityListener<? super E> first, EntityListener<? super E> second) {

      super();
      this.listeners = new EntityListener[] { first, second };
      this.listenerCount = 2;
    }

    @Override
    public EntityListenerAdapter<E> add(EntityListener<? super E> listener) {

      int oldCapacity = this.listeners.length;
      if (this.locked) {
        int newCapacity = (this.listenerCount < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
        this.listeners = Arrays.copyOf(this.listeners, newCapacity);
      } else if (this.listenerCount == oldCapacity) {
        int newCapacity = (oldCapacity * 3) / 2 + 1;
        this.listeners = Arrays.copyOf(this.listeners, newCapacity);
      }
      this.listeners[this.listenerCount++] = listener;
      return this;
    }

    @Override
    public EntityListenerAdapter<E> remove(EntityListener<? super E> listener) {

      for (int i = 0; i < this.listenerCount; i++) {
        if (listener == this.listeners[i]) {
          if (this.listenerCount == 2) {
            return new Single<>(this.listeners[1 - i]);
          } else {
            EntityListener<? super E>[] oldListeners = this.listeners;
            if (this.locked) {
              this.listeners = new EntityListener[this.listeners.length];
              System.arraycopy(oldListeners, 0, this.listeners, 0, i);
            }
            int remaining = this.listenerCount - i - 1;
            if (remaining > 0) {
              System.arraycopy(oldListeners, i + 1, this.listeners, i, remaining);
            }
            this.listenerCount--;
            if (!this.locked) {
              this.listeners[this.listenerCount] = null;
            }
          }
          return this;
        }
      }
      return null;
    }

    @Override
    public void preInsert(E entity) {

      try {
        this.locked = true;
        for (EntityListener<? super E> listener : this.listeners) {
          listener.preInsert(entity);
        }
      } finally {
        this.locked = false;
      }
    }

    @Override
    public void preUpdate(E entity) {

      try {
        this.locked = true;
        for (EntityListener<? super E> listener : this.listeners) {
          listener.preUpdate(entity);
        }
      } finally {
        this.locked = false;
      }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void preDelete(Id<E> id) {

      try {
        this.locked = true;
        for (EntityListener<? super E> listener : this.listeners) {
          listener.preDelete((Id) id);
        }
      } finally {
        this.locked = false;
      }
    }

    @Override
    public int size() {

      return this.listenerCount;
    }

    @Override
    public EntityListener<? super E> get(int index) {

      if ((index >= 0) && (index < this.listenerCount)) {
        return this.listeners[index];
      }
      return null;
    }
  }

}
