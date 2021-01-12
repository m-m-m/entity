/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.UUID;

import io.github.mmm.entity.Entity;

/**
 * This is the interface for an ID that uniquely identifies a {@link Entity persistent entity} of a particular
 * {@link #getType() type} ({@code <E>}). <br>
 * An {@link Id} has the following properties:
 * <ul>
 * <li>{@link #getId() object-id} - the primary key that identifies the entity and is unique for a specific
 * {@link #getType() type}. As a best practice it is recommended to make the object-id even unique for all entities of a
 * database.</li>
 * <li>{@link #getType() type} - is the (optional) type of the identified entity.</li>
 * <li>{@link #getVersion() version} - the optional version (revision) of the entity.</li>
 * </ul>
 * Just like the {@link #getId() primary key} the {@link #getVersion() version} and {@link #getType() type} of an object
 * do not change. This allows to use the {@link Id} as globally unique identifier for its corresponding entity.<br>
 * An {@link Id} has a compact {@link #toString() string representation}. However, for structured representation and
 * marshaling use {@link IdMarshalling} and convert to JSON, XML, or other structured format.
 *
 * @param <E> type of the identified entity.
 *
 * @see AbstractId
 * @since 1.0.0
 */
public interface Id<E> {

  /** Name of the {@link #getId() id} property. */
  String PROPERTY_ID = "id";

  /** Name of the {@link #getVersion() version} property. */
  String PROPERTY_VERSION = "version";

  /**
   * The value used as {@link #getVersion() version} if it unspecified. If you are using an {@link Id} as link to an
   * {@link Entity} you will use this value to point to the recent version of the {@link io.github.mmm.entity.Entity}.
   */
  Comparable<?> VERSION_LATEST = null;

  /** The separator for the {@link #getVersion() version}. */
  char VERSION_SEPARATOR = '@';

  /**
   * @see LongVersionId
   * @see UuidVersionId
   * @see StringVersionId
   *
   * @return the <em>primary key</em> of the identified {@link Entity} as {@link Object} value. It is only unique for a
   *         particular {@link #getType() type} of an <em>entity</em>.
   */
  Object getId();

  /**
   * @return the {@link #getId() primary key} as {@link String} for marshalling.
   */
  default String getIdAsString() {

    Object id = getId();
    if (id == null) {
      return null; // should never happen...
    }
    return id.toString();
  }

  /**
   * @return the {@link Class} reflecting the <em>type</em> of the referenced <em>entity</em>. May be {@code null} if
   *         not available.
   */
  Class<E> getType();

  /**
   * <b>ATTENTION</b>: This method is designed to ensure and verify the expected {@link #getType() type}. It will fail
   * if a different type is already assigned.
   *
   * @param type the new value of {@link #getType()}. Exact type should actually be {@link Class}{@literal <E>} but this
   *        prevents simple usage. As the {@link #getType() type} can not actually be changed with this method, this
   *        should be fine.
   * @return a copy of this {@link Id} with the given {@link #getType() type} or this {@link Id} itself if already
   *         satisfying.
   * @throws IllegalArgumentException if this {@link Id} already has a different {@link #getType() type} assigned.
   */
  Id<E> withType(Class<?> type);

  /**
   * @return a copy of this {@link Id} with a {@link #getVersion() version} of {@link #VERSION_LATEST} e.g. to use the
   *         {@link Id} as regular <em>foreign key</em> (pointing to the latest revision and not a historic revision) or
   *         this {@link Id} itself if already satisfying.
   */
  Id<E> withLatestVersion();

  /**
   * @return the {@code version} of this entity. Whenever the {@link io.github.mmm.entity.Entity} gets updated (a
   *         modification is saved and the transaction is committed), this version is increased. Typically the version
   *         is a {@link Number} starting with {@code 0} for a new {@link io.github.mmm.entity.Entity} that is increased
   *         whenever a modification is committed. However, it may also be an {@link java.time.Instant}. The version
   *         acts as a modification sequence for optimistic locking. On each update it will be verified that the version
   *         has not been increased already by another transaction. When linking an {@link io.github.mmm.entity.Entity}
   *         ({@link Id} used as foreign key) the version can act as revision for auditing. If it is {@code null} it
   *         points to the {@link LatestVersion latest version} of the {@link io.github.mmm.entity.Entity}. Otherwise it
   *         points to a specific historic revision of the {@link io.github.mmm.entity.Entity}. Depending on the
   *         database technology (e.g. when using hibernate envers) the version and the revision can be semantically
   *         different. In such case a {@link io.github.mmm.entity.Entity#getId() primary key} can not be converted 1:1
   *         as revisioned foreign key {@link Id}.
   */
  Comparable<?> getVersion();

  /**
   * @return the {@link #getVersion() version} as {@link String} for marshalling.
   */
  default String getVersionAsString() {

    Object version = getVersion();
    if (version == null) {
      return null; // should never happen...
    }
    return version.toString();
  }

  /**
   * @return the {@link String} representation of this {@link Id}. Will consist of {@link #getId() object-id},
   *         {@link #getType() type} and {@link #getVersion() revision} separated with a specific separator. Segments
   *         that are {@code null} will typically be omitted in the {@link String} representation.
   */
  @Override
  String toString();

  /**
   * Type-safe and {@code null}-safe variant of {@link Entity#getId()}.
   *
   * @param <E> type of {@link Entity}.
   * @param entity the {@link Entity}.
   * @return the {@link Id} {@link Entity#getId() of} the given {@link Entity}. Will be {@code null} if the given
   *         {@link Entity} was {@code null} or {@link Entity#getId() it's ID} is {@code null}.
   * @see Entity#getId()
   */
  @SuppressWarnings("unchecked")
  static <E extends Entity> Id<E> of(E entity) {

    if (entity == null) {
      return null;
    }
    return (Id<E>) entity.getId();
  }

  /**
   * @param <E> type of {@link Entity}.
   * @param type the {@link Class} reflecting the {@link Entity}.
   * @param id the {@link Id#getId() ID}.
   * @param version the optional {@link Id#getVersion() version}.
   * @return the {@link Id} for the given arguments.
   */
  static <E> Id<E> of(Class<E> type, Object id, Object version) {

    if (id == null) {
      assert (version == null);
      return null;
    }
    if (id instanceof Long) {
      return LongId.of(type, (Long) id, version);
    } else if (id instanceof UUID) {
      return UuidId.of(type, (UUID) id, version);
    } else if (id instanceof String) {
      return StringId.of(type, (String) id, version);
    } else {
      throw new IllegalArgumentException("Unsupported id type: " + id.getClass().getName());
    }
  }

}
