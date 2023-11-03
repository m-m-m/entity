package io.github.mmm.entity.property.id;

import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link EntityBean} for testing.
 */
public interface Source extends EntityBean {

  /** @return Foreign key to {@link Target}. */
  FkProperty<Target> Target();

}
