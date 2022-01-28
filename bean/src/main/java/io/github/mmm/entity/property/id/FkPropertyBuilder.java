/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.property.AttributeReadOnly;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.builder.PropertyBuilder;
import io.github.mmm.validation.main.ValidatorBuilderObject;

/**
 * {@link PropertyBuilder} for {@link IdProperty}.
 *
 * @param <E> type of the referenced {@link EntityBean}.
 * @since 1.0.0
 */
public final class FkPropertyBuilder<E extends EntityBean> extends
    PropertyBuilder<Id<E>, FkProperty<E>, ValidatorBuilderObject<Id<E>, FkPropertyBuilder<E>>, FkPropertyBuilder<E>> {

  private Id<E> id;

  /**
   * The constructor.
   *
   * @param lock the {@link #getLock() lock}.
   */
  public FkPropertyBuilder(AttributeReadOnly lock) {

    super(lock);
  }

  /**
   * The constructor.
   *
   * @param lock the {@link #getLock() lock}.
   * @param idTemplate the {@link Class} reflecting the entity.
   */
  public FkPropertyBuilder(AttributeReadOnly lock, Id<E> idTemplate) {

    super(lock);
    this.id = idTemplate;
  }

  @Override
  protected ValidatorBuilderObject<Id<E>, FkPropertyBuilder<E>> createValidatorBuilder() {

    return new ValidatorBuilderObject<>(this);
  }

  @Override
  protected FkProperty<E> build(String name, PropertyMetadata<Id<E>> metadata) {

    return new FkProperty<>(name, this.id, metadata);
  }

}
