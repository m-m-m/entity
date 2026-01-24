package io.github.mmm.entity.bean.attribute;

import io.github.mmm.base.metainfo.MetaInfos;
import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.string.StringProperty;

/**
 * Interface for an {@link EntityBean} having a {@link #Key()}.
 */
@AbstractInterface
public abstract interface EntityWithKey extends EntityBean {

  /**
   * @return the (business) key of this entity. Typically its value should be unique per entity.
   */
  @MetaInfos("score=1")
  StringProperty Key();

}
