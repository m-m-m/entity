/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.util.Objects;

import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.uuid.UuidParser;
import io.github.mmm.marshall.Marshalling;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/**
 * Helper class to read and write {@link Id} values.
 */
@SuppressWarnings("exports")
public final class IdMarshaller implements Marshalling<Id<?>> {

  private static final IdMarshaller INSTANCE = new IdMarshaller();

  private IdMarshaller() {

  }

  @Override
  public void writeObject(StructuredWriter writer, Id<?> id) {

    if (id == null) {
      writer.writeValueAsNull();
      return;
    }
    AbstractId<?, ?, ?> abstractId = (AbstractId<?, ?, ?>) id;
    writer.writeStartObject();
    writer.writeName(abstractId.getMarshalPropertyId());
    writer.writeValue(id.getId());
    Object version = id.getVersion();
    String versionProperty = abstractId.getMarshalPropertyVersion();
    if (versionProperty == null) {
      Objects.requireNonNull(version);
    } else {
      writer.writeName(versionProperty);
      writer.writeValue(version);
    }
    writer.writeEnd();
  }

  @Override
  public Id<?> readObject(StructuredReader reader) {

    return readObject(reader, null);
  }

  /**
   * @param <E> type of the identified entity.
   * @param reader the {@link StructuredReader}.
   * @param type the {@link Id#getType() entity type}.
   * @return the unmarshalled {@link Id}.
   */
  public <E> Id<E> readObject(StructuredReader reader, Class<E> type) {

    Object id = null;
    Object version = null;
    try {
      if (reader.readStartObject()) {
        throw new IllegalArgumentException("Expected START_OBJECT but found " + reader.getState());
      }
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
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse Id.", e);
    }
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
   * @return the singleton instance of this {@link IdMarshaller}.
   */
  public static IdMarshaller get() {

    return INSTANCE;
  }

}
