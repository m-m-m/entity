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
   * @param idClass the {@link Class} of the {@link Id} implementation.
   * @param entityClass the {@link Class} reflecting the entity.
   */
  @SuppressWarnings("rawtypes")
  public IdProperty(Class<? extends Id> idClass, Class<E> entityClass) {

    this(NAME, idClass, null, entityClass, null);
  }

  /**
   * The constructor.
   *
   * @param idClass the {@link Class} of the {@link Id} implementation.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the {@link Class} reflecting the entity.
   */
  @SuppressWarnings("rawtypes")
  public IdProperty(Class<? extends Id> idClass, PropertyMetadata<Id<E>> metadata, Class<E> entityClass) {

    this(NAME, idClass, metadata, entityClass, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param idClass the {@link Class} of the {@link Id} implementation.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the {@link Class} reflecting the entity.
   */
  @SuppressWarnings("rawtypes")
  public IdProperty(String name, Class<? extends Id> idClass, PropertyMetadata<Id<E>> metadata, Class<E> entityClass) {

    this(name, idClass, metadata, entityClass, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param idClass the {@link Class} of the {@link Id} implementation.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param entityClass the optional {@link Class} reflecting the entity.
   * @param idFactory the optional {@link IdFactory}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public IdProperty(String name, Class<? extends Id> idClass, PropertyMetadata<Id<E>> metadata, Class<E> entityClass,
      IdFactory<?, ?, ?> idFactory) {

    super(name, (Class) idClass, metadata);
    this.entityClass = entityClass;
    this.idFactory = idFactory;
  }

  @Override
  protected void doSetValue(Id<E> newValue) {

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
    super.doSetValue(newValue);
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
