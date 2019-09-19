/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.property.linklist;

import java.util.List;

import net.sf.mmm.entity.bean.EntityBean;
import net.sf.mmm.entity.bean.SimpleEntityBean;
import net.sf.mmm.entity.link.Link;
import net.sf.mmm.property.PropertyMetadata;
import net.sf.mmm.property.ReadableProperty;
import net.sf.mmm.property.WritableProperty;
import net.sf.mmm.property.factory.AbstractPropertyFactory;
import net.sf.mmm.property.factory.PropertyFactory;

/**
 * Implementation of {@link PropertyFactory} for {@link LinkListProperty}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link SimpleEntityBean}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyFactoryLinkList<E extends EntityBean>
    extends AbstractPropertyFactory<List<Link<E>>, LinkListProperty<E>> {

  @Override
  public Class<List<Link<E>>> getValueClass() {

    return (Class) List.class;
  }

  @Override
  public Class<? extends ReadableProperty<List<Link<E>>>> getReadableInterface() {

    return null;
  }

  @Override
  public Class<? extends WritableProperty<List<Link<E>>>> getWritableInterface() {

    return null;
  }

  @Override
  public Class<LinkListProperty<E>> getImplementationClass() {

    return (Class) LinkListProperty.class;
  }

  @Override
  public LinkListProperty<E> create(String name, Class<? extends List<Link<E>>> valueClass,
      PropertyMetadata<List<Link<E>>> metadata) {

    return new LinkListProperty<>(name, metadata);
  }

}
