/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.UUID;
import java.util.function.Supplier;

import io.github.mmm.entity.Entity;

/**
 * This is the interface for an ID that uniquely identifies a {@link Entity persistent entity} of a particular
 * {@link #getEntityClass() type} ({@code <E>}). <br>
 * An {@link Id} has the following properties:
 * <ul>
 * <li>{@link #get() primary-key} - the primary key that identifies the entity and is unique for a specific
 * {@link #getEntityClass() type}. As a best practice it is recommended to make the primary-key even unique for all
 * entities of a database.</li>
 * <li>{@link #getRevision() revision} - the optional revision of the entity.</li>
 * <li>{@link #getEntityClass() type} - is the type of the identified entity.</li>
 * </ul>
 * Just like the {@link #get() primary key} the {@link #getEntityClass() type} of an object does not change. In an
 * {@link Entity} is updated its {@link #getRevision() revision} may change. In this case a new {@link Id} instance is
 * created and assigned since {@link Id} is designed as an immutable datatype. To allow using {@link Id} as globally
 * unique identifier for its corresponding {@link Entity}, regular methods (e.g. {@code EntityRepository.findById} will
 * ignore the {@link #getRevision() revision}).<br>
 * Unlike regular JPA design this {@link Id} approach gives you a lot of simplicity without loosing any flexibility. So
 * your {@code EntityBean} and {@code EntityRepository} does not need any generic type for the primary key and
 * refactoring the type of the primary key or revision will have minimal impact on your entire code-base.<br>
 * An {@link Id} has a compact {@link #toString() string representation}. However, for structured representation and
 * marshaling use {@link GenericId} and convert to JSON, XML, or other structured format via
 * {@link GenericId#readObject(io.github.mmm.marshall.StructuredReader) readObject} and
 * {@link GenericId#write(io.github.mmm.marshall.StructuredWriter) write} methods.<br>
 * {@link Id} is designed for ultimate flexibility and also acts as factory. Therefore an {@link Entity} instance shall
 * never return {@code null} for {@link Entity#getId()}. It will hold a {@link #isEmpty() empty} ID that is created at
 * construction time. This way we can support custom {@link Id} types easily. The only requirement is that all instances
 * of {@link Id} are proper implementations of {@link AbstractId}. The class {@link AbstractId} contains additional
 * complexity and generic types for frameworks. Regular API users only need to use this {@link Id} interface that hides
 * some complexity.<br>
 * In case you need to create an {@link Id} from its primary key e.g. in test-code, you may want to use static factory
 * methods such as {@link LongId#of(Long)}, {@link UuidId#of(UUID)}, {@link StringId#of(String)}, etc.
 *
 * @param <E> the {@link #getEntityClass() entity type}.
 *
 * @see AbstractId
 * @since 1.0.0
 */
public interface Id<E> extends Supplier<Object> {

  /** Marshalling property name of the {@link #get() id}. */
  String PROPERTY_ID = "id";

  /** Marshalling property name of the {@link #getRevision() revision}. */
  String PROPERTY_REVISION = "rev";

  /** Column name of the {@link #getRevision() revision}. */
  String COLUMN_REVISION = "Rev";

  /**
   * The value used as {@link #getRevision() revision} if it unspecified. If you are using an {@link Id} as link to an
   * {@link Entity} you will use this value to point to the recent revision of the {@link io.github.mmm.entity.Entity}.
   */
  Comparable<?> REVISION_LATEST = null;

  /** The separator for the {@link #getRevision() revision}. */
  char REVISION_SEPARATOR = '@';

  /**
   * @see LongVersionId
   * @see UuidVersionId
   * @see StringVersionId
   *
   * @return the <em>primary key</em> of the identified {@link Entity} as {@link Object} value. It may only be unique
   *         for a particular {@link #getEntityClass() type} of an <em>entity</em> (unless {@link UUID} is used).
   */
  @Override
  Object get();

  /**
   * @return the {@link #get() primary key} as {@link String} for marshalling.
   */
  default String getAsString() {

    Object id = get();
    if (id == null) {
      return null;
    }
    return id.toString();
  }

  /**
   * @return the {@link Class} reflecting the {@link #get() primary key}.
   */
  public abstract Class<?> getType();

