/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.link;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.link.Link;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.factory.AbstractPropertyFactory;
import io.github.mmm.property.factory.PropertyFactory;
import io.github.mmm.property.factory.PropertyTypeInfo;

/**
 * Implementation of {@link PropertyFactory} for {@link LinkProperty}.
 *
 * @param <E> the generic type of the {@link Link#getEntity() linked entity}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyFactoryLink<E extends EntityBean> extends AbstractPropertyFactory<Link<E>, LinkProperty<E>> {

  @Override
  public Class<Link<E>> getValueClass() {

    return (Class) Link.class;
  }

  @Override
  public Class<? extends ReadableProperty<Link<E>>> getReadableInterface() {

    return null;
  }

  @Override
  public Class<? extends WritableProperty<Link<E>>> getWritableInterface() {

    return null;
  }

  @Override
  public Class<LinkProperty<E>> getImplementationClass() {

    return (Class) LinkProperty.class;
  }

  @Override
  public LinkProperty<E> create(String name, PropertyTypeInfo<Link<E>> typeInfo, PropertyMetadata<Link<E>> metadata) {

    Class entityClass = typeInfo.getTypeArgumentClass(0);
    return new LinkProperty<>(name, entityClass, metadata);
  }

  @Override
  public boolean isPolymorphic() {

    return true;
  }

}
