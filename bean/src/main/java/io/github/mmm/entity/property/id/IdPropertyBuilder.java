/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.property.AttributeReadOnly;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.builder.PropertyBuilder;
import io.github.mmm.validation.main.ValidatorBuilderObject;

/**
 * {@link PropertyBuilder} for {@link IdProperty}.
 *
 * @since 1.0.0
 */
public final class IdPropertyBuilder
    extends PropertyBuilder<Id<?>, IdProperty, ValidatorBuilderObject<Id<?>, IdPropertyBuilder>, IdPropertyBuilder> {

  private IdFactory<?, ?> idFactory;

  private Class<?> entityClass;

  /**
   * The constructor.
   *
   * @param lock the {@link #getLock() lock}.
   */
  public IdPropertyBuilder(AttributeReadOnly lock) {

    super(lock);
  }

  /**
   * The constructor.
   *
   * @param lock the {@link #getLock() lock}.
   * @param entityClass the {@link Class} reflecting the entity.
   */
  public IdPropertyBuilder(AttributeReadOnly lock, Class<?> entityClass) {

    super(lock);
    this.entityClass = entityClass;
  }

  /**
   * @param factory the {@link IdFactory} to marshal data.
   * @return this builder itself ({@code this}) for fluent API calls.
   */
  public IdPropertyBuilder idFactory(IdFactory<?, ?> factory) {

    this.idFactory = factory;
    return this;
  }

  /**
   * @param entityType the {@link Class} reflecting the entity.
   * @return this builder itself ({@code this}) for fluent API calls.
   */
  public IdPropertyBuilder entityClass(Class<?> entityType) {

    this.entityClass = entityType;
    return this;
  }

  @Override
  protected ValidatorBuilderObject<Id<?>, IdPropertyBuilder> createValidatorBuilder() {

    return new ValidatorBuilderObject<>(this);
  }

  @Override
  protected IdProperty build(String name, PropertyMetadata<Id<?>> metadata) {

    return new IdProperty(name, this.entityClass, metadata, this.idFactory);
  }

}
