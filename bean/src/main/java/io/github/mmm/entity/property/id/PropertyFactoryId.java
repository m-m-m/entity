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
 * @param <E> the generic type of the {@link io.github.mmm.entity.bean.EntityBean entity}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertyFactoryId<E> extends AbstractPropertyFactory<Id<E>, IdProperty<E>> {

  @Override
  public Class<? extends Id<E>> getValueClass() {

    return (Class) Id.class;
  }

  @Override
  public Class<? extends ReadableProperty<Id<E>>> getReadableInterface() {

    return (Class) ReadableProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Id<E>>> getWritableInterface() {

    return (Class) WritableProperty.class;
  }

  @Override
  public Class<IdProperty<E>> getImplementationClass() {

    return (Class) IdProperty.class;
  }

  @Override
  public IdProperty<E> create(String name, Class<? extends Id<E>> valueClass, PropertyMetadata<Id<E>> metadata) {

    return new IdProperty<>(name, valueClass, metadata);
  }

  @Override
  public boolean isPolymorphic() {

    return false;
  }

}
