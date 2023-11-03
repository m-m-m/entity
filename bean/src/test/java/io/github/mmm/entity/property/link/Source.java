package io.github.mmm.entity.property.link;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link EntityBean} for testing.
 */
public interface Source extends EntityBean {

  /** @return Link to {@link Target}. */
  LinkProperty<Target> Target();

}
