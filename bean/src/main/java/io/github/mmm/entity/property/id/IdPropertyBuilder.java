/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.builder.PropertyBuilder;
import io.github.mmm.property.builder.PropertyBuilders;
import io.github.mmm.validation.main.ValidatorBuilderObject;

/**
 * {@link PropertyBuilder} for {@link IdProperty}.
 *
 * @param <E> the generic type of the {@link io.github.mmm.entity.bean.EntityBean entity}.
 * @since 1.0.0
 */
public final class IdPropertyBuilder<E> extends
    PropertyBuilder<Id<E>, IdProperty<E>, ValidatorBuilderObject<Id<E>, IdPropertyBuilder<E>>, IdPropertyBuilder<E>> {

  private IdFactory<?, ?, ?> idFactory;

  private Class<E> entityClass;

  /**
   * The constructor.
   *
   * @param parent the {@link PropertyBuilders}.
   */
  public IdPropertyBuilder(PropertyBuilders parent) {

    super(parent);
  }

  /**
   * The constructor.
   *
   * @param parent the {@link PropertyBuilders}.
   * @param idFactory the {@link IdFactory} to marshal data.
   * @param entityClass the {@link Class} reflecting the entity.
   */
  public IdPropertyBuilder(PropertyBuilders parent, IdFactory<?, ?, ?> idFactory, Class<E> entityClass) {

    super(parent);
    this.idFactory = idFactory;
    this.entityClass = entityClass;
  }

  /**
   * @param factory the {@link IdFactory} to marshal data.
   * @return this builder itself ({@code this}) for fluent API calls.
   */
  public IdPropertyBuilder<E> idFactory(IdFactory<?, ?, ?> factory) {

    this.idFactory = factory;
    return this;
  }

  /**
   * @param entityType the {@link Class} reflecting the entity.
   * @return this builder itself ({@code this}) for fluent API calls.
   */
  public IdPropertyBuilder<E> entityClass(Class<E> entityType) {

    this.entityClass = entityType;
    return this;
  }

  @Override
  protected ValidatorBuilderObject<Id<E>, IdPropertyBuilder<E>> createValidatorBuilder() {

    return new ValidatorBuilderObject<>(this);
  }

  @Override
  protected IdProperty<E> build(String name, PropertyMetadata<Id<E>> metadata) {

    return new IdProperty<>(name, this.idFactory, this.entityClass, metadata);
  }

}
