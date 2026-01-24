package io.github.mmm.entity.bean.attribute;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.number.integers.IntegerProperty;

/**
 * Interface for an {@link EntityBean} having a {@link #Order()}.
 */
@AbstractInterface
public abstract interface EntityWithOrder extends EntityBean {

  /**
   * @return the potential ordering of this entity. If this entity acts as a list or {@link EntityWithParent tree} of data this
   *         order will be used to define the sorting so the entities will be displayed in ascending order.
   */
  IntegerProperty Order();

}
