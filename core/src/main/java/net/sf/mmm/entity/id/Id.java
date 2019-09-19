/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.id;

import net.sf.mmm.entity.Entity;

/**
 * This is the interface for an ID that uniquely identifies a persistent entity of a particular {@link #getType() type}
 * ({@code <E>}). <br>
 * An {@link Id} is build out of the following parts:
 * <ul>
 * <li>{@link #getId() object-id} - the primary key that identifies the entity and is unique for a specific
 * {@link #getType() type}.</li>
 * <li>{@link #getType() type} - is the (optional) type of the identified entity.</li>
 * <li>{@link #getVersion() version} - the optional version (revision) of the entity.</li>
 * </ul>
 * Just like the {@link #getId() primary key} the {@link #getVersion() version} and {@link #getType() type} of an object
 * do not change. This allows to use the {@link Id} as globally unique identifier for its corresponding entity.<br>
 * An {@link Id} has a compact {@link #toString() string representation} that can be converted back to an {@link Id}.
 * Therefore, the implementation shall provide a {@link String}-arg constructor and a static {@code valueOf(String)}
 * method.
 *
 * @param <E> type of the identified entity.
 *
 * @see AbstractId
 * @since 1.0.0
 */
public interface Id<E> {

  /** The name of the {@link #getId() ID} property (e.g. for JSON or XML). */
  String PROPERTY_ID = "id";

  /** The name of the {@link #getVersion() version} property (e.g. for JSON or XML). */
  String PROPERTY_VERSION = "v";

  /**
   * The value used as {@link #getVersion() version} if it unspecified. If you are using an {@link Id} as link to an
   * {@link Entity} you will use this value to point to the recent version of the {@link net.sf.mmm.entity.Entity}.
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
   * @param type the new value of {@link #getType()}. Exact type should actually be {@link Class}{@literal <E>} but this
   *        prevents simple usage. As the {@link #getType() type} can not actually be changed with this method, this
   *        should be fine.
   * @return a copy of this {@link Id} with the given {@link #getType() type} or this {@link Id} itself if already
   *         satisfying.
   */
  Id<E> withType(Class<?> type);

  /**
   * @return a copy of this {@link Id} with a {@link #getVersion() version} of {@link #VERSION_LATEST} e.g. to use the
   *         {@link Id} as regular <em>foreign key</em> (pointing to the latest revision and not a historic revision) or
   *         this {@link Id} itself if already satisfying.
   */
  Id<E> withLatestVersion();

  /**
   * @return the {@code version} of this entity. Whenever the {@link net.sf.mmm.entity.Entity} gets updated (a
   *         modification is saved and the transaction is committed), this version is increased. Typically the version
   *         is a {@link Number} starting with {@code 0} for a new {@link net.sf.mmm.entity.Entity} that is increased
   *         whenever a modification is committed. However, it may also be an {@link java.time.Instant}. The version
   *         acts as a modification sequence for optimistic locking. On each update it will be verified that the version
   *         has not been increased already by another transaction. When linking an {@link net.sf.mmm.entity.Entity}
   *         ({@link Id} used as foreign key) the version can act as revision for auditing. If it is
   *         {@link #VERSION_LATEST} ({@code null}) it points to the latest revision of the
   *         {@link net.sf.mmm.entity.Entity}. Otherwise it points to a specific historic revision of the
   *         {@link net.sf.mmm.entity.Entity}. Depending on the database technology (e.g. when using hibernate envers)
   *         the version and the revision can be semantically different. In such case a
   *         {@link net.sf.mmm.entity.Entity#getId() primary key} can not be converted 1:1 as revisioned foreign key
   *         {@link Id}.
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

}
