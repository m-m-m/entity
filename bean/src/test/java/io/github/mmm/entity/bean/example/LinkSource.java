package io.github.mmm.entity.bean.example;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.property.link.LinkProperty;

/**
 * {@link EntityBean} for testing.
 */
public interface LinkSource extends EntityBean {

  /** @return Link to {@link Target}. */
  LinkProperty<Target> Target();

}
