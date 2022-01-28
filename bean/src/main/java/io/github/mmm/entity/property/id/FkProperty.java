/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.IdMapper;
import io.github.mmm.entity.id.Id;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.value.converter.TypeMapper;

/**
 * {@link IdProperty} for a foreign key to another entity.
 *
 * @param <E> type of the referenced {@link EntityBean}.
 * @since 1.0.0
 */
public class FkProperty<E extends EntityBean> extends IdProperty<Id<E>> {

  private TypeMapper<Id<E>, ?> typeMapper;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param id the initial {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public FkProperty(String name, Id<E> id, PropertyMetadata<Id<E>> metadata) {

    super(name, id, metadata);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public TypeMapper<Id<E>, ?> getTypeMapper() {

    if (this.typeMapper == null) {
      this.typeMapper = (TypeMapper) IdMapper.of(get());
    }
    return this.typeMapper;
  }

}
