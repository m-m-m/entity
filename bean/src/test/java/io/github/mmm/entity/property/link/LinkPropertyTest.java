package io.github.mmm.entity.property.link;

import org.junit.jupiter.api.Test;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.property.PropertyTest;
import io.github.mmm.property.WritableProperty;

/**
 * Test of {@link LinkProperty}.
 */
public class LinkPropertyTest extends PropertyTest<Link<Target>, LinkProperty<Target>> {

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
    LongVersionId<Target> id2 = new LongVersionId<>(Target.class, 4712L, 1L);
    // act
    LinkProperty<Target> readOnlyLinkProperty = WritableProperty.getReadOnly(linkProperty);
    // assert
    assertThat((Object) readOnlyLinkProperty.get()).isNull();
    linkProperty.set(LINK);
    Link<Target> readOnlyLink = readOnlyLinkProperty.get();
    readOnlyLink.getTarget().setId(id2);
    // assertThat(readOnlyLink).isNotNull().isNotSameAs(LINK);

  }

}
