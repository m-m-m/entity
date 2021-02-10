/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;
import java.util.UUID;

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
    Object version = id.getVersion();
    if (version == null) {
      writer.writeValue(id.get());
    } else {
      AbstractId<?, ?, ?> abstractId = (AbstractId<?, ?, ?>) id;
      writer.writeStartObject();
      writer.writeName(abstractId.getMarshalPropertyId());
      writer.writeValue(id.get());
      String versionProperty = abstractId.getMarshalPropertyVersion();
      if (versionProperty == null) {
        Objects.requireNonNull(version);
      } else {
        writer.writeName(versionProperty);
        writer.writeValue(version);
      }
      writer.writeEnd();
    }
  }

  @Override
  default Id<?> readObject(StructuredReader reader) {

    return readObject(reader, null);
  }

  /**
   * @param <E> type of the identified entity.
   * @param reader the {@link StructuredReader}.
   * @param type the {@link Id#getType() entity type}.
   * @return the unmarshalled {@link Id}.
   */
  default <E> Id<E> readObject(StructuredReader reader, Class<E> type) {

    Object id = null;
    Object version = null;
    try {
      if (reader.readStartObject()) {
        while (!reader.readEnd()) {
          String name = reader.readName();
          if (AbstractId.PROPERTY_LONG_ID.equals(name)) {
            id = update(id, reader.readValueAsLong(), Id.PROPERTY_ID);
          } else if (AbstractId.PROPERTY_UUID.equals(name)) {
            id = update(id, UuidParser.get().parse(reader.readValueAsString()), Id.PROPERTY_ID);
          } else if (AbstractId.PROPERTY_STRING_ID.equals(name)) {
            id = update(id, reader.readValueAsString(), Id.PROPERTY_ID);
          } else if (AbstractId.PROPERTY_LONG_VERSION.equals(name)) {
            version = update(version, reader.readValueAsLong(), Id.PROPERTY_VERSION);
          } else if (AbstractId.PROPERTY_INSTANT_VERSION.equals(name)) {
            version = update(version, reader.readValueAsInstant(), Id.PROPERTY_VERSION);
          } else {
            // ignore unknown property for compatibility and future extensions...
          }
        }
      } else {
        id = reader.readValue();
        if (id instanceof String) {
          UUID uuid = UuidParser.get().parse((String) id);
          if (uuid != null) {
            id = uuid;
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse Id.", e);
    }
    return create(type, id, version);
  }

  /**
   * @param <E> type of the identified entity.
   * @param type the {@link Id#getType() entity type}.
   * @param id the {@link Id#get() id}.
   * @param version the optional {@link Id#getVersion() version}.
   * @return the new {@link Id} instance.
   */
  default <E> Id<E> create(Class<E> type, Object id, Object version) {

    return Id.of(type, id, version);
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
