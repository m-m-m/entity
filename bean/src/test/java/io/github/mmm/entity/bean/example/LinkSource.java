package io.github.mmm.entity.bean.example;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.property.link.LinkProperty;

/// [EntityBean] for testing.
public interface LinkSource extends EntityBean {

  /// @return Link to [Target].
  LinkProperty<Target> Target();

}
