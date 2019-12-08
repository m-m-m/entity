/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.linklist;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

import io.github.mmm.entity.bean.SimpleEntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.id.IdMarshaller;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.link.Link;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.container.list.ListProperty;

/**
 * {@link ListProperty} with {@link List} of {@link Link}s that each {@link Link#getTarget() point to} an
 * {@link SimpleEntityBean}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link SimpleEntityBean}.
 *
 * @since 1.0.0
 */
public class LinkListProperty<E> extends ListProperty<Link<E>> {

  private Class<E> entityClass;

  private IdFactory<?, ?, ?> idFactory;

  private Function<Id<E>, E> resolver;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public LinkListProperty(String name, PropertyMetadata<List<Link<E>>> metadata) {

    this(name, metadata, null, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} of the linked entity.
   */
  public LinkListProperty(String name, PropertyMetadata<List<Link<E>>> metadata, Class<E> entityClass) {

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
  public LinkListProperty(String name, PropertyMetadata<List<Link<E>>> metadata, Class<E> entityClass,
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
  public LinkListProperty(String name, PropertyMetadata<List<Link<E>>> metadata, Class<E> entityClass,
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
  public LinkListProperty(String name, PropertyMetadata<List<Link<E>>> metadata, Class<E> entityClass,
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
