/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.entity.property.link;

import java.util.function.Function;

import net.sf.mmm.entity.id.AbstractId;
import net.sf.mmm.entity.id.Id;
import net.sf.mmm.entity.id.IdFactory;
import net.sf.mmm.entity.id.IdMarshaller;
import net.sf.mmm.entity.link.IdLink;
import net.sf.mmm.entity.link.Link;
import net.sf.mmm.marshall.StructuredReader;
import net.sf.mmm.marshall.StructuredWriter;
import net.sf.mmm.property.PropertyMetadata;
import net.sf.mmm.property.object.ObjectProperty;

/**
 * {@link ObjectProperty} with {@link Link} {@link #getValue() value} {@link Link#getTarget() pointing to} an
 * {@link net.sf.mmm.entity.bean.EntityBean entity}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link net.sf.mmm.entity.bean.EntityBean entity}.
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
