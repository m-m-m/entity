/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.property.linkset;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.function.Function;

import net.sf.mmm.entity.bean.EntityBean;
import net.sf.mmm.entity.bean.SimpleEntityBean;
import net.sf.mmm.entity.id.Id;
import net.sf.mmm.entity.id.IdFactory;
import net.sf.mmm.entity.id.IdMarshaller;
import net.sf.mmm.entity.link.IdLink;
import net.sf.mmm.entity.link.Link;
import net.sf.mmm.entity.property.linklist.LinkListProperty;
import net.sf.mmm.marshall.StructuredReader;
import net.sf.mmm.marshall.StructuredWriter;
import net.sf.mmm.property.PropertyMetadata;
import net.sf.mmm.property.container.set.SetProperty;

/**
 * {@link SetProperty} with {@link Set} {@link #getValue() value} containing {@link Link}s that each
 * {@link Link#getTarget() point to} an {@link SimpleEntityBean}. Unlike {@link LinkListProperty} the {@link Link}s of
 * an {@link LinkSetProperty} have no order and can not contain duplicates.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link SimpleEntityBean}.
 *
 * @since 1.0.0
 */
public class LinkSetProperty<E extends EntityBean> extends SetProperty<Link<E>> {

  private Class<E> entityClass;

  private IdFactory<?, ?, ?> idFactory;

  private Function<Id<E>, E> resolver;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public LinkSetProperty(String name, PropertyMetadata<Set<Link<E>>> metadata) {

    this(name, metadata, null, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} of the linked entity.
   */
  public LinkSetProperty(String name, PropertyMetadata<Set<Link<E>>> metadata, Class<E> entityClass) {

    this(name, metadata, entityClass, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param idFactory the optional {@link IdFactory}.
   */
  public LinkSetProperty(String name, PropertyMetadata<Set<Link<E>>> metadata, Class<E> entityClass,
      IdFactory<?, ?, ?> idFactory) {

    this(name, metadata, entityClass, idFactory, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param idFactory the optional {@link IdFactory}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   */
  public LinkSetProperty(String name, PropertyMetadata<Set<Link<E>>> metadata, Class<E> entityClass,
      IdFactory<?, ?, ?> idFactory, Function<Id<E>, E> resolver) {

    this(name, metadata, entityClass, idFactory, resolver, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param idFactory the optional {@link IdFactory}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   * @param componentType the {@link #getComponentType() component type}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public LinkSetProperty(String name, PropertyMetadata<Set<Link<E>>> metadata, Class<E> entityClass,
      IdFactory<?, ?, ?> idFactory, Function<Id<E>, E> resolver, Type componentType) {

    super(name, (Class) Link.class, componentType, metadata);
    this.entityClass = entityClass;
    this.idFactory = idFactory;
    this.resolver = resolver;
  }

  @Override
  protected Link<E> readElement(StructuredReader reader) {

    Id<E> id = IdMarshaller.readId(reader, this.idFactory, this.entityClass);
    return IdLink.of(id, this.resolver);
  }

  @Override
  protected void writeElement(StructuredWriter writer, Link<E> link) {

    Id<E> id = null;
    if (link != null) {
      id = link.getId();
    }
    IdMarshaller.writeId(writer, id);
  }

}
