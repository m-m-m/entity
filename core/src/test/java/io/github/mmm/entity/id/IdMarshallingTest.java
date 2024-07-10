/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

import java.time.Instant;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.entity.Entity;
import io.github.mmm.marshall.MarshallingConfig;
import io.github.mmm.marshall.StandardFormat;
import io.github.mmm.marshall.StructuredReader;

/**
 * Test of {@link IdMarshalling}.
 */
@SuppressWarnings("javadoc")
public class IdMarshallingTest extends Assertions {

  /** Test of {@link IdMarshalling} from/to JSON for all types of {@link Id}s. */
  @Test
  public void testAllIdTypes() {

    UUID uuid = UUID.randomUUID();
    // test version IDs
    check(new LongVersionId<>(Entity.class, 42L, 5L), "{\"l\":42,\"v\":5}");
    check(new StringVersionId<>(Entity.class, "MyId", 5L), "{\"s\":\"MyId\",\"v\":5}");
    check(new UuidVersionId<>(Entity.class, uuid, 5L), "{\"u\":\"" + uuid + "\",\"v\":5}");
    // test timestamp IDs
    Instant ts = Instant.parse("1999-12-31T23:59:59.123456789Z");
    check(new LongInstantId<>(Entity.class, 42L, ts), "{\"l\":42,\"t\":\"1999-12-31T23:59:59.123456789Z\"}");
    check(new StringInstantId<>(Entity.class, "MyId", ts), "{\"s\":\"MyId\",\"t\":\"1999-12-31T23:59:59.123456789Z\"}");
    check(new UuidInstantId<>(Entity.class, uuid, ts),
        "{\"u\":\"" + uuid + "\",\"t\":\"1999-12-31T23:59:59.123456789Z\"}");
    // test flat IDs
    check(new LongRevisionlessId<>(Entity.class, 42L), "42");
    check(new StringRevisionlessId<>(Entity.class, "MyId"), "\"MyId\"");
    check(new UuidRevisionlessId<>(Entity.class, uuid), "\"" + uuid + "\"");
  }

  /**
   * Test of {@link IdMarshalling} to JSON for all {@link AbstractVersionId}s with
   * {@link AbstractVersionId#getRevision() revision} of {@code 0}.
   */
  @Test
  public void testWriteWithZeroVersion() {

    UUID uuid = UUID.randomUUID();
    assertThat(writeJson(new LongVersionId<>(Entity.class, 42L, 0L))).isEqualTo("{\"l\":42,\"v\":0}");
    assertThat(writeJson(new StringVersionId<>(Entity.class, "MyId", 0L))).isEqualTo("{\"s\":\"MyId\",\"v\":0}");
    assertThat(writeJson(new UuidVersionId<>(Entity.class, uuid, 0L))).isEqualTo("{\"u\":\"" + uuid + "\",\"v\":0}");
  }

  /**
   * Test of {@link IdMarshalling} to JSON for all {@link AbstractVersionId}s with
   * {@link AbstractVersionId#getRevision() revision} of {@code 0}.
   */
  @Test
  public void testWriteWithoutRevision() {

    UUID uuid = UUID.randomUUID();
    assertThat(writeJson(new LongRevisionlessId<>(Entity.class, 42L))).isEqualTo("42");
    assertThat(writeJson(new StringRevisionlessId<>(Entity.class, "MyId"))).isEqualTo("\"MyId\"");
    assertThat(writeJson(new UuidRevisionlessId<>(Entity.class, uuid))).isEqualTo("\"" + uuid + '"');
  }

  public static void check(GenericId<Entity, ?, ?> id, String json) {

    assertThat(id.getEntityClass()).isSameAs(Entity.class);
    assertThat(writeJson(id)).isEqualTo(json);
    assertThat(readJson(id.withPkAndRevision(null, null), json)).isEqualTo(id);
  }

  public static String writeJson(GenericId<?, ?, ?> id) {

    StringBuilder sb = new StringBuilder();
    id.write(StandardFormat.json(MarshallingConfig.NO_INDENTATION).writer(sb));
    return sb.toString();
  }

  public static GenericId<Entity, ?, ?> readJson(GenericId<Entity, ?, ?> id, String json) {

    StructuredReader reader = StandardFormat.json().reader(json);
    GenericId<Entity, ?, ?> newId = id.readObject(reader);
    assertThat(newId.getEntityClass()).isSameAs(Entity.class);
    return newId;
  }

}
