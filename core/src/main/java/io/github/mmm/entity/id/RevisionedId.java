/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/// Implementation of [AbstractId] that has a [typed][#getRevisionType()] [revision][#getRevision()]. It combines a
/// [PkId] with the actual [revision][#getRevision()] for better flexibility.
///
/// @param <E> type of the identified entity.
/// @param <P> type of the [primary key][#getPk()].
/// @param <R> type of the [revision][#getRevision()].
/// @param <SELF> type of this class itself for fluent API calls.
/// @since 1.0.0
public abstract class RevisionedId<E, P, R extends Comparable<?>, SELF extends RevisionedId<E, P, R, SELF>>
    extends AbstractId<E, P, R, SELF> {

  /// @see #withoutRevision()
  protected final PkId<E, P, ?> id;

  /// The constructor.
  ///
  /// @param id the wrapped [PkId] containing [entity class][#getEntityClass()] and [primary key][#getPk()].
  protected RevisionedId(PkId<E, P, ?> id) {

    super();
    this.id = id;
  }

  @Override
  public final boolean hasRevisionField() {

    return true;
  }

  @Override
  public final P getPk() {

    return this.id.getPk();
  }

  @Override
  public final Class<P> getPkClass() {

    return this.id.getPkClass();
  }

  @Override
  public P parsePk(String idString) {

    return this.id.parsePk(idString);
  }

  @Override
  public final Class<E> getEntityClass() {

    return this.id.getEntityClass();
  }

  @Override
  public final String getMarshalPropertyId() {

    return this.id.getMarshalPropertyId();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T> GenericId<T, P, R, ?> create(Class<T> entityType, P pk, R revision) {

    PkId newId = this.id.withEntityTypeGeneric(entityType).withPk(pk);
    return newId(newId, revision);
  }

  /// Internal factory method.
  ///
  /// @param newId the new internal [PkId].
  /// @param newRevision the new [revision][#getRevision()].
  /// @return a new instance of this type with the given arguments.
  protected abstract SELF newId(PkId<E, P, ?> newId, R newRevision);

  @Override
  public SELF withPk(P newPk) {

    PkId<E, P, ?> newId = this.id.withPk(newPk);
    if (newId == this.id) {
      return self();
    }
    return newId(newId, getRevision());
  }

  @Override
  public SELF withPkAndRevision(P newPk, R newRevision) {

    PkId<E, P, ?> newId = this.id.withPk(newPk);
    if (newId == this.id && Objects.equals(getRevision(), newRevision)) {
      return self();
    }
    return newId(newId, newRevision);
  }

  @Override
  public SELF withRevision(R newRevision) {

    if (Objects.equals(getRevision(), newRevision)) {
      return self();
    }
    return newId(this.id, newRevision);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T> RevisionedId<T, P, R, ?> withEntityTypeGeneric(Class<T> newEntityType) {

    return withEntityType((Class) newEntityType);
  }

  @Override
  public SELF withEntityType(Class<E> newEntityType) {

    PkId<E, P, ?> newId = this.id.withEntityType(newEntityType);
    if (newId == this.id) {
      return self();
    }
    return newId(newId, getRevision());
  }

  @Override
  public SELF updateRevision() {

    R newRevision = updateRevision(getRevision());
    return withRevision(newRevision);
  }

  @Override
  public final PkId<E, P, ?> withoutRevision() {

    return this.id;
  }

}
