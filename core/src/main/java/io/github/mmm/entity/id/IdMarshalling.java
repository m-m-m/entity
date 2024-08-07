/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.uuid.UuidParser;
import io.github.mmm.marshall.Marshalling;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/**
 * Helper class to read and write {@link Id} values.
 */
// ID classes and factory work fine without marshalling on module-path
public interface IdMarshalling extends Marshalling<Id<?>> {

  @Override
  default void writeObject(StructuredWriter writer, Id<?> id) {

    if (id == null) {
      writer.writeValueAsNull();
      return;
    }
    ((GenericId<?, ?, ?, ?>) id).write(writer);
  }

  @Override
  default Id<?> readObject(StructuredReader reader) {

    return readObject(reader, null);
  }

  /**
   * @param <E> type of the identified entity.
   * @param reader the {@link StructuredReader}.
   * @param type the {@link Id#getEntityClass() entity type}.
   * @return the unmarshalled {@link Id}.
   */
  default <E> Id<E> readObject(StructuredReader reader, Class<E> type) {

    return readObject(reader, IdFactory.get(), type);
  }

  /**
   * @param <E> type of the identified entity.
   * @param <I> type of the {@link GenericId#getPk() ID}.
   * @param <V> type of the {@link GenericId#getRevision() revision}.
   * @param reader the {@link StructuredReader} to read from.
   * @param factory the {@link IdFactory} to create {@link Id} instances.
   * @param entityType the {@link GenericId#getEntityClass() entity type}.
   * @return the unmarshalled {@link GenericId}.
   */
  static <E, I, V extends Comparable<?>> GenericId<E, I, V, ?> readObject(StructuredReader reader,
      IdFactory<I, V> factory, Class<E> entityType) {

    Object pk = null;
    Object revision = null;
    try {
      if (reader.readStartObject(RevisionedIdVersion.DEFAULT)) {
        while (!reader.readEnd()) {
          String name = reader.readName();
          if (GenericId.PROPERTY_PK_LONG.equals(name)) {
            pk = update(pk, reader.readValueAsLong(), Id.PROPERTY_PK);
          } else if (GenericId.PROPERTY_PK_UUID.equals(name)) {
            pk = update(pk, UuidParser.get().parse(reader.readValueAsString()), Id.PROPERTY_PK);
          } else if (GenericId.PROPERTY_PK_STRING.equals(name)) {
            pk = update(pk, reader.readValueAsString(), Id.PROPERTY_PK);
          } else if (GenericId.PROPERTY_REVISION_VERSION.equals(name)) {
            revision = update(revision, reader.readValueAsLong(), Id.PROPERTY_REVISION);
          } else if (GenericId.PROPERTY_REVISION_INSTANT.equals(name)) {
            revision = update(revision, reader.readValueAsInstant(), Id.PROPERTY_REVISION);
          } else {
            // ignore unknown property for compatibility and future extensions...
          }
        }
      } else {
        if (reader.isStringValue()) {
          pk = reader.readValueAsString();
        } else {
          pk = reader.readValueAsLong();
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse Id.", e);
    }
    return factory.createGeneric(entityType, pk, revision);
  }

  private static Object update(Object oldValue, Object newValue, String key) {

    if (oldValue == null) {
      return newValue;
    } else if (newValue == null) {
      return oldValue;
    } else {
      throw new DuplicateObjectException(newValue, key, oldValue);
    }
  }

  /**
   * @return the singleton instance of this {@link IdMarshalling}.
   */
  static IdMarshalling get() {

    return IdMarshallingImpl.INSTANCE;
  }

}
