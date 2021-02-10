/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AbstractInstantId} using {@link UUID} as {@link #get() primary key}. Can be used in two
 * scenarios:
 * <ul>
 * <li>Your data store uses {@link UUID}s natively as <em>primary key</em> (e.g. apache cassandra supports this). In
 * such case you will always directly use a {@link UUID} as the actual <em>primary key</em>.</li>
 * <li>You may need to express a link to a transient entity. Then you can temporary assign a {@link UUID} to the entity
 * on the client and link it via such ID. On the server-side the actual {@link UuidLatestId} will then be replaced with
 * the actual {@link #get() ID} while persisting the data.</li>
 * </ul>
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class UuidLatestId<E> extends AbstractLatestId<E, UUID> implements UuidId<E> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   */
  public UuidLatestId(Class<E> type, UUID id) {

    super(type);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public UUID get() {

    return this.id;
  }

  @Override
  protected String getMarshalPropertyId() {

    return PROPERTY_UUID;
  }

  @Override
  public Factory getFactory() {

    return FACTORY;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   * @return the new {@link UuidLatestId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidLatestId<E> of(Class<E> type, UUID id) {

    if (id == null) {
      return null;
    }
    return new UuidLatestId<>(type, id);
  }

  /**
   * {@link IdFactory} implementation.
   */
  public static class Factory extends UuidIdFactory<LatestVersion> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<? extends Id<?>> getIdClass() {

      return (Class) UuidLatestId.class;
    }

    @Override
    public boolean hasVersion() {

      return false;
    }

    @Override
    public <E> UuidLatestId<E> parse(Class<E> type, String id, String version) {

      assert (version == null);
      return create(type, UUID.fromString(id), null);
    }

    @Override
    public <E> UuidLatestId<E> create(Class<E> type, UUID id, LatestVersion version) {

      assert (version == null);
      return of(type, id);
    }
  }

}
