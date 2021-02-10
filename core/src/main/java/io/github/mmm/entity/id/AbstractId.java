/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

/**
 * This is the abstract base implementation of {@link Id}.
 *
 * @param <E> type of the identified entity.
 * @param <I> type of the {@link #get() ID}.
 * @param <V> type of the {@link #getVersion() Version}.
 *
 * @since 1.0.0
 */
public abstract class AbstractId<E, I, V extends Comparable<?>> implements Id<E> {

  /**
   * Name of the {@link #get() ID} property (e.g. for JSON or XML) in case of a {@link Long}.
   *
   * @see LongInstantId
   * @see LongVersionId
   */
  public static final String PROPERTY_LONG_ID = "l";

  /**
   * Name of the {@link #get() ID} property (e.g. for JSON or XML) in case of a {@link String}.
   *
   * @see StringInstantId
   * @see StringVersionId
   */
  public static final String PROPERTY_STRING_ID = "s";

  /**
   * Name of the {@link #get() ID} property (e.g. for JSON or XML) in case of a {@link java.util.UUID}.
   *
   * @see UuidInstantId
   * @see UuidVersionId
   */
  public static final String PROPERTY_UUID = "u";

  /**
   * Name of the {@link #getVersion() version} property (e.g. for JSON or XML) in case of a {@link Long}.
   *
   * @see LongVersionId
   * @see UuidVersionId
   * @see StringVersionId
   */
  public static final String PROPERTY_LONG_VERSION = "v";

  /**
   * Name of the {@link #getVersion() version} property (e.g. for JSON or XML) in case of an {@link java.time.Instant}
   * ("t" for "timestamp").
   *
   * @see LongInstantId
   * @see UuidInstantId
   * @see StringInstantId
   */
  public static final String PROPERTY_INSTANT_VERSION = "t";

  private final Class<E> type;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   */
  public AbstractId(Class<E> type) {

    super();
    this.type = type;
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public abstract I get();

  @Override
  public abstract V getVersion();

  /**
   * @return the property name of the {@link #get() id} for marshalling.
   * @see #PROPERTY_LONG_ID
   * @see #PROPERTY_UUID
   * @see #PROPERTY_STRING_ID
   */
  protected abstract String getMarshalPropertyId();

  /**
   * @return the property name of the {@link #getVersion() version} for marshalling.
   * @see #PROPERTY_LONG_VERSION
   * @see #PROPERTY_INSTANT_VERSION
   */
  protected abstract String getMarshalPropertyVersion();

  @Override
  public Id<E> withLatestVersion() {

    return withVersion(null);
  }

  /**
   * @param newVersion the new value of {@link #getVersion()}.
   * @return a copy of this {@link Id} with the given {@link #getVersion() version} or this {@link Id} itself if already
   *         satisfying.
   */
  public Id<E> withVersion(V newVersion) {

    if (getVersion() == newVersion) {
      return this;
    }
    return getFactory().create(this.type, get(), newVersion);
  }

  @SuppressWarnings("unchecked")
  @Override
  public final Id<E> withType(Class<?> newType) {

    if (this.type == null) {
      return getFactory().create((Class<E>) newType, get(), getVersion());
    } else if (this.type != newType) {
      throw new IllegalArgumentException(
          "Illegal type " + newType + " - already typed to " + this.type + " at " + toString());
    }
    return this;
  }

  /**
   * @return the {@link IdFactory} responsible for this {@link Id} implementation.
   */
  public abstract IdFactory<I, V> getFactory();

  @Override
  public final int hashCode() {

    return ~get().hashCode();
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj == null) || !(obj instanceof AbstractId)) {
      return false;
    }
    AbstractId<?, ?, ?> other = (AbstractId<?, ?, ?>) obj;
    if (!Objects.equals(get(), other.get())) {
      return false;
    }
    if ((this.type != null) && (other.type != null) && !this.type.equals(other.type)) {
      return false;
    }
    if (!Objects.equals(getVersion(), other.getVersion())) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(32);
    toString(buffer);
    return buffer.toString();
  }

  /**
   * @see #toString()
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(CharSequence) append} the string
   *        representation to.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(get());
    V version = getVersion();
    if (version != null) {
      buffer.append(VERSION_SEPARATOR);
      buffer.append(version);
    }
  }

  /**
   * @param <E> the type of the identified entity.
   * @param id the existing {@link Id} or {@code null}.
   * @param type the {@link #getType() type} to {@link #withType(Class) ensure}.
   * @return the result of <code>id.{@link Id#withType(Class) withType}(type)</code> or {@code null} if the given
   *         {@link Id} is {@code null}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <E> Id<E> getWithType(Id<?> id, Class<E> type) {

    if (id == null) {
      return null;
    }
    return (Id) id.withType(type);
  }

  /**
   * @param <I> the generic type expected for {@link Id#get()}.
   * @param id the actual {@link Id} instance.
   * @param type the {@link Class} reflecting the expected type of {@link Id#get()}.
   * @return the {@link #get() primary key} of the given {@link Id}. May be {@code null} if the given {@link Id} is
   *         {@code null}.
   * @throws IllegalArgumentException if the given {@code type} does not match.
   */
  public static <I> I getIdAs(Id<?> id, Class<I> type) throws IllegalArgumentException {

    return getAs(id, type, PROPERTY_ID);
  }

  /**
   * @param <V> the generic type expected for {@link Id#getVersion()}.
   * @param id the actual {@link Id} instance.
   * @param type the {@link Class} reflecting the expected type of {@link Id#getVersion()}.
   * @return the {@link #getVersion() version} of the given {@link Id}. May be {@code null} if the given {@link Id}
   *         itself or its {@link #getVersion() version} is {@code null}.
   * @throws IllegalArgumentException if the given {@code type} does not match.
   */
  public static <V> V getVersionAs(Id<?> id, Class<V> type) throws IllegalArgumentException {

    return getAs(id, type, PROPERTY_VERSION);
  }

  /**
   * @param id the actual {@link Id} instance.
   * @return the {@link #getVersion() version} of the given {@link Id}. May be {@code null} if the given {@link Id}
   *         itself or its {@link #getVersion() version} is {@code null}.
   * @throws IllegalArgumentException if the given {@code type} does not match.
   */
  public static long getVersionAsLong(Id<?> id) throws IllegalArgumentException {

    Long version = getVersionAs(id, Long.class);
    if (version == null) {
      return 0;
    }
    return version.longValue();
  }

  @SuppressWarnings("unchecked")
  private static <V> V getAs(Id<?> id, Class<V> type, String property) throws IllegalArgumentException {

    if (id == null) {
      return null;
    }
    Object value;
    if (PROPERTY_ID.equals(property)) {
      value = id.get();
    } else if (PROPERTY_VERSION.equals(property)) {
      value = id.getVersion();
    } else {
      throw new IllegalStateException(property);
    }
    if (value == null) {
      return null;
    } else if (type.isInstance(value)) {
      return (V) value;
    } else {
      throw new IllegalArgumentException(
          "Expected type " + type.getSimpleName() + " for property " + property + " but found " + value + " of type "
              + value.getClass().getSimpleName() + " from ID " + id + " of type " + id.getClass().getSimpleName());
    }
  }

}
