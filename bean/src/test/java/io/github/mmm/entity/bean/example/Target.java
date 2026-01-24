package io.github.mmm.entity.bean.example;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.attribute.EntityWithKey;
import io.github.mmm.entity.bean.attribute.EntityWithName;
import io.github.mmm.entity.bean.attribute.EntityWithTitle;

/**
 * {@link EntityBean} for testing.
 */
public interface Target extends EntityWithTitle, EntityWithKey, EntityWithName {

  static Target of() {

    return BeanFactory.get().create(Target.class);
  }

}
