/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.entity.Entity;
import io.github.mmm.marshall.JsonFormat;
import io.github.mmm.marshall.MarshallingConfig;
import io.github.mmm.marshall.StructuredReader;

/**
 * Test of {@link IdMarshalling}.
 */
@SuppressWarnings("javadoc")
public class IdMarshallingTest extends Assertions {

  /** Test of {@link IdMarshalling} from/to JSON for all types of {@link Id}s. */
  @Test
  public void testAllIdTypes() {

    IdMarshalling marshalling = IdMarshalling.get();

    // test latest IDs
    check(marshalling, new LongLatestId<>(Entity.class, 42L), "42");
    check(marshalling, new StringLatestId<>(Entity.class, "MyId"), "\"MyId\"");
    UUID uuid = UUID.randomUUID();
    check(marshalling, new UuidLatestId<>(Entity.class, uuid), "\"" + uuid + "\"");
    // test version IDs
    check(marshalling, new LongVersionId<>(Entity.class, 42L, 5L), "{\"l\":42,\"v\":5}");
    check(marshalling, new StringVersionId<>(Entity.class, "MyId", 5L), "{\"s\":\"MyId\",\"v\":5}");
    check(marshalling, new UuidVersionId<>(Entity.class, uuid, 5L), "{\"u\":\"" + uuid + "\",\"v\":5}");
    // test timestamp IDs
    Instant ts = Instant.parse("1999-12-31T23:59:59.123456789Z");
    check(marshalling, new LongInstantId<>(Entity.class, 42L, ts),
        "{\"l\":42,\"t\":\"1999-12-31T23:59:59.123456789Z\"}");
    check(marshalling, new StringInstantId<>(Entity.class, "MyId", ts),
        "{\"s\":\"MyId\",\"t\":\"1999-12-31T23:59:59.123456789Z\"}");
    check(marshalling, new UuidInstantId<>(Entity.class, uuid, ts),
        "{\"u\":\"" + uuid + "\",\"t\":\"1999-12-31T23:59:59.123456789Z\"}");
  }

  public static void check(IdMarshalling marshalling, Id<Entity> id, String json) {

    assertThat(id.getType()).isSameAs(Entity.class);
    assertThat(writeJson(marshalling, id)).isEqualTo(json);
    assertThat(readJson(marshalling, json)).isEqualTo(id);
  }

  public static String writeJson(IdMarshalling marshalling, Id<?> id) {

    StringBuilder sb = new StringBuilder();
    marshalling.writeObject(JsonFormat.of(MarshallingConfig.NO_INDENTATION).writer(sb), id);
    return sb.toString();
  }

  public static Id<Entity> readJson(IdMarshalling marshalling, String json) {

    Id<Entity> id = readJson(marshalling, json, Entity.class);
    assertThat(id.getType()).isSameAs(Entity.class);
    return id;
  }

  public static <E> Id<E> readJson(IdMarshalling marshalling, String json, Class<E> entity) {

    StructuredReader reader = JsonFormat.of().reader(json);
    return marshalling.readObject(reader, entity);
  }
}
