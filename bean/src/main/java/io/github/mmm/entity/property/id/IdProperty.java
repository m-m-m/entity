/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.object.ObjectProperty;
import io.github.mmm.property.object.SimpleProperty;

/**
 * {@link ObjectProperty} with {@link Id} {@link #get() value} pointing to an entity.
 *
 * @since 1.0.0
 */
public class IdProperty extends SimpleProperty<Id<?>> {

  /** Default {@link #getName() name} for primary key. */
  public static final String NAME = "Id";

  private GenericId<?, ?, ?> value;

  /**
   * The constructor.
   *
   * @param id the initial {@link #get() value}.
   */
  public IdProperty(Id<?> id) {

    this(NAME, id, null);
  }

  /**
   * The constructor.
   *
   * @param id the initial {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public IdProperty(Id<?> id, PropertyMetadata<Id<?>> metadata) {

    this(NAME, id, metadata);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param id the initial {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  @SuppressWarnings("rawtypes")
  public IdProperty(String name, Id<?> id, PropertyMetadata<Id<?>> metadata) {

    super(name, metadata);
    assert (id != null);
    this.value = (GenericId) id;
  }

  @Override
  protected Id<?> doGet() {

    return this.value;
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected void doSet(Id<?> newValue) {

    GenericId<?, ?, ?> newId = (GenericId) newValue;
    if (this.value != null) {
      if (newId == null) {
        newId = this.value.withIdAndVersion(null, null);
      } else {
        Class<?> newEntityType = newId.getEntityType();
        Class<?> entityType = this.value.getEntityType();
        if (newEntityType == null) {
          newId = newId.withEntityType(entityType);
        } else if (entityType == null) {
          assert (entityType == newEntityType) : "Can not change entity type of primary key!";
        }
      }
    }
    this.value = newId;
  }

  @Override
  public Id<?> getSafe() {

    return get();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Class<Id<?>> getValueClass() {

    return (Class) Id.class;
  }

  @Override
  public Id<?> parse(String valueAsString) {

    return this.value.create(valueAsString);
  }

  @Override
  public void read(StructuredReader reader) {

    GenericId<?, ?, ?> id = this.value.readObject(reader);
    set(id);
  }

  @Override
  public void write(StructuredWriter writer) {

    this.value.write(writer);
  }

}