  /**
   * @return {@code true} if both {@link #get() primary key} and {@link #getRevision() revision} are empty (such
   *         {@link Id} object is used as placeholder and factory), {@code false} otherwise (e.g. if persistent).
   */
  default boolean isEmpty() {

    return (get() == null) && (getRevision() == null);
  }

  /**
   * @return the {@link Class} reflecting the <em>type</em> of the referenced <em>{@link io.github.mmm.entity.Entity
   *         entity}</em>. May be {@code null} if not available.
   */
  Class<E> getEntityClass();

  /**
   * @return a copy of this {@link Id} without a {@link #getRevision() revision} ({@code null}) e.g. to use the
   *         {@link Id} as regular <em>foreign key</em> (pointing to the latest revision and not a historic revision) or
   *         this {@link Id} itself if already satisfying. <b>ATTENTION</b>: This method may return a different class
   *         than it was invoked on. To prevent this use {@link GenericId#withRevision(Comparable) withRevision}(null).
   */
  Id<E> withoutRevision();

  /**
   * @return the {@code revision} of this entity. Whenever the {@link io.github.mmm.entity.Entity} gets updated (a
   *         modification is saved and the transaction is committed), this revision is increased. Typically the revision
   *         is a {@link Number} starting with {@link AbstractVersionId#INSERT_REVISION 0} for a new
   *         {@link io.github.mmm.entity.Entity} that is increased whenever a modification is committed. However, it may
   *         also be an {@link java.time.Instant}. The revision acts as a modification sequence for optimistic locking.
   *         On each update it will be verified that the revision has not been increased already by another transaction.
   *         When linking an {@link io.github.mmm.entity.Entity} ({@link Id} used as foreign key) the revision can act
   *         as version identifier for auditing. If it is {@code null} it points to the latest revision of the
   *         {@link io.github.mmm.entity.Entity}. Otherwise it points to a specific historic revision of the
   *         {@link io.github.mmm.entity.Entity}.
   */
  Comparable<?> getRevision();

  /**
   * @return the {@link Class} reflecting the {@link #getRevision() revision}.
   */
  public abstract Class<? extends Comparable<?>> getRevisionType();

  /**
   * @return the {@link #getRevision() revision} as {@link String} for marshalling.
   */
  default String getRevisionAsString() {

    Object revision = getRevision();
    if (revision == null) {
      return null;
    }
    return revision.toString();
  }

  /**
   * <b>ATTENTION</b>: This method is designed to ensure and verify the expected {@link #getEntityClass() type}. It will
   * fail if a different type is already assigned. It shall be used to cast from {@code Id<?>} to an {@link Id} with a
   * properly typed generic as illustrated by the following example:
   *
   * <pre>
   * {@link Id}{@code <?>} id = entity.getId();
   * {@link Class}{@code <E>} entityType = getEntityType();
   * {@link Id}{@code <E>} typedId = id.withEntityType(entityType);
   * </pre>
   *
   * @param <T> type
   * @param newEntityType the new value of {@link #getEntityClass()}. Exact type should actually be
   *        {@link Class}{@literal <E>} but this prevents simple generic usage. As the {@link #getEntityClass() type}
   *        can not actually be changed with this method, this should be fine.
   * @return a copy of this {@link Id} with the given {@link #getEntityClass() type} or this {@link Id} itself if
   *         already satisfying.
   * @throws IllegalArgumentException if this {@link Id} already has a different {@link #getEntityClass() type}
   *         assigned.
   */
  <T> Id<T> withEntityType(Class<T> newEntityType);

  /**
   * @return the {@link String} representation of this {@link Id}. Will consist of {@link #get() object-id},
   *         {@link #getEntityClass() type} and {@link #getRevision() revision} separated with a specific separator.
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

  /**
   * @return {@code true} if this {@link Id} is transient if used as {@link Entity#getId() primary key}, {@code false}
   *         otherwise. Here transient means that the {@link Entity#getId() owning} {@link Entity} has never been saved
   *         to a persistent store yet. Otherwise the {@link Entity} is persistent and was received from a persistent
   *         store. Please note that the existence of its {@link #get() primary key} is not sufficient as it may be
   *         assigned already in transient state (e.g. for {@link UuidId} or also for {@link LongId} using negative TX
   *         local values).
   */
  default boolean isTransient() {

    return (getRevision() == null);
  }

}
