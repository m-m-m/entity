/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.link;

import java.util.function.Function;

import io.github.mmm.entity.id.AbstractId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.id.IdMarshaller;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.link.Link;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.object.ObjectProperty;

/**
 * {@link ObjectProperty} with {@link Link} {@link #getValue() value} {@link Link#getTarget() pointing to} an
 * {@link io.github.mmm.entity.bean.EntityBean entity}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link io.github.mmm.entity.bean.EntityBean entity}.
 *
 * @since 1.0.0
 */
public class LinkProperty<E> extends ObjectProperty<Link<E>> {

  private Class<E> entityClass;

  private IdFactory<?, ?, ?> idFactory;

  private Function<Id<E>, E> resolver;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public LinkProperty(String name, PropertyMetadata<Link<E>> metadata) {

    this(name, metadata, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} of the linked entity.
   */
  public LinkProperty(String name, PropertyMetadata<Link<E>> metadata, Class<E> entityClass) {

    this(name, metadata, entityClass, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param idFactory the optional {@link IdFactory}.
   */
  public LinkProperty(String name, PropertyMetadata<Link<E>> metadata, Class<E> entityClass,
      IdFactory<?, ?, ?> idFactory) {

    this(name, metadata, entityClass, idFactory, null);
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
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public LinkProperty(String name, PropertyMetadata<Link<E>> metadata, Class<E> entityClass,
      IdFactory<?, ?, ?> idFactory, Function<Id<E>, E> resolver) {

    super(name, (Class) Link.class, metadata);
    this.entityClass = entityClass;
    this.idFactory = idFactory;
    this.resolver = resolver;
  }

  @Override
  protected void doSetValue(Link<E> newValue) {

    if (newValue != null) {
      Id<E> id = newValue.getId();
      if (id != null) {
        if (this.idFactory == null) {
          this.idFactory = ((AbstractId<?, ?, ?>) id).getFactory();
        }
        if (this.entityClass == null) {
          this.entityClass = id.getType();
        } else if (newValue instanceof IdLink) {
          newValue = ((IdLink<E>) newValue).withType(this.entityClass);
        }
      }
    }
    super.doSetValue(newValue);
  }

  @Override
  public void read(StructuredReader reader) {

    Id<E> id = IdMarshaller.readId(reader, this.idFactory, this.entityClass);
    IdLink<E> link = IdLink.of(id, this.resolver);
    setValue(link);
  }

  @Override
  public void write(StructuredWriter writer) {

    Id<E> id = null;
    Link<E> link = getValue();
    if (link != null) {
      id = link.getId();
    }
    IdMarshaller.writeId(writer, id);
  }

}
