package io.github.mmm.entity.bean.example;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.property.id.FkProperty;

/// [EntityBean] for testing.
public interface FkSource extends EntityBean {

  /// @return Foreign key to [Target].
  FkProperty<Target> Target();

}
