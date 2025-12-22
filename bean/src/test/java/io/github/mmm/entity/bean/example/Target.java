package io.github.mmm.entity.bean.example;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.property.EntityWithKey;
import io.github.mmm.entity.bean.property.EntityWithName;
import io.github.mmm.entity.bean.property.EntityWithTitle;

/**
 * {@link EntityBean} for testing.
 */
public interface Target extends EntityWithTitle, EntityWithKey, EntityWithName {

  static Target of() {

    return BeanFactory.get().create(Target.class);
  }

}
