/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Empty implementation of {@link PkId} using as placeholder.
 *
 * @param <E> type of the identified entity.
 * @param <SELF> type of this class itself for fluent API calls.
 * @since 1.0.0
 */
public final class PkIdEmpty<E, SELF extends PkId<E, Object, SELF>> extends PkId<E, Object, SELF> {

  /** The empty instance. */
  @SuppressWarnings("rawtypes")
  private static final PkIdEmpty EMPTY = new PkIdEmpty<>(null);

  /**
   * The constructor.
   *
   * @param type the {@link #getEntityClass() type}.
   */
  public PkIdEmpty(Class<E> type) {

    super(type);
  }

  @Override
  public Object getPk() {

    return null;
  }

  @Override
  public Class<Object> getPkClass() {

    return Object.class;
  }

  @Override
  public boolean isTransient() {

    return true;
  }

  @Override
  public Object parsePk(String idString) {

    return IdFactory.get().parsePk(idString);
  }

  @Override
  public Comparable<?> parseRevision(String revisionString) {

    return IdFactory.get().parseRevision(revisionString);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected SELF newId(Class<E> newEntityClass, Object newPk) {

    if (newPk == null) {
      return (SELF) new PkIdEmpty<>(newEntityClass);
    }
    return (SELF) IdFactory.get().create(newEntityClass, newPk, null);
  }

  @Override
  public String getMarshalPropertyId() {

    return null;
  }

  /**
   * @param <E> type of the identified entity.
   * @return the {@link #isEmpty() empty} instance of this class.
   */
  public static <E> PkIdEmpty<E, ?> getEmpty() {

    return EMPTY;
  }

}
