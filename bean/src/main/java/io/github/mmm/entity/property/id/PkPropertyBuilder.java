/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.id;

import io.github.mmm.entity.id.Id;
import io.github.mmm.property.AttributeReadOnly;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.builder.PropertyBuilder;
import io.github.mmm.validation.main.ValidatorBuilderObject;

/**
 * {@link PropertyBuilder} for {@link IdProperty}.
 *
 * @since 1.0.0
 */
public final class PkPropertyBuilder
    extends PropertyBuilder<Id<?>, PkProperty, ValidatorBuilderObject<Id<?>, PkPropertyBuilder>, PkPropertyBuilder> {

  private Id<?> id;

  /**
   * The constructor.
   *
   * @param lock the {@link #getLock() lock}.
   */
  public PkPropertyBuilder(AttributeReadOnly lock) {

    super(lock);
  }

  /**
   * The constructor.
   *
   * @param lock the {@link #getLock() lock}.
   * @param idTemplate the {@link Class} reflecting the entity.
   */
  public PkPropertyBuilder(AttributeReadOnly lock, Id<?> idTemplate) {

    super(lock);
    this.id = idTemplate;
  }

  @Override
  protected ValidatorBuilderObject<Id<?>, PkPropertyBuilder> createValidatorBuilder() {

    return new ValidatorBuilderObject<>(this);
  }

  @Override
  protected PkProperty build(String name, PropertyMetadata<Id<?>> metadata) {

    return new PkProperty(name, this.id, metadata);
  }

}
