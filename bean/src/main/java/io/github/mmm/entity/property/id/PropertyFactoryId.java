/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.Id;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.factory.AbstractPropertyFactory;
import io.github.mmm.property.factory.PropertyFactory;
import io.github.mmm.property.object.ObjectProperty;

/**
 * This is the implementation of {@link PropertyFactory} for {@link ObjectProperty}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertyFactoryId extends AbstractPropertyFactory<Id<?>, IdProperty> {

  @Override
  public Class<? extends Id<?>> getValueClass() {

    return (Class) Id.class;
  }

  @Override
  public Class<? extends ReadableProperty<Id<?>>> getReadableInterface() {

    return (Class) ReadableProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Id<?>>> getWritableInterface() {

    return (Class) WritableProperty.class;
  }

  @Override
  public Class<IdProperty> getImplementationClass() {

    return IdProperty.class;
  }

  @Override
  public IdProperty create(String name, Class<? extends Id<?>> valueClass, PropertyMetadata<Id<?>> metadata) {

    return new IdProperty(name, null, metadata);
  }

  @Override
  public boolean isPolymorphic() {

    return false;
  }

}
