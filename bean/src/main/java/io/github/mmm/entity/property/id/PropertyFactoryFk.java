/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.factory.AbstractPropertyFactory;
import io.github.mmm.property.factory.PropertyFactory;
import io.github.mmm.property.factory.PropertyTypeInfo;
import io.github.mmm.property.object.ObjectProperty;

/**
 * This is the implementation of {@link PropertyFactory} for {@link ObjectProperty}.
 *
 * @param <E> type of the referenced {@link EntityBean}.
 * @since 1.0.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertyFactoryFk<E extends EntityBean> extends AbstractPropertyFactory<Id<E>, FkProperty<E>> {

  @Override
  public Class<Id<E>> getValueClass() {

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
  public Class<FkProperty<E>> getImplementationClass() {

    return (Class) FkProperty.class;
  }

  @Override
  public FkProperty<E> create(String name, PropertyTypeInfo<Id<E>> typeInfo, PropertyMetadata<Id<E>> metadata) {

    Class<?> entityClass = typeInfo.getTypeArgumentClass(0);
    Class idClass = typeInfo.getValueClass();
    Id id = IdFactory.get().createEmpty(entityClass, idClass);
    return new FkProperty<>(name, id, metadata);
  }

  @Override
  public boolean isPolymorphic() {

    return false;
  }

}
