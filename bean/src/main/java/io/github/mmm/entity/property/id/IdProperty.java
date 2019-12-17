/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.AbstractId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactories;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.id.IdMarshaller;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.object.ObjectProperty;

/**
 * {@link ObjectProperty} with {@link Id} {@link #getValue() value} pointing to an entity.
 *
 * @param <E> the generic type of the {@link io.github.mmm.entity.bean.EntityBean entity}.
 *
 * @since 1.0.0
 */
public class IdProperty<E> extends ObjectProperty<Id<E>> {

  /** Default {@link #getName() name}. */
  public static final String NAME = "Id";

  private Class<E> entityClass;

  private IdFactory<?, ?, ?> idFactory;

  /**
   * The constructor.
   *
   * @param idFactory the {@link IdFactory} to marshal data.
   * @param entityClass the {@link Class} reflecting the entity.
   */
  public IdProperty(IdFactory<?, ?, ?> idFactory, Class<E> entityClass) {

    this(NAME, idFactory, entityClass, null);
  }

  /**
   * The constructor.
   *
   * @param idFactory the {@link IdFactory} to marshal data.
   * @param entityClass the {@link Class} reflecting the entity.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public IdProperty(IdFactory<?, ?, ?> idFactory, Class<E> entityClass, PropertyMetadata<Id<E>> metadata) {

    this(NAME, idFactory, entityClass, metadata);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param idFactory the {@link IdFactory} to marshal data.
   * @param entityClass the optional {@link Class} reflecting the entity.
   */
  public IdProperty(String name, IdFactory<?, ?, ?> idFactory, Class<E> entityClass) {

    this(name, idFactory, entityClass, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param idClass the {@link #getValueClass() value class} reflecting the contained {@link Id}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public IdProperty(String name, Class<? extends Id> idClass, PropertyMetadata<Id<E>> metadata) {

    super(name, (Class) idClass, metadata);
    this.entityClass = null;
    this.idFactory = null;
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param idFactory the {@link IdFactory} to marshal data.
   * @param entityClass the optional {@link Class} reflecting the entity.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  @SuppressWarnings("unchecked")
  public IdProperty(String name, IdFactory<?, ?, ?> idFactory, Class<E> entityClass, PropertyMetadata<Id<E>> metadata) {

    super(name, idClass(idFactory), metadata);
    this.entityClass = entityClass;
    this.idFactory = idFactory;
  }

  @SuppressWarnings("rawtypes")
  private static Class idClass(IdFactory<?, ?, ?> idFactory) {

    Class<? extends Id> idClass = null;
    if (idFactory != null) {
      idClass = idFactory.getIdClass();
    }
    if (idClass == null) {
      idClass = Id.class;
    }
    return idClass;
  }

  @Override
  protected void doSet(Id<E> newValue) {

    if (newValue != null) {
      if (this.entityClass == null) {
        this.entityClass = newValue.getType();
      } else {
        newValue = newValue.withType(this.entityClass);
      }
      if (this.idFactory == null) {
        this.idFactory = ((AbstractId<?, ?, ?>) newValue).getFactory();
      }
    }
    super.doSet(newValue);
  }

  /**
   * @return the {@link IdFactory}.
   */
  protected IdFactory<?, ?, ?> getIdFactory() {

    if (this.idFactory == null) {
      this.idFactory = IdFactories.get().get(getValueClass());
    }
    return this.idFactory;
  }

  @Override
  public void read(StructuredReader reader) {

    IdMarshaller.readId(reader, getIdFactory(), this.entityClass);
    super.read(reader);
  }

  @Override
  public void write(StructuredWriter writer) {

    IdMarshaller.writeId(writer, getValue());
  }

}
