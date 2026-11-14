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
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.marshall.id.StructuredIdMapping;
import io.github.mmm.marshall.id.StructuredIdMappingMap;
import io.github.mmm.marshall.id.StructuredIdMappingObject;

/// This is the interface for a link to a persistent entity. It acts as a reference that can be evaluated lazily. It
/// will hold the [primary key][#getId()] of the linked entity.
///
/// @param <E> type of the [linked entity][#getEntity()].
///
/// @see io.github.mmm.entity.Entity
///
/// @since 1.0.0
public interface Link<E> extends StructuredIdMappingObject {

  /// Name of the [ID][#getId()] property (e.g. for JSON or XML).
  String PROPERTY_ID = "id";

  /// Name of the [target][#getEntity()] property (e.g. for JSON or XML).
  String PROPERTY_TARGET = "t";

  /// @return the [unique identifier][io.github.mmm.entity.Entity#getId()] of the linked [entity][#getEntity()]. When
  ///         creating new [Entities][io.github.mmm.entity.Entity] a link may hold a transient
  ///         [io.github.mmm.entity.Entity] as [target][#getEntity()] that has no [primary key][Id#getPk()] assigned,
  ///         yet. In such case this method will return an [empty][Id#isEmpty()] [Id].
  Id<E> getId();

  /// @return `true` if the [link target][#getEntity()] is already resolved, `false` otherwise (if [#getEntity()] may
  ///         trigger lazy loading and could also fail if called without an open transaction).
  boolean isResolved();

  /// This method resolves the linked entity. **ATTENTION:** A [Link] can be evaluated lazily. In such case the first
  /// call of this method will resolve the linked entity from the database. That can be a relatively expensive operation
  /// and requires an open transaction. Use [#isResolved()] to distinguish and prevent undesired link resolving. Further,
  /// after serialisation (e.g. mapping to JSON and back) maybe only the [ID][#getId()] was transferred and this link can
  /// not be resolved. In that case this method may return `null`. Please note that [id][#getId()] and
  /// [target][#getEntity()] can not both be `null`.
  ///
  /// @return the link target or `null` if the link can not be resolved.
  E getEntity();

  /// @return `true` if this link is empty, `false` otherwise.
  /// @see Id#isEmpty()
  default boolean isEmpty() {

    Id<E> id = getId();
    if ((id != null) && !id.isEmpty()) {
      return false;
    }
    if (isResolved()) {
      E entity = getEntity();
      if (entity != null) {
        return false;
      }
    }
    return true;
  }

  @Override
  default StructuredIdMapping defineIdMapping() {

    return StructuredIdMappingMap.of(PROPERTY_ID, PROPERTY_TARGET);
  }

  /// @param <E> type of [Entity].
  /// @param entity the [Entity].
  /// @return the [Link] for the given [Entity]. Will be `null` if the given [Entity] was `null`.
  static <E extends Entity> Link<E> of(E entity) {

    if (entity == null) {
      return null;
    }
    return new EntityLinkWithoutRevision<>(entity);
  }

  /// @param <E> type of the [linked][#getEntity()] [io.github.mmm.entity.Entity].
  /// @param id the [id][#getId()].
  /// @return the [Link] for the given [Id] [without the revision][GenericId#withoutRevision()].
  public static <E> Link<E> of(Id<E> id) {

    if (id == null) {
      return null;
    }
    return new IdLinkWithoutRevision<>(id, null);
  }

  /// @param <E> type of [Entity].
  /// @param entities the [entities][Entity] to convert to [Link]s.
  /// @return the [Set] of [Link]s for the given [entities][Entity].
  @SuppressWarnings("unchecked")
  static <E extends Entity> Set<Link<E>> ofSet(E... entities) {

    if (entities == null) {
      return Collections.emptySet();
    }
    Set<Link<E>> links = new HashSet<>(entities.length);
    ofCollection(links, entities);
    return links;
  }

  /// @param <E> type of [Entity].
  /// @param ids the [Id]s to convert to [Link]s.
  /// @return the [Set] of [Link]s for the given [Id]s.
  @SuppressWarnings("unchecked")
  static <E extends Entity> Set<Link<E>> ofSet(Id<E>... ids) {

    if (ids == null) {
      return Collections.emptySet();
    }
    Set<Link<E>> links = new HashSet<>(ids.length);
    ofCollection(links, ids);
    return links;
  }

  /// @param <E> type of [Entity].
  /// @param entities the [entities][Entity] to convert to [Link]s.
  /// @return the [Set] of [Link]s for the given [entities][Entity].
  static <E extends Entity> Set<Link<E>> ofSet(Collection<E> entities) {

    if (entities == null) {
      return Collections.emptySet();
    }
    Set<Link<E>> links = new HashSet<>(entities.size());
    ofCollection(links, entities);
    return links;
  }

  /// @param <E> type of [Entity].
  /// @param entities the [entities][Entity] to convert to [Link]s.
  /// @return the [List] of [Link]s for the given [entities][Entity].
  @SuppressWarnings("unchecked")
  static <E extends Entity> List<Link<E>> ofList(E... entities) {

    if (entities == null) {
      return Collections.emptyList();
    }
    List<Link<E>> links = new ArrayList<>(entities.length);
    ofCollection(links, entities);
    return links;
  }

  /// @param <E> type of [Entity].
  /// @param ids the [Id]s to convert to [Link]s.
  /// @return the [List] of [Link]s for the given [Id]s.
  @SuppressWarnings("unchecked")
  static <E extends Entity> List<Link<E>> ofList(Id<E>... ids) {

    if (ids == null) {
      return Collections.emptyList();
    }
    List<Link<E>> links = new ArrayList<>(ids.length);
    ofCollection(links, ids);
    return links;
  }

  /// @param <E> type of [Entity].
  /// @param entities the [entities][Entity] to convert to [Link]s.
  /// @return the [EntityLink] for the given [Entity]. Will be `null` if the given [Entity] was `null`.
  static <E extends Entity> List<Link<E>> ofList(Collection<E> entities) {

    if (entities == null) {
      return Collections.emptyList();
    }
    List<Link<E>> links = new ArrayList<>(entities.size());
    ofCollection(links, entities);
    return links;
  }

  /// @param <E> type of [Entity].
  /// @param links the [Collection] where to [add][Collection#add(Object)] the [Link]s to create from the given
  ///        [entities][Entity].
  /// @param entities the [entities][Entity] to convert to [Link]s.
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

  /// @param <E> type of [Entity].
  /// @param links the [Collection] where to [add][Collection#add(Object)] the [Link]s to create from the given
  ///        [entities][Entity].
  /// @param entities the [entities][Entity] to convert to [Link]s.
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

  /// @param <E> type of [Entity].
  /// @param links the [Collection] where to [add][Collection#add(Object)] the [Link]s to create from the given [Id]s.
  /// @param ids the [Id]s to convert to [Link]s.
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

  /// Null-safe variant of [Link#getId()].
  ///
  /// @param <E> type of the [linked][#getEntity()] entity.
  /// @param link the [Link] to get the [Id] from. May be `null`.
  /// @return the [ID][Link#getId()] contained in the given [Link] or `null` if [Link] was `null`.
  static <E> Id<E> getId(Link<E> link) {

    if (link != null) {
      return link.getId();
    }
    return null;
  }

}
