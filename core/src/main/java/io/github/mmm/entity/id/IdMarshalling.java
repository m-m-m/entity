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
@SuppressWarnings("exports")
public interface IdMarshalling extends Marshalling<Id<?>> {

  @Override
  default void writeObject(StructuredWriter writer, Id<?> id) {

    if (id == null) {
      writer.writeValueAsNull();
      return;
    }
    ((GenericId<?, ?, ?>) id).write(writer);
  }

  @Override
  default Id<?> readObject(StructuredReader reader) {

    return readObject(reader, null);
  }

  /**
   * @param <E> type of the identified entity.
   * @param reader the {@link StructuredReader}.
   * @param type the {@link Id#getEntityType() entity type}.
   * @return the unmarshalled {@link Id}.
   */
  default <E> Id<E> readObject(StructuredReader reader, Class<E> type) {

    return readObject(reader, IdFactory.get(), type);
  }

  /**
   * @param <E> type of the identified entity.
   * @param <I> type of the {@link GenericId#get() ID}.
   * @param <V> type of the {@link GenericId#getRevision() revision}.
   * @param reader the {@link StructuredReader} to read from.
   * @param factory the {@link IdFactory} to create {@link Id} instances.
   * @param entityType the {@link GenericId#getEntityType() entity type}.
   * @return the unmarshalled {@link GenericId}.
   */
  static <E, I, V extends Comparable<?>> GenericId<E, I, V> readObject(StructuredReader reader, IdFactory<I, V> factory,
      Class<E> entityType) {

    Object id = null;
    Object revision = null;
    try {
      if (reader.readStartObject()) {
        while (!reader.readEnd()) {
          String name = reader.readName();
          if (GenericId.PROPERTY_LONG_ID.equals(name)) {
            id = update(id, reader.readValueAsLong(), Id.PROPERTY_ID);
          } else if (GenericId.PROPERTY_UUID.equals(name)) {
            id = update(id, UuidParser.get().parse(reader.readValueAsString()), Id.PROPERTY_ID);
          } else if (GenericId.PROPERTY_STRING_ID.equals(name)) {
            id = update(id, reader.readValueAsString(), Id.PROPERTY_ID);
          } else if (GenericId.PROPERTY_LONG_REVISION.equals(name)) {
            revision = update(revision, reader.readValueAsLong(), Id.PROPERTY_REVISION);
          } else if (GenericId.PROPERTY_INSTANT_REVISION.equals(name)) {
            revision = update(revision, reader.readValueAsInstant(), Id.PROPERTY_REVISION);
          } else {
            // ignore unknown property for compatibility and future extensions...
          }
        }
      } else {
        if (reader.isStringValue()) {
          id = reader.readValueAsString();
        } else {
          id = reader.readValueAsLong();
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse Id.", e);
    }
    return factory.createGeneric(entityType, id, revision);
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
