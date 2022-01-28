/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkMapper;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.value.converter.TypeMapper;

/**
 * {@link IdProperty} for the primary key of an entity.
 *
 * @since 1.0.0
 */
public class PkProperty extends IdProperty<Id<?>> {

  /** Default {@link #getName() name} for primary key. */
  public static final String NAME = "Id";

  private TypeMapper<Id<?>, ?> typeMapper;

  /**
   * The constructor.
   *
   * @param id the initial {@link #get() value}.
   */
  public PkProperty(Id<?> id) {

    this(NAME, id, null);
  }

  /**
   * The constructor.
   *
   * @param id the initial {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public PkProperty(Id<?> id, PropertyMetadata<Id<?>> metadata) {

    this(NAME, id, metadata);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param id the initial {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public PkProperty(String name, Id<?> id, PropertyMetadata<Id<?>> metadata) {

    super(name, id, metadata);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public TypeMapper<Id<?>, ?> getTypeMapper() {

    if (this.typeMapper == null) {
      this.typeMapper = (TypeMapper) PkMapper.of(get());
    }
    return this.typeMapper;
  }

}
