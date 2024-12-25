package io.github.mmm.entity.bean.property;

import io.github.mmm.base.metainfo.MetaInfos;
import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.string.StringProperty;

/**
 * Interface for an {@link EntityBean} having a {@link #Name()}.
 */
@AbstractInterface
public abstract interface EntityWithName extends EntityBean {

  /**
   * @return the name of this entity.
   */
  @MetaInfos("score=1")
  StringProperty Name();

}
