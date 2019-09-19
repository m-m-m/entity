/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.property.linkset;

import java.util.Set;

import net.sf.mmm.entity.bean.EntityBean;
import net.sf.mmm.entity.bean.SimpleEntityBean;
import net.sf.mmm.entity.link.Link;
import net.sf.mmm.property.PropertyMetadata;
import net.sf.mmm.property.ReadableProperty;
import net.sf.mmm.property.WritableProperty;
import net.sf.mmm.property.factory.AbstractPropertyFactory;
import net.sf.mmm.property.factory.PropertyFactory;

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
