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
  public <E> GenericId<E, Object, Comparable<?>> create(Class<E> entityType, Object id, Comparable<?> revision) {

    GenericId result;
    if (id == null) {
      assert (revision == null);
      return null;
    } else if (id instanceof Long) {
      result = LongId.of((Long) id, entityType, revision);
    } else if (id instanceof UUID) {
      result = UuidId.of((UUID) id, entityType, revision);
    } else if (id instanceof String) {
      result = StringId.of((String) id, entityType, revision);
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
  public Comparable<?> parseRevision(String revisionString) {

    Instant instant = InstantParser.get().parse(revisionString);
    if (instant != null) {
      return instant;
    }
    return Long.valueOf(revisionString);
  }

}
