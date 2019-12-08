/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AbstractInstantId} using {@link UUID} as {@link #getId() primary key}. Can be used in two
 * scenarios:
 * <ul>
 * <li>Your data store uses {@link UUID}s natively as <em>primary key</em> (e.g. apache cassandra supports this). In
 * such case you will always directly use a {@link UUID} as the actual <em>primary key</em>.</li>
 * <li>You may need to express a link to a transient entity. Then you can temporary assign a {@link UUID} to the entity
 * on the client and link it via such ID. On the server-side the actual {@link UuidInstantId} will then be replaced with
 * the actual {@link #getId() ID} while persisting the data.</li>
 * </ul>
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class UuidInstantId<E> extends AbstractInstantId<E, UUID> {

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
  public UuidInstantId(Class<E> type, UUID id, Instant version) {

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
   * @return the new {@link UuidInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidInstantId<E> of(Class<E> type, UUID id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link UuidInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidInstantId<E> of(Class<E> type, UUID id, Instant version) {

    if (id == null) {
      return null;
    }
    return new UuidInstantId<>(type, id, version);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory implements IdFactory<UUID, Instant, UuidInstantId<?>> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<UuidInstantId<?>> getIdClass() {

      return (Class) UuidInstantId.class;
    }

    @Override
    public <E> UuidInstantId<E> unmarshall(Class<E> type, String id, String version) {

      return create(type, UUID.fromString(id), Instant.parse(version));
    }

    @Override
    public <E> UuidInstantId<E> create(Class<E> type, UUID id, Instant version) {

      return of(type, id, version);
    }
  }

}
