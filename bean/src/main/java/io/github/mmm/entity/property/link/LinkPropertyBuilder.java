/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.link;

import java.util.function.Function;

import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.link.Link;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.builder.PropertyBuilder;
import io.github.mmm.validation.main.ValidatorBuilderObject;

/**
 * {@link PropertyBuilder} for {@link LinkProperty}.
 *
 * @param <E> the generic type of the {@link io.github.mmm.entity.bean.EntityBean entity}.
 * @since 1.0.0
 */
public final class LinkPropertyBuilder<E> extends
    PropertyBuilder<Link<E>, LinkProperty<E>, ValidatorBuilderObject<Link<E>, LinkPropertyBuilder<E>>, LinkPropertyBuilder<E>> {

  private IdFactory<?, ?, ?> idFactory;

  private Class<E> entityClass;

  private Function<Id<E>, E> resolver;

  /**
   * The constructor.
   */
  public LinkPropertyBuilder() {

    super();
  }

  /**
   * The constructor.
   *
   * @param idFactory the {@link IdFactory} to marshal data.
   * @param entityClass the {@link Class} reflecting the entity.
   */
  public LinkPropertyBuilder(IdFactory<?, ?, ?> idFactory, Class<E> entityClass) {

    super();
    this.idFactory = idFactory;
    this.entityClass = entityClass;
  }

  /**
   * @param factory the {@link IdFactory} to marshal data.
   * @return this builder itself ({@code this}) for fluent API calls.
   */
  public LinkPropertyBuilder<E> idFactory(IdFactory<?, ?, ?> factory) {

    this.idFactory = factory;
    return this;
  }

  /**
   * @param entityType the {@link Class} reflecting the entity.
   * @return this builder itself ({@code this}) for fluent API calls.
   */
  public LinkPropertyBuilder<E> entityClass(Class<E> entityType) {

    this.entityClass = entityType;
    return this;
  }

  /**
   * @param entityResolver the {@link Function} to load {@link io.github.mmm.entity.Entity entities} by their
   *        {@link Id}.
   * @return this builder itself ({@code this}) for fluent API calls.
   */
  public LinkPropertyBuilder<E> resolver(Function<Id<E>, E> entityResolver) {

    this.resolver = entityResolver;
    return this;
  }

  @Override
  protected ValidatorBuilderObject<Link<E>, LinkPropertyBuilder<E>> createValidatorBuilder() {

    return new ValidatorBuilderObject<>(this);
  }

  @Override
  protected LinkProperty<E> build(String name, PropertyMetadata<Link<E>> metadata) {

    return new LinkProperty<>(name, this.idFactory, this.entityClass, this.resolver, metadata);
  }

}
