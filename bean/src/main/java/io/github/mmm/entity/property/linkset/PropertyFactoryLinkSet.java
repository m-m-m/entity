/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.linkset;

import java.util.Set;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.SimpleEntityBean;
import io.github.mmm.entity.link.Link;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.factory.AbstractPropertyFactory;
import io.github.mmm.property.factory.PropertyFactory;

/**
 * Implementation of {@link PropertyFactory} for {@link LinkSetProperty}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link SimpleEntityBean}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyFactoryLinkSet<E extends EntityBean>
    extends AbstractPropertyFactory<Set<Link<E>>, LinkSetProperty<E>> {

  @Override
  public Class<Set<Link<E>>> getValueClass() {

    return (Class) Set.class;
  }

  @Override
  public Class<? extends ReadableProperty<Set<Link<E>>>> getReadableInterface() {

    return null;
  }

  @Override
  public Class<? extends WritableProperty<Set<Link<E>>>> getWritableInterface() {

    return null;
  }

  @Override
  public Class<LinkSetProperty<E>> getImplementationClass() {

    return (Class) LinkSetProperty.class;
  }

  @Override
  public LinkSetProperty<E> create(String name, Class<? extends Set<Link<E>>> valueClass,
      PropertyMetadata<Set<Link<E>>> metadata) {

    return new LinkSetProperty<>(name, metadata);
  }

}
