package io.github.mmm.entity.property.link;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.PkIdLong;
import io.github.mmm.entity.link.IdLink;

/**
 * Implementation of {@link LinkMarshalling}.
 */
final class LinkMarshallingImpl implements LinkMarshalling {

  /** internal example instance. */
  static final IdLink<Entity> EXAMPLE_LINK = IdLink.of(PkIdLong.of(1L, Entity.class), null);

  /** The signleton instance. */
  static final LinkMarshallingImpl INSTANCE = new LinkMarshallingImpl();

  private LinkMarshallingImpl() {

  }

}
