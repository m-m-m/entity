package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

import io.github.mmm.base.temporal.InstantParser;
import io.github.mmm.base.uuid.UuidParser;

/**
 * Generic implementation of {@link IdFactory}.
 *
 * @since 1.0.0
 */
final class GenericIdFactory implements IdFactory<Object, Comparable<?>> {

  static final GenericIdFactory INSTANCE = new GenericIdFactory();

  private GenericIdFactory() {

  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <E> GenericId<E, Object, Comparable<?>> create(Class<E> entityType, Object id, Comparable<?> version) {

    GenericId result;
    if (id == null) {
      assert (version == null);
      return null;
    } else if (id instanceof Long) {
      result = LongId.of((Long) id, entityType, version);
    } else if (id instanceof UUID) {
      result = UuidId.of((UUID) id, entityType, version);
    } else if (id instanceof String) {
      result = StringId.of((String) id, entityType, version);
    } else {
      throw new IllegalStateException("Unsupported ID type: " + id.getClass().getName());
    }
    return result;
  }

  @Override
  public Object parseId(String idString) {

    UUID uuid = UuidParser.get().parse(idString);
    if (uuid != null) {
      return uuid;
    }
    return idString;
  }

  @Override
  public Comparable<?> parseVersion(String versionString) {

    Instant instant = InstantParser.get().parse(versionString);
    if (instant != null) {
      return instant;
    }
    return Long.valueOf(versionString);
  }

}
