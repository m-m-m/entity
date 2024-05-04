/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.object.SimpleProperty;

/**
 * {@link SimpleProperty} with {@link Id} {@link #get() value} pointing to an entity.
 *
 * @param <V> type of the {@link #get() value}.
 * @since 1.0.0
 */
public abstract class IdProperty<V extends Id<?>> extends SimpleProperty<V> {

  private V value;

  private Class<?> entityClass;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param id the initial {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  @SuppressWarnings("unchecked")
  public IdProperty(String name, V id, PropertyMetadata<V> metadata) {

    super(name, metadata);
    if (id == null) {
      this.value = (V) LongVersionId.getEmpty();
    } else {
      doSet(id);
    }
  }

  @Override
  protected V doGet() {

    return this.value;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void doSet(V newValue) {

    if (newValue != null) {
      Class<?> newEntityClass = newValue.getEntityClass();
      if (this.entityClass == null) {
        this.entityClass = newEntityClass;
      } else {
        if (newEntityClass == null) {
          newValue = (V) ((GenericId<?, ?, ?>) newValue).withEntityType(this.entityClass);
        } else if (!this.entityClass.isAssignableFrom(newEntityClass)) {
          throw new IllegalArgumentException("Cannot set ID of type " + newEntityClass.getName() + " to "
              + getClass().getSimpleName() + " " + getName() + " with incompatible type " + this.entityClass.getName());
        }
      }
    }
    this.value = newValue;
  }

  @SuppressWarnings("unchecked")
  @Override
  public V getStaticSafeValue() {

    return (V) LongVersionId.getEmpty();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Class<V> getValueClass() {

    return (Class) Id.class;
  }

  @Override
  @SuppressWarnings("unchecked")
  public V parse(String valueAsString) {

    return (V) ((GenericId<?, ?, ?>) this.value).create(valueAsString);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void readValue(StructuredReader reader) {

    V id = (V) ((GenericId<?, ?, ?>) this.value).readObject(reader);
    set(id);
  }

  @Override
  public void write(StructuredWriter writer) {

    ((GenericId<?, ?, ?>) this.value).write(writer);
  }

}
