package io.github.mmm.entity.link;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.entity.DummyEntity;
import io.github.mmm.entity.id.Id;

/**
 * Test of {@link Link}.
 */
public class LinkTest extends Assertions {

  /** Test that creation via {@link Link#of(Id)} with empty {@link Id} fails. */
  @Test
  public void testCreateLinkWithEmptyIdFails() {

    // arrange
    Id<DummyEntity> id = null;
    // act
    try {
      Link<DummyEntity> link = new IdLinkWithoutRevision<>(id, null);
      assert (link != null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("Cannot create link with neither ID nor target entity!");
    }
  }

  /** Test that creation via {@link Link#of(Id)} with valid {@link Id} succeeds. */
  @Test
  public void testCreateLinkWithValidIdSucceeds() {

    // arrange
    Id<DummyEntity> id = Id.of(DummyEntity.class, 4711L, 1L);
    // act
    Link<DummyEntity> link = Link.of(id);
    // assert
    assertThat(link.getId()).isSameAs(id.withoutRevision());
  }

  /** Test of {@link Link#isResolved()}. */
  @Test
  public void testIsResolved() {

    // arrange
    Id<DummyEntity> id = Id.of(DummyEntity.class, 4711L, 1L);
    DummyEntity entity = new DummyEntity();
    entity.setId(id);
    // act
    Link<DummyEntity> entityLink = Link.of(entity);
    Link<DummyEntity> idLinkWithoutResolver = IdLink.of(id, null);
    Link<DummyEntity> idLinkWithResolver = IdLink.of(id, i -> i == id ? entity : null);
    // assert
    assertThat(entityLink.isResolved()).isTrue();
    assertThat(entityLink.getEntity()).isSameAs(entity);
    assertThat(entityLink.getId()).isNotSameAs(id);
    assertThat(entityLink.getId()).isEqualTo(id.withoutRevision());
    assertThat(idLinkWithResolver.isResolved()).isFalse();
    assertThat(idLinkWithResolver.getId()).isSameAs(id);
    assertThat(idLinkWithResolver.getEntity()).isSameAs(entity);
    assertThat(idLinkWithoutResolver.isResolved()).isFalse();
    assertThat(idLinkWithoutResolver.getId()).isSameAs(id);
    assertThat(idLinkWithoutResolver.getEntity()).isNull(); // target cannot be resolved without resolver function
  }

}
