package io.github.mmm.entity.bean.property;

import io.github.mmm.base.metainfo.MetaInfos;
import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.string.StringProperty;

/**
 * Interface for an {@link EntityBean} having a {@link #Title()}.
 */
@AbstractInterface
public abstract interface EntityWithTitle extends EntityBean {

  /**
   * @return the title (display name) of this entity.
   */
  @MetaInfos("score=1")
  StringProperty Title();

}
