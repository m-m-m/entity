package io.github.mmm.entity.bean.example;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.property.id.FkProperty;

/**
 * {@link EntityBean} for testing.
 */
public interface FkSource extends EntityBean {

  /** @return Foreign key to {@link Target}. */
  FkProperty<Target> Target();

}
