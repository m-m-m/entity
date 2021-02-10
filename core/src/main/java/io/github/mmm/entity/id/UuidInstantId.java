/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AbstractInstantId} using {@link UUID} as {@link #get() primary key}. Can be used in two
 * scenarios:
 * <ul>
 * <li>Your data store uses {@link UUID}s natively as <em>primary key</em> (e.g. apache cassandra supports this). In
 * such case you will always directly use a {@link UUID} as the actual <em>primary key</em>.</li>
 * <li>You may need to express a link to a transient entity. Then you can temporary assign a {@link UUID} to the entity
 * on the client and link it via such ID. On the server-side the actual {@link UuidInstantId} will then be replaced with
 * the actual {@link #get() ID} while persisting the data.</li>
 * </ul>
 *
 * @param <E> the generic type of the identified entity.
 *
 * @since 1.0.0
 */
public class UuidInstantId<E> extends AbstractInstantId<E, UUID> implements UuidId<E> {

  /** @see #getFactory() */
  public static final Factory FACTORY = new Factory();

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #get() primary key}.
   * @param version the {@link #getVersion() version}.
   */
  public UuidInstantId(Class<E> type, UUID id, Instant version) {

    super(type, version);
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
   * {@link IdFactory} implementation.
   */
  public static class Factory extends UuidIdFactory<Instant> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<? extends Id<?>> getIdClass() {

      return (Class) UuidInstantId.class;
    }

    @Override
    public <E> UuidId<E> parse(Class<E> type, String id, String version) {

      Instant instant = null;
      if (version != null) {
        instant = Instant.parse(version);
      }
      return create(type, UUID.fromString(id), instant);
    }
  }

}
