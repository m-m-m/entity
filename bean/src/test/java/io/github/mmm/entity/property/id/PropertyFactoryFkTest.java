package io.github.mmm.entity.property.id;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkIdLong;
import io.github.mmm.entity.id.RevisionedIdVersion;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.property.link.LinkProperty;

/**
 * Test of {@link PropertyFactoryFk} and {@link FkProperty}.
 */
public class PropertyFactoryFkTest extends Assertions {

  /**
   * Test of that {@link BeanFactory#create(Class) bean creation} using {@link PropertyFactoryFk} will determine and
   * assign {@link Id#getEntityClass()} correctly.
   */
  @Test
  public void testLinkCreationWithEntityType() {

    // arrange
    // act
    Source source = BeanFactory.get().create(Source.class);
    FkProperty<Target> targetProperty = source.Target();
    Id<Target> id = targetProperty.get();
    // assert
    assertThat(id.getEntityClass()).isSameAs(Target.class);
    assertThat(id.getPk()).isNull();
    assertThat(id.getRevision()).isNull();
  }

  /** Test of {@link LinkProperty#set(Object)} with {@link Link} of wrong entity type. */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  public void testSetFkOfWrongTypeFails() {

    // arrange
    Source source = BeanFactory.get().create(Source.class);
    GenericId<Source, Long, Long, ?> sourceId = RevisionedIdVersion.DEFAULT.create(Source.class, 4711L, 1L);
    try {
      // act
      source.Target().set((Id) sourceId); // doing evil things should fail
      // assert
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("Cannot set ID of type " + Source.class.getName()
          + " to FkProperty Target with incompatible type " + Target.class.getName());
    }
  }

  /** Test of {@link LinkProperty#set(Object)} with {@link Link} of correct entity type. */
  @Test
  public void testSetLinkWithCorrectTypeSucceeds() {

    // arrange
    RevisionedIdVersion<Target, Long> id = new RevisionedIdVersion<>(new PkIdLong<>(Target.class, 4711L), 1L);
    Source source = BeanFactory.get().create(Source.class);
    // act
    source.Target().set(id);
    // assert
    assertThat(source.Target().get()).isSameAs(id);
  }

}
