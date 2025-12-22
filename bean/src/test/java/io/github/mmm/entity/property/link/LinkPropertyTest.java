package io.github.mmm.entity.property.link;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.github.mmm.base.exception.ReadOnlyException;
import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.PropertyTest;
import io.github.mmm.entity.bean.example.Target;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkIdLong;
import io.github.mmm.entity.id.PkIdString;
import io.github.mmm.entity.id.PkIdUuid;
import io.github.mmm.entity.id.RevisionedIdInstant;
import io.github.mmm.entity.id.RevisionedIdVersion;
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

  private static final String TEST_INSTANT = "1999-12-31T23:59:59.123456789Z";

  private static final Id<Target> ID = new RevisionedIdVersion<>(new PkIdLong<>(Target.class, 4711L), 1L);

  private static final Target TARGET;

  private static final Link<Target> LINK;

  static {
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
    // act
    LinkProperty<Target> readOnlyLinkProperty = WritableProperty.getReadOnly(linkProperty);
    // assert
    assertThat((Object) readOnlyLinkProperty.get()).isNull();
    linkProperty.set(LINK);
    Link<Target> readOnlyLink = readOnlyLinkProperty.get();
    assertThrows(ReadOnlyException.class, () -> {
      readOnlyLink.getTarget().setId(ID);
    });
    assertThat(readOnlyLink).isNotNull().isNotSameAs(LINK);
  }

  @Test
  public void testMapJson() {

    Instant instant = Instant.parse(TEST_INSTANT);
    // LongId variants
    checkMapJson("4711", 4711L, null, PkIdLong.class);
    checkMapJson("{\"id\":{\"l\":4711,\"v\":42}}", 4711L, 42L, RevisionedIdVersion.class);
    checkMapJson("{\"id\":{\"l\":4711,\"t\":\"" + TEST_INSTANT + "\"}}", 4711L, instant, RevisionedIdInstant.class);
    // UuidId variants
    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString();
    checkMapJson("\"" + uuidString + "\"", uuid, null, PkIdUuid.class);
    checkMapJson("{\"id\":{\"u\":\"" + uuidString + "\",\"v\":43}}", uuid, 43L, RevisionedIdVersion.class);
    checkMapJson("{\"id\":{\"u\":\"" + uuidString + "\",\"t\":\"" + TEST_INSTANT + "\"}}", uuid, instant,
        RevisionedIdInstant.class);
    // String variants
    String pk = "primary-key";
    checkMapJson("\"" + pk + "\"", pk, null, PkIdString.class);
    checkMapJson("{\"id\":{\"s\":\"" + pk + "\",\"v\":44}}", pk, 44L, RevisionedIdVersion.class);
    checkMapJson("{\"id\":{\"s\":\"" + pk + "\",\"t\":\"" + TEST_INSTANT + "\"}}", pk, instant,
        RevisionedIdInstant.class);
  }

  @SuppressWarnings("rawtypes")
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

  @Test
  void testTargetMapJson() {

    // arrange
    Target target = Target.of();
    target.Key().set("key");
    target.Name().set("name");
    target.Title().set("title");
    target.Id().set(PkIdString.of("primary-key", Target.class));
    LinkProperty<Target> linkProperty = new LinkProperty<>("target", Target.class, null);
    LinkProperty<Target> linkProperty2 = new LinkProperty<>("target", Target.class, null);
    linkProperty.set(Link.of(target));
    StructuredTextFormat jsonFormat = StandardFormat.json(MarshallingConfig.NO_INDENTATION);

    // act
    String json = jsonFormat.write(linkProperty);
    jsonFormat.read(json, linkProperty2);

    // assert
    assertThat(json)
        .isEqualTo("{\"t\":{\"Id\":\"primary-key\",\"Key\":\"key\",\"Name\":\"name\",\"Title\":\"title\"}}");
    assertThat(linkProperty).isEqualTo(linkProperty2);
  }

  @Override
  protected void verifyReadOnlyValue(LinkProperty<Target> readOnly) {

    assertThat(readOnly.get()).isNotNull().isNotSameAs(LINK);
  }

}
