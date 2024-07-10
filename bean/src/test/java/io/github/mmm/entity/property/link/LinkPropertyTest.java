package io.github.mmm.entity.property.link;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.github.mmm.base.exception.ReadOnlyException;
import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.PropertyTest;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongInstantId;
import io.github.mmm.entity.id.LongRevisionlessId;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.entity.id.StringInstantId;
import io.github.mmm.entity.id.StringRevisionlessId;
import io.github.mmm.entity.id.StringVersionId;
import io.github.mmm.entity.id.UuidInstantId;
import io.github.mmm.entity.id.UuidRevisionlessId;
import io.github.mmm.entity.id.UuidVersionId;
import io.github.mmm.entity.link.Link;
import io.github.mmm.marshall.MarshallingConfig;
import io.github.mmm.marshall.StandardFormat;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredTextFormat;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.WritableProperty;

/**
 * Test of {@link LinkProperty}.
 */
public class LinkPropertyTest extends PropertyTest<Link<Target>, LinkProperty<Target>> {

  /**
   *
   */
  private static final String INSTANT = "1999-12-31T23:59:59.123456789Z";

  private static final Id<Target> ID;

  private static final Target TARGET;

  private static final Link<Target> LINK;

  static {
    ID = new LongVersionId<>(Target.class, 4711L, 1L);
    TARGET = BeanFactory.get().create(Target.class);
    TARGET.Id().set(ID);
    LINK = Link.of(TARGET);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  LinkPropertyTest() {

    super(LINK, (Class) LinkProperty.class);
  }

  /** Test of {@link LinkProperty#getReadOnly()}. */
  @Test
  public void testReadOnly() {

    // arrange
    LinkProperty<Target> linkProperty = new LinkProperty<>("target", Target.class, null);
    LongVersionId<Target> id = new LongVersionId<>(Target.class, 4712L, 1L);
    // act
    LinkProperty<Target> readOnlyLinkProperty = WritableProperty.getReadOnly(linkProperty);
    // assert
    assertThat((Object) readOnlyLinkProperty.get()).isNull();
    linkProperty.set(LINK);
    Link<Target> readOnlyLink = readOnlyLinkProperty.get();
    assertThrows(ReadOnlyException.class, () -> {
      readOnlyLink.getTarget().setId(id);
    });
    assertThat(readOnlyLink).isNotNull().isNotSameAs(LINK);
  }

  @Test
  public void testMapJson() {

    Instant instant = Instant.parse(INSTANT);
    // LongId variants
    checkMapJson("4711", 4711L, null, LongRevisionlessId.class);
    checkMapJson("{\"l\":4711,\"v\":42}", 4711L, 42L, LongVersionId.class);
    checkMapJson("{\"l\":4711,\"t\":\"" + INSTANT + "\"}", 4711L, instant, LongInstantId.class);
    // UuidId variants
    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString();
    checkMapJson("\"" + uuidString + "\"", uuid, null, UuidRevisionlessId.class);
    checkMapJson("{\"u\":\"" + uuidString + "\",\"v\":43}", uuid, 43L, UuidVersionId.class);
    checkMapJson("{\"u\":\"" + uuidString + "\",\"t\":\"" + INSTANT + "\"}", uuid, instant, UuidInstantId.class);
    // String variants
    String pk = "primary-key";
    checkMapJson("\"" + pk + "\"", pk, null, StringRevisionlessId.class);
    checkMapJson("{\"s\":\"" + pk + "\",\"v\":44}", pk, 44L, StringVersionId.class);
    checkMapJson("{\"s\":\"" + pk + "\",\"t\":\"" + INSTANT + "\"}", pk, instant, StringInstantId.class);
  }

  private void checkMapJson(String json, Object pk, Object revision, Class<? extends Id> idClass) {

    // arrange
    LinkProperty<Target> linkProperty = new LinkProperty<>("target", Target.class, null);
    StructuredTextFormat jsonFormat = StandardFormat.json(MarshallingConfig.NO_INDENTATION);
    StructuredReader reader = jsonFormat.reader(json);
    StringBuilder sb = new StringBuilder(json.length());
    StructuredWriter writer = jsonFormat.writer(sb);
    // act
    linkProperty.read(reader);
    Id<Target> id = linkProperty.get().getId();
    linkProperty.write(writer);
    // assert
    assertThat(id.getPk()).isEqualTo(pk);
    assertThat(id.getRevision()).isEqualTo(revision);
    assertThat(id.getEntityClass()).isSameAs(Target.class);
    assertThat(id.getClass()).isSameAs(idClass);
    assertThat(sb.toString()).isEqualTo(json);
  }

  @Override
  protected void verifyReadOnlyValue(LinkProperty<Target> readOnly) {

    assertThat(readOnly.get()).isNotNull().isNotSameAs(LINK);
  }

}
