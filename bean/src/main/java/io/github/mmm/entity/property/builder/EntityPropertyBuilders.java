/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.builder;

import static io.github.mmm.property.builder.PropertyBuildersHelper.accept;
import static io.github.mmm.property.builder.PropertyBuildersHelper.builder;
import static io.github.mmm.property.builder.PropertyBuildersHelper.get;

import java.util.function.Function;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.id.IdPropertyBuilder;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.entity.property.link.LinkPropertyBuilder;
import io.github.mmm.property.Property;
import io.github.mmm.property.builder.PropertyBuilders;

/**
 * Extends {@link PropertyBuilders} for entity specific properties.
 *
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public interface EntityPropertyBuilders extends PropertyBuilders {

  /**
   * @param <E> type of the referenced {@link Entity}.
   * @param entityClass the {@link Class} reflecting the referenced {@link Entity}.
   * @return a new {@link IdPropertyBuilder}.
   */
  default <E extends Entity> IdPropertyBuilder<E> newId(Class<E> entityClass) {

    return builder(new IdPropertyBuilder<>(getLock(), entityClass), this);
  }

  /**
   * @param <E> type of the referenced {@link Entity}.
   * @param name the {@link Property#getName() property name}.
   * @param entityClass the {@link Class} reflecting the referenced {@link Entity}.
   * @return a new {@link IdProperty}.
   */
  default <E extends Entity> IdProperty<E> newId(String name, Class<E> entityClass) {

    return get(name, this, metadata -> accept(new IdProperty<>(name, entityClass, metadata), this));
  }

  /**
   * @param <E> type of the referenced {@link Entity}.
   * @param idFactory the {@link IdFactory} to marshal data.
   * @param entityClass the {@link Class} reflecting the referenced {@link Entity}.
   * @return a new {@link LinkPropertyBuilder}.
   */
  default <E extends Entity> LinkPropertyBuilder<E> newLink(Class<E> entityClass) {

    return builder(new LinkPropertyBuilder<>(getLock(), entityClass), this);
  }

  /**
   * @param <E> type of the referenced {@link Entity}.
   * @param name the {@link Property#getName() property name}.
   * @param entityClass the {@link Class} reflecting the referenced {@link Entity}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   * @return a new {@link LinkProperty}.
   */
  default <E extends Entity> LinkProperty<E> newLink(String name, Class<E> entityClass, Function<Id<E>, E> resolver) {

    return get(name, this, metadata -> accept(new LinkProperty<>(name, entityClass, metadata, resolver), this));
  }

}
