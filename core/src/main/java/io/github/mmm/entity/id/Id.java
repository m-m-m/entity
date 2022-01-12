/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.UUID;
import java.util.function.Supplier;

import io.github.mmm.entity.Entity;

/**
 * This is the interface for an ID that uniquely identifies a {@link Entity persistent entity} of a particular
 * {@link #getEntityType() type} ({@code <E>}). <br>
 * An {@link Id} has the following properties:
 * <ul>
 * <li>{@link #get() object-id} - the primary key that identifies the entity and is unique for a specific
 * {@link #getEntityType() type}. As a best practice it is recommended to make the object-id even unique for all
 * entities of a database.</li>
 * <li>{@link #getVersion() version} - the optional version (revision) of the entity.</li>
 * <li>{@link #getEntityType() type} - is the type of the identified entity.</li>
 * </ul>
 * Just like the {@link #get() primary key} the {@link #getVersion() version} and {@link #getEntityType() type} of an
 * object do not change. This allows to use the {@link Id} as globally unique identifier for its corresponding
 * entity.<br>
 * An {@link Id} has a compact {@link #toString() string representation}. However, for structured representation and
 * marshaling use {@link GenericId} and convert to JSON, XML, or other structured format via
 * {@link GenericId#readObject(io.github.mmm.marshall.StructuredReader) readObject} and
 * {@link GenericId#write(io.github.mmm.marshall.StructuredWriter) write} methods.<br>
 * {@link Id} is designed for ultimate flexibility and therefore it also acts as factory. Therefore an {@link Entity}
 * instance shall never return {@code null} for {@link Entity#getId()}. It will hold a {@link #isEmpty() empty} ID that
 * is created at construction time. This way we can support custom {@link Id} types easily. The only requirement is that
 * all instances of {@link Id} are proper implementations of {@link AbstractId}. The class {@link AbstractId} contains
 * additional complexity and generic types for frameworks. Regular API users only need to use this {@link Id} interface
 * that hides some complexity.
 *
 * @param <E> type of the identified entity.
 *
 * @see AbstractId
 * @since 1.0.0
 */
public interface Id<E> extends Supplier<Object> {

  /** Name of the {@link #get() id} property. */
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
   * @return the <em>primary key</em> of the identified {@link Entity} as {@link Object} value. It may only be unique
   *         for a particular {@link #getEntityType() type} of an <em>entity</em> (unless {@link UUID} is used).
   */
  @Override
  Object get();

  /**
   * @return the {@link #get() primary key} as {@link String} for marshalling.
   */
  default String getIdAsString() {

    Object id = get();
    if (id == null) {
      return null;
    }
    return id.toString();
  }

  /**
   * @return the {@link Class} reflecting the {@link #get() primary key}.
   */
  public abstract Class<?> getIdType();

  /**
   * @return {@code true} if both {@link #get() primary key} and {@link #getVersion() version} are empty (such
   *         {@link Id} object is used as placeholder and factory), {@code false} otherwise (e.g. if persistent).
   */
  default boolean isEmpty() {

    return (get() == null) && (getVersion() == null);
  }

  /**
   * @return the {@link Class} reflecting the <em>type</em> of the referenced <em>entity</em>. May be {@code null} if
   *         not available.
   */
  Class<E> getEntityType();

  /**
   * @return a copy of this {@link Id} without a {@link #getVersion() version} ({@code null}) e.g. to use the {@link Id}
   *         as regular <em>foreign key</em> (pointing to the latest revision and not a historic revision) or this
   *         {@link Id} itself if already satisfying.
   */
  Id<E> withoutVersion();

  /**
   * @return the {@code version} of this entity. Whenever the {@link io.github.mmm.entity.Entity} gets updated (a
   *         modification is saved and the transaction is committed), this version is increased. Typically the version
   *         is a {@link Number} starting with {@code 1} for a new {@link io.github.mmm.entity.Entity} that is increased
   *         whenever a modification is committed. However, it may also be an {@link java.time.Instant}. The version
   *         acts as a modification sequence for optimistic locking. On each update it will be verified that the version
   *         has not been increased already by another transaction. When linking an {@link io.github.mmm.entity.Entity}
   *         ({@link Id} used as foreign key) the version can act as revision for auditing. If it is {@code null} it
   *         points to the latest version of the {@link io.github.mmm.entity.Entity}. Otherwise it points to a specific
   *         historic revision of the {@link io.github.mmm.entity.Entity}.
   */
  Comparable<?> getVersion();

  /**
   * @return the {@link Class} reflecting the {@link #getVersion() version}.
   */
  public abstract Class<? extends Comparable<?>> getVersionType();

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
   * @return the {@link String} representation of this {@link Id}. Will consist of {@link #get() object-id},
   *         {@link #getEntityType() type} and {@link #getVersion() revision} separated with a specific separator.
   *         Segments that are {@code null} will typically be omitted in the {@link String} representation.
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
  static <E extends Entity> Id<E> from(E entity) {

    if (entity == null) {
      return null;
    }
    return (Id<E>) entity.getId();
  }

}
