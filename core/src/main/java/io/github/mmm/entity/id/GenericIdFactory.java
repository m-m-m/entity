package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.github.mmm.base.temporal.InstantParser;
import io.github.mmm.base.uuid.UuidParser;

/**
 * Generic implementation of {@link IdFactory}.
 *
 * @since 1.0.0
 */
final class GenericIdFactory implements IdFactory<Object, Comparable<?>> {

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends Id>, GenericId> EMPTY_ID_MAP = new HashMap<>();

  static {
    EMPTY_ID_MAP.put(null, LongVersionId.getEmpty());
    EMPTY_ID_MAP.put(Id.class, LongVersionId.getEmpty());
    EMPTY_ID_MAP.put(GenericId.class, LongVersionId.getEmpty());
    EMPTY_ID_MAP.put(AbstractId.class, LongVersionId.getEmpty());
    EMPTY_ID_MAP.put(LongId.class, LongVersionId.getEmpty());
    EMPTY_ID_MAP.put(AbstractVersionId.class, LongVersionId.getEmpty());
    EMPTY_ID_MAP.put(LongVersionId.class, LongVersionId.getEmpty());
    EMPTY_ID_MAP.put(AbstractInstantId.class, LongInstantId.getEmpty());
    EMPTY_ID_MAP.put(LongInstantId.class, LongInstantId.getEmpty());
    EMPTY_ID_MAP.put(StringId.class, StringVersionId.getEmpty());
    EMPTY_ID_MAP.put(StringVersionId.class, StringVersionId.getEmpty());
    EMPTY_ID_MAP.put(StringInstantId.class, StringInstantId.getEmpty());
    EMPTY_ID_MAP.put(UuidId.class, UuidVersionId.getEmpty());
    EMPTY_ID_MAP.put(UuidVersionId.class, UuidVersionId.getEmpty());
    EMPTY_ID_MAP.put(UuidInstantId.class, UuidInstantId.getEmpty());
  }

  static final GenericIdFactory INSTANCE = new GenericIdFactory();

  private GenericIdFactory() {

  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <E> GenericId<E, Object, Comparable<?>> create(Class<E> entityType, Object id, Comparable<?> revision) {

    GenericId result;
    if (revision instanceof Integer i) {
      revision = Long.valueOf(i.longValue());
    }
    if (id == null) {
      assert (revision == null);
      return null;
    } else if (id instanceof Long l) {
      result = LongId.of(l, entityType, revision);
    } else if (id instanceof UUID uuid) {
      result = UuidId.of(uuid, entityType, revision);
    } else if (id instanceof String string) {
      result = StringId.of(string, entityType, revision);
    } else if (id instanceof Integer i) {
      result = LongId.of(Long.valueOf(i.longValue()), entityType, revision);
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
    // TODO may be a Long
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

  @SuppressWarnings("rawtypes")
  static <E, I extends Id> GenericId<E, ?, ?> empty(Class<E> entityType, Class<I> idClass) {

    GenericId<E, ?, ?> empty = EMPTY_ID_MAP.get(idClass);
    if (empty == null) {
      assert false : "" + idClass;
      empty = EMPTY_ID_MAP.get(idClass);
    }
    return empty.withEntityType(entityType);
  }

}
