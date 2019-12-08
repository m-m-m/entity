/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AbstractVersionId} using {@link UUID} as {@link #getId() primary key}. Can be used in two
 * scenarios:
 * <ul>
 * <li>Your data store uses {@link UUID}s natively as <em>primary key</em> (e.g. apache cassandra supports this). In
 * such case you will always directly use a {@link UUID} as the actual <em>primary key</em>.</li>
 * <li>You may need to express a link to a transient entity. Then you can temporary assign a {@link UUID} to the entity
 * on the client and link it via such ID. On the server-side the actual {@link UuidVersionId} will then be replaced with
 * the actual {@link #getId() ID} while persisting the data.</li>
 * </ul>
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class UuidVersionId<E> extends AbstractVersionId<E, UUID> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   */
  public UuidVersionId(Class<E> type, UUID id, Long version) {

    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public UUID getId() {

    return this.id;
  }

  @Override
  public Factory getFactory() {

    return FACTORY;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link UuidVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidVersionId<E> of(Class<E> type, UUID id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link UuidVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidVersionId<E> of(Class<E> type, UUID id, Long version) {

    if (id == null) {
      return null;
    }
    return new UuidVersionId<>(type, id, version);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory implements IdFactory<UUID, Long, UuidVersionId<?>> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<UuidVersionId<?>> getIdClass() {

      return (Class) UuidVersionId.class;
    }

    @Override
    public <E> UuidVersionId<E> unmarshall(Class<E> type, String id, String version) {

      return create(type, UUID.fromString(id), Long.valueOf(version));
    }

    @Override
    public <E> UuidVersionId<E> create(Class<E> type, UUID id, Long version) {

      return of(type, id, version);
    }
  }

}
