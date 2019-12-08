/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/**
 * Helper class to read and write {@link Id} values.
 */
@SuppressWarnings("exports")
public final class IdMarshaller {

  private IdMarshaller() {

  }

  /**
   * @param writer the {@link StructuredWriter}.
   * @param id the {@link Id} to write to the {@link StructuredWriter}.
   */
  public static void writeId(StructuredWriter writer, Id<?> id) {

    String idValue = null;
    String version = null;
    if (id != null) {
      idValue = id.getIdAsString();
      version = id.getVersionAsString();
    }
    if (idValue == null) {
      writer.writeValueAsNull();
    } else if (version == null) {
      writer.writeValue(idValue);
    } else {
      writer.writeStartObject();
      writer.writeName(Id.PROPERTY_ID);
      writer.writeValue(idValue);
      writer.writeName(Id.PROPERTY_VERSION);
      writer.writeValue(version);
      writer.writeEnd();
    }
  }

  /**
   * @param <E> type of the identified entity.
   * @param reader the {@link StructuredReader}.
   * @param mapper the {@link IdMapper}.
   * @param type the {@link Id#getType() entity type}.
   * @return the unmarshalled {@link Id}.
   */
  public static <E> Id<E> readId(StructuredReader reader, IdMapper mapper, Class<E> type) {

    String id = null;
    String version = null;
    try {
      if (reader.readStartObject()) {
        while (!reader.readEnd()) {
          String name = reader.readName();
          if (Id.PROPERTY_ID.equals(name)) {
            id = reader.readValueAsString();
          } else if (Id.PROPERTY_VERSION.equals(name)) {
            version = reader.readValueAsString();
          } else {
            // ignore unknown property...
          }
        }
      } else {
        id = reader.readValueAsString();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse Id.", e);
    }
    return mapper.unmarshall(type, id, version);
  }

}
