package io.github.mmm.entity.link;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.entity.DummyEntity;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongVersionId;

/**
 * Test of {@link Link}.
 */
public class LinkTest extends Assertions {

  /** Test that creation via {@link Link#of(Id)} with empty {@link Id} fails. */
  @Test
  public void testCreateLinkWithEmptyIdFails() {

    // arrange
    Id<DummyEntity> id = LongVersionId.getEmpty(DummyEntity.class);
    // act
    try {
      Link<DummyEntity> link = Link.of(id);
      assert (link != null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("Cannot create link for empty ID - primary key must be present!");
    }
  }

  /** Test that creation via {@link Link#of(Id)} with valid {@link Id} succeeds. */
  @Test
  public void testCreateLinkWithValidIdSucceeds() {

    // arrange
    Id<DummyEntity> id = LongVersionId.getEmpty(DummyEntity.class).withIdAndRevision(4711L, 1L);
    // act
    Link<DummyEntity> link = Link.of(id);
    // assert
    assertThat(link.getId()).isSameAs(id);
  }

  /** Test of {@link Link#isResolved()}. */
  @Test
  public void testIsResolved() {

    // arrange
    Id<DummyEntity> id = LongVersionId.getEmpty(DummyEntity.class).withIdAndRevision(4711L, 1L);
    DummyEntity entity = new DummyEntity();
    entity.setId(id);
    // act
    Link<DummyEntity> entityLink = Link.of(entity);
    Link<DummyEntity> idLinkWithoutResolver = IdLink.of(id, null);
    Link<DummyEntity> idLinkWithResolver = IdLink.of(id, i -> i == id ? entity : null);
    // assert
    assertThat(entityLink.isResolved()).isTrue();
    assertThat(entityLink.getTarget()).isSameAs(entity);
    assertThat(entityLink.getId()).isNotSameAs(id);
    assertThat(entityLink.getId()).isEqualTo(id.withoutRevision());
    assertThat(idLinkWithResolver.isResolved()).isFalse();
    assertThat(idLinkWithResolver.getId()).isSameAs(id);
    assertThat(idLinkWithResolver.getTarget()).isSameAs(entity);
    assertThat(idLinkWithoutResolver.isResolved()).isFalse();
    assertThat(idLinkWithoutResolver.getId()).isSameAs(id);
    assertThat(idLinkWithoutResolver.getTarget()).isNull(); // target cannot be resolved without resolver function
  }

}
