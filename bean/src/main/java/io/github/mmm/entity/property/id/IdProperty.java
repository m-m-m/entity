/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.AbstractId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactories;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.id.IdMarshalling;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.AttributeReadOnly;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.object.ObjectProperty;

/**
 * {@link ObjectProperty} with {@link Id} {@link #getValue() value} pointing to an entity.
 *
 * @since 1.0.0
 */
public class IdProperty extends ObjectProperty<Id<?>> {

  /** Default {@link #getName() name} for primary key. */
  public static final String NAME = "Id";

  private Class<?> entityClass;

  private IdFactory<?, ?> idFactory;

  /**
   * The constructor.
   *
   * @param entityClass the {@link Class} reflecting the entity.
   */
  public IdProperty(Class<?> entityClass) {

    this(NAME, entityClass, null, null);
  }

  /**
   * The constructor.
   *
   * @param entityClass the {@link Class} reflecting the entity.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public IdProperty(Class<?> entityClass, PropertyMetadata<Id<?>> metadata) {

    this(NAME, entityClass, metadata, null);
  }

  /**
   * The constructor.
   *
   * @param entityClass the {@link Class} reflecting the entity.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param idFactory the {@link IdFactory} to marshal data.
   */
  public IdProperty(Class<?> entityClass, PropertyMetadata<Id<?>> metadata, IdFactory<?, ?> idFactory) {

    this(NAME, entityClass, metadata, idFactory);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} reflecting the entity.
   */
  public IdProperty(String name, Class<?> entityClass) {

    this(name, entityClass, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} reflecting the entity.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public IdProperty(String name, Class<?> entityClass, PropertyMetadata<Id<?>> metadata) {

    this(name, entityClass, metadata, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} reflecting the entity.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param idFactory the {@link IdFactory} to marshal data.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public IdProperty(String name, Class<?> entityClass, PropertyMetadata<Id<?>> metadata, IdFactory<?, ?> idFactory) {

    super(name, (Class) IdFactory.getIdClass(idFactory), metadata);
    this.entityClass = entityClass;
    this.idFactory = idFactory;
  }

  @Override
  protected void doSet(Id<?> newValue) {

    if (newValue != null) {
      if (this.entityClass == null) {
        this.entityClass = newValue.getType();
      } else {
        newValue = newValue.withType(this.entityClass);
      }
      if (this.idFactory == null) {
        this.idFactory = ((AbstractId<?, ?, ?>) newValue).getFactory();
      } else {
        assert (this.idFactory.accept(newValue));
      }
    }
    super.doSet(newValue);
  }

  /**
   * @return the {@link Id#getType() entity class}.
   */
  public Class<?> getEntityClass() {

    if (this.entityClass == null) {
      if (NAME.equals(getName())) {
        AttributeReadOnly lock = getMetadata().getLock();
        if (lock instanceof EntityBean) {
          this.entityClass = ((EntityBean) lock).getType().getJavaClass();
        }
      }
      Id<?> id = get();
      if (id != null) {
        this.entityClass = id.getType();
      }
    }
    return this.entityClass;
  }

  /**
   * @return the {@link IdFactory}.
   */
  protected IdFactory<?, ?> getIdFactory() {

    if (this.idFactory == null) {
      this.idFactory = IdFactories.get().get(getValueClass());
    }
    return this.idFactory;
  }

  private IdMarshalling getMarshalling() {

    IdFactory<?, ?> factory = getIdFactory();
    if (factory == null) {
      return IdMarshalling.get();
    } else {
      return factory.getMarshalling();
    }
  }

  @Override
  public void read(StructuredReader reader) {

    Id<?> id = getMarshalling().readObject(reader, this.entityClass);
    set(id);
  }

  @Override
  public void write(StructuredWriter writer) {

    getMarshalling().writeObject(writer, getValue());
  }

}
