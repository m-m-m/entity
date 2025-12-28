package io.github.mmm.entity.property.link;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.example.LinkSource;
import io.github.mmm.entity.bean.example.Target;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.link.Link;

/**
 * Test of {@link PropertyFactoryLink} and {@link LinkProperty}.
 */
class PropertyFactoryLinkTest extends Assertions {

  /**
   * Test of that {@link BeanFactory#create(Class) bean creation} using {@link PropertyFactoryLink} will determine and
   * assign {@link LinkProperty#getEntityClass()} correctly.
   */
  @Test
  void testLinkCreationWithEntityType() {

    // arrange
    // act
    LinkSource source = BeanFactory.get().create(LinkSource.class);
    LinkProperty<Target> targetProperty = source.Target();
    // assert
    assertThat(targetProperty.getEntityClass()).isSameAs(Target.class);
    assertThat(targetProperty.get().isEmpty()).isTrue();
  }

  /** Test of {@link LinkProperty#set(Object)} with {@link Link} of wrong entity type. */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  void testSetLinkOfWrongTypeFails() {

    // arrange
    LinkSource source = BeanFactory.get().create(LinkSource.class);
    try {
      // act
      source.Target().set((Link) Link.of(source)); // doing evil things should fail
      // assert
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("Cannot set link of type " + LinkSource.class.getName()
          + " to property Target with incompatible entity type " + Target.class.getName());
    }
  }

  /** Test of {@link LinkProperty#set(Object)} with {@link Link} of correct entity type. */
  @Test
  void testSetLinkWithCorrectTypeSucceeds() {

    // arrange
    Target target = BeanFactory.get().create(Target.class);
    Id<Target> id = Id.of(Target.class, 4711L);
    target.setId(id);
    LinkSource source = BeanFactory.get().create(LinkSource.class);
    // act
    source.Target().set(Link.of(target));
    // assert
    assertThat(source.Target().get().getId()).isEqualTo(id);
    assertThat(source.Target().get().getEntity()).isSameAs(target);
  }

}
