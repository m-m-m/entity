/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.Id;

/**
 * This is the interface for a link to a persistent entity. It acts as a reference that can be evaluated lazily. It will
 * hold the {@link #getId() primary key} of the linked entity.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} entity.
 *
 * @see io.github.mmm.entity.Entity
 *
 * @since 1.0.0
 */
public interface Link<E> {

  /**
   * @return the {@link io.github.mmm.entity.Entity#getId() unique identifier} of the linked {@link #getTarget() bean}.
   *         When creating new {@link io.github.mmm.entity.Entity Entities} a link may hold a transient
   *         {@link io.github.mmm.entity.Entity} as {@link #getTarget() target} that has no ID assigned, yet. In such case
   *         this method will return {@code null}.
   */
  Id<E> getId();

  /**
   * @return {@code true} if the {@link #getTarget() link target} is already resolved, {@code false} otherwise (if
   *         {@link #getTarget()} may trigger lazy loading and could also fail if called without an open transaction).
   */
  boolean isResolved();

  /**
   * This method resolves the linked entity.<br/>
   * <b>ATTENTION:</b><br/>
   * A {@link Link} can be evaluated lazily. In such case the first call of this method will resolve the linked entity
   * from the database. That can be a relatively expensive operation and requires an open transaction. Use
   * {@link #isResolved()} to distinguish and prevent undesired link resolving.<br>
   * Further, after serialization (e.g. mapping to JSON and back) maybe only the {@link #getId() ID} was transferred and
   * this link can not be resolved. In that case this method may return {@code null}. Please note that {@link #getId()
   * id} and {@link #getTarget() target} can not both be null. In case a link property is empty it will contain
   * {@code null} instead of an empty {@link Link} so you can always properly distinguish the scenarios.
   *
   * @return the link target or {@code null} if the link can not be resolved.
   */
  E getTarget();

  /**
   * @param <E> type of {@link Entity}.
   * @param entity the {@link Entity}.
   * @return the {@link Link} for the given {@link Entity}. Will be {@code null} if the given {@link Entity} was
   *         {@code null}.
   */
  static <E extends Entity> Link<E> of(E entity) {

    if (entity == null) {
      return null;
    }
    return new EntityLink<>(entity);
  }

  /**
   * @param <E> type of the {@link #getTarget() linked} {@link io.github.mmm.entity.Entity}.
   * @param id the {@link #getId() id}.
   * @return the {@link Link} for the given {@link Id}.
   */
  public static <E> Link<E> of(Id<E> id) {

    return new IdLink<>(id, null);
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param entities the {@link Entity entities} to convert to {@link Link}s.
   * @return the {@link Set} of {@link Link}s for the given {@link Entity entities}.
   */
  @SuppressWarnings("unchecked")
  static <E extends Entity> Set<Link<E>> ofSet(E... entities) {

    if (entities == null) {
      return Collections.emptySet();
    }
    Set<Link<E>> links = new HashSet<>(entities.length);
    ofCollection(links, entities);
    return links;
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param ids the {@link Id}s to convert to {@link Link}s.
   * @return the {@link Set} of {@link Link}s for the given {@link Id}s.
   */
  @SuppressWarnings("unchecked")
  static <E extends Entity> Set<Link<E>> ofSet(Id<E>... ids) {

    if (ids == null) {
      return Collections.emptySet();
    }
    Set<Link<E>> links = new HashSet<>(ids.length);
    ofCollection(links, ids);
    return links;
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param entities the {@link Entity entities} to convert to {@link Link}s.
   * @return the {@link Set} of {@link Link}s for the given {@link Entity entities}.
   */
  static <E extends Entity> Set<Link<E>> ofSet(Collection<E> entities) {

    if (entities == null) {
      return Collections.emptySet();
    }
    Set<Link<E>> links = new HashSet<>(entities.size());
    ofCollection(links, entities);
    return links;
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param entities the {@link Entity entities} to convert to {@link Link}s.
   * @return the {@link List} of {@link Link}s for the given {@link Entity entities}.
   */
  @SuppressWarnings("unchecked")
  static <E extends Entity> List<Link<E>> ofList(E... entities) {

    if (entities == null) {
      return Collections.emptyList();
    }
    List<Link<E>> links = new ArrayList<>(entities.length);
    ofCollection(links, entities);
    return links;
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param ids the {@link Id}s to convert to {@link Link}s.
   * @return the {@link List} of {@link Link}s for the given {@link Id}s.
   */
  @SuppressWarnings("unchecked")
  static <E extends Entity> List<Link<E>> ofList(Id<E>... ids) {

    if (ids == null) {
      return Collections.emptyList();
    }
    List<Link<E>> links = new ArrayList<>(ids.length);
    ofCollection(links, ids);
    return links;
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param entities the {@link Entity entities} to convert to {@link Link}s.
   * @return the {@link EntityLink} for the given {@link Entity}. Will be {@code null} if the given {@link Entity} was
   *         {@code null}.
   */
  static <E extends Entity> List<Link<E>> ofList(Collection<E> entities) {

    if (entities == null) {
      return Collections.emptyList();
    }
    List<Link<E>> links = new ArrayList<>(entities.size());
    ofCollection(links, entities);
    return links;
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param links the {@link Collection} where to {@link Collection#add(Object) add} the {@link Link}s to create from
   *        the given {@link Entity entities}.
   * @param entities the {@link Entity entities} to convert to {@link Link}s.
   */
  @SuppressWarnings("unchecked")
  static <E extends Entity> void ofCollection(Collection<Link<E>> links, E... entities) {

    if (entities == null) {
      return;
    }
    for (E entity : entities) {
      Link<E> link = of(entity);
      if (link != null) {
        links.add(link);
      }
    }
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param links the {@link Collection} where to {@link Collection#add(Object) add} the {@link Link}s to create from
   *        the given {@link Entity entities}.
   * @param entities the {@link Entity entities} to convert to {@link Link}s.
   */
  static <E extends Entity> void ofCollection(Collection<Link<E>> links, Collection<E> entities) {

    if (entities == null) {
      return;
    }
    for (E entity : entities) {
      Link<E> link = of(entity);
      if (link != null) {
        links.add(link);
      }
    }
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param links the {@link Collection} where to {@link Collection#add(Object) add} the {@link Link}s to create from
   *        the given {@link Id}s.
   * @param ids the {@link Id}s to convert to {@link Link}s.
   */
  @SuppressWarnings("unchecked")
  static <E extends Entity> void ofCollection(Collection<Link<E>> links, Id<E>... ids) {

    if (ids == null) {
      return;
    }
    for (Id<E> id : ids) {
      Link<E> link = of(id);
      if (link != null) {
        links.add(link);
      }
    }
  }

}
