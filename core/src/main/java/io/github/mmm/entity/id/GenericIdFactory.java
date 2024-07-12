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
    EMPTY_ID_MAP.put(null, PkIdLong.getEmpty());
    EMPTY_ID_MAP.put(Id.class, PkIdLong.getEmpty());
    EMPTY_ID_MAP.put(GenericId.class, PkIdLong.getEmpty());
    EMPTY_ID_MAP.put(AbstractId.class, PkIdLong.getEmpty());
    EMPTY_ID_MAP.put(PkIdLong.class, PkIdLong.getEmpty());
    EMPTY_ID_MAP.put(PkId.class, PkIdLong.getEmpty());
    EMPTY_ID_MAP.put(PkIdLong.class, PkIdLong.getEmpty());
    EMPTY_ID_MAP.put(PkIdString.class, PkIdString.getEmpty());
    EMPTY_ID_MAP.put(PkIdUuid.class, PkIdUuid.getEmpty());
  }

  static final GenericIdFactory INSTANCE = new GenericIdFactory();

  private GenericIdFactory() {

  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <E> GenericId<E, Object, Comparable<?>, ?> create(Class<E> entityClass, Object pk, Comparable<?> revision) {

    if (revision instanceof Integer i) {
      revision = Long.valueOf(i.longValue());
    }
    PkId pkId = PkId.of(entityClass, pk);
    if (pkId == null) {
      assert (revision == null);
      return null;
    }
    return pkId.withRevisionGeneric(revision);
  }

  @Override
  public Object parsePk(String pkString) {

    UUID uuid = UuidParser.get().parse(pkString);
    if (uuid != null) {
      return uuid;
    }
    // TODO may be a Long
    return pkString;
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
  static <E, I extends Id> GenericId<E, ?, ?, ?> empty(Class<E> entityType, Class<I> idClass) {

    GenericId<E, ?, ?, ?> empty = EMPTY_ID_MAP.get(idClass);
    if (empty == null) {
      throw new IllegalStateException(idClass.getName());
    }
    return empty.withEntityType(entityType);
  }

}
