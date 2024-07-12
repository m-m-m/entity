/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import io.github.mmm.entity.Entity;

/**
 * An abstract base implementation of {@link Id} that has no {@link #getRevision() revision} and will neither support
 * optimistic locking nor reading audit trails.
 *
 * @param <E> type of the identified entity.
 * @param <P> type of the {@link #getPk() primary key}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
public abstract class PkId<E, P, SELF extends PkId<E, P, SELF>> extends AbstractId<E, P, Comparable<?>, SELF> {

  /** @see #getEntityClass() */
  protected final Class<E> entityClass;

  /**
   * The constructor.
   *
   * @param entityClass - see {@link #getEntityClass()}.
   */
  protected PkId(Class<E> entityClass) {

    super();
    this.entityClass = entityClass;
  }

  @Override
  public final Class<E> getEntityClass() {

    return this.entityClass;
  }

  @Override
  public Comparable<?> getRevision() {

    return null;
  }

  @Override
  public boolean hasRevisionField() {

    return false;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Class<Comparable<?>> getRevisionType() {

    return (Class) Comparable.class;
  }

  @Override
  public Comparable<?> parseRevision(String revisionString) {

    return null;
  }

  @Override
  public Comparable<?> updateRevision(Comparable<?> currentRevision) {

    return null;
  }

  @Override
  public String getMarshalPropertyRevision() {

    return null;
  }

  /**
   * @param newEntityClass the {@link #getEntityClass() entity class}.
   * @param newPk the {@link #getPk() primary key}.
   * @return a new instance of this {@link PkId} implementation class with the given parameters.
   */
  protected abstract SELF newId(Class<E> newEntityClass, P newPk);

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T> GenericId<T, P, Comparable<?>, ?> create(Class<T> entityType, P pk, Comparable<?> revision) {

    PkId id = newId((Class) entityType, pk);
    return id.withRevisionGeneric(revision);
  }

  @Override
  public SELF withoutRevision() {

    return self();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T> PkId<T, P, ?> withEntityTypeGeneric(Class<T> newEntityClass) {

    return withEntityType((Class) newEntityClass);
  }

  @Override
  public SELF withEntityType(Class<E> newEntityClass) {

    if (this.entityClass == newEntityClass) {
      return self();
    } else if (this.entityClass == null) {
      return newId(newEntityClass, getPk());
    } else {
      throw new IllegalArgumentException("Illegal type " + newEntityClass.getName() + " - already typed to "
          + this.entityClass.getName() + " at " + toString());
    }
  }

  @Override
  public SELF withPk(P newPk) {

    if (Objects.equals(getPk(), newPk)) {
      return self();
    }
    return newId(getEntityClass(), newPk);
  }

  @Override
  public SELF withPkAndRevision(P newPk, Comparable<?> newRevision) {

    if (newRevision != null) {
      throw new IllegalArgumentException(
          "Cannot change revision type to " + newRevision.getClass().getName() + " - use withRevisionGeneric instead!");
    }
    if (Objects.equals(getPk(), newPk)) {
      return self();
    }
    return newId(getEntityClass(), newPk);
  }

  /**
   * @deprecated use {@link #withRevisionGeneric(Comparable)} instead. As the return type can change this method would
   *             break the generic contract.
   */
  @Deprecated
  @Override
  public SELF withRevision(Comparable<?> newRevision) {

    if (newRevision != null) {
      throw new IllegalArgumentException(
          "You must use withRevisionGeneric instead to avoid violating generic contract.");
    }
    return self();
  }

  /**
   * @param <R> type of the new {@link #getRevision() revision}.
   * @param newRevision the new {@link #getRevision() revision}.
   * @return a {@link GenericId} that has the given {@link #getRevision() revision} but other attributes from this
   *         {@link PkId}. Therefore the returned object may be of a different class and not be an instance of
   *         {@link PkId}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <R extends Comparable<?>> GenericId<E, P, R, ?> withRevisionGeneric(R newRevision) {

    GenericId result;
    if (newRevision == null) {
      result = this;
    } else if (newRevision instanceof Long rev) {
      result = new RevisionedIdVersion<>(this, rev);
    } else if (newRevision instanceof Instant rev) {
      result = new RevisionedIdInstant<>(this, rev);
    } else if (newRevision instanceof Integer rev) {
      result = new RevisionedIdVersion<>(this, Long.valueOf(rev.longValue()));
    } else {
      throw new IllegalArgumentException("Unsupported revision type " + newRevision.getClass().getName() + "!");
    }
    return result;
  }

  /**
   * This is a generic convenience method to create a {@link Id#withoutRevision() revision-less} {@link Id} back from
   * its {@link #getPk() primary key}.
   *
   * @param <E> type of {@link Entity}.
   * @param type the {@link #getEntityClass() entityClass}
   * @param pk the {@link #getPk() primary key}.
   * @return the {@link Id#withoutRevision() revision-less} {@link Id} for the given arguments.
   */
  public static <E> PkId<E, ?, ?> of(Class<E> type, Object pk) {

    if (pk == null) {
      return null;
    }
    if (pk instanceof Long l) {
      return new PkIdLong<>(type, l);
    } else if (pk instanceof UUID u) {
      return new PkIdUuid<>(type, u);
    } else if (pk instanceof String s) {
      return new PkIdString<>(type, s);
    } else if (pk instanceof Integer i) {
      return new PkIdLong<>(type, Long.valueOf(i.longValue()));
    } else {
      throw new IllegalArgumentException("Unsupported primary key type " + pk.getClass().getName());
    }
  }

}
