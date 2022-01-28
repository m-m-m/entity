/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.builder;

import static io.github.mmm.property.builder.PropertyBuildersHelper.accept;
import static io.github.mmm.property.builder.PropertyBuildersHelper.builder;
import static io.github.mmm.property.builder.PropertyBuildersHelper.get;

import java.util.function.Function;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongVersionId;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.property.id.FkProperty;
import io.github.mmm.entity.property.id.FkPropertyBuilder;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.entity.property.id.PkPropertyBuilder;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.entity.property.link.LinkPropertyBuilder;
import io.github.mmm.property.AttributeReadOnly;
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
   * @param idTemplate the {@link Id} to use as {@link Id#isEmpty() template}.
   * @return a new {@link PkPropertyBuilder}.
   */
  default PkPropertyBuilder newPk() {

    return newPk(null);
  }

  /**
   * @param idTemplate the {@link Id} to use as {@link Id#isEmpty() template}.
   * @return a new {@link PkPropertyBuilder}.
   */
  default PkPropertyBuilder newPk(Id<?> idTemplate) {

    return builder(new PkPropertyBuilder(getLock(), safeId(idTemplate)), this);
  }

  /**
   * @param name the {@link Property#getName() property name}.
   * @param idTemplate the {@link Id} to use as {@link Id#isEmpty() template}.
   * @return a new {@link PkProperty}.
   */
  default PkProperty newPk(String name, Id<?> idTemplate) {

    return get(name, this, metadata -> accept(new PkProperty(name, safeId(idTemplate), metadata), this));
  }

  /**
   * @param <E> type of the referenced {@link EntityBean}.
   * @param idTemplate the {@link Id} to use as {@link Id#isEmpty() template}.
   * @return a new {@link FkPropertyBuilder}.
   */
  default <E extends EntityBean> FkPropertyBuilder<E> newFk() {

    return newFk(null);
  }

  /**
   * @param <E> type of the referenced {@link EntityBean}.
   * @param idTemplate the {@link Id} to use as {@link Id#isEmpty() template}.
   * @return a new {@link PkPropertyBuilder}.
   */
  default <E extends EntityBean> FkPropertyBuilder<E> newFk(Id<E> idTemplate) {

    return builder(new FkPropertyBuilder<>(getLock(), safeId(idTemplate)), this);
  }

  /**
   * @param <E> type of the referenced {@link EntityBean}.
   * @param name the {@link Property#getName() property name}.
   * @param idTemplate the {@link Id} to use as {@link Id#isEmpty() template}.
   * @return a new {@link PkProperty}.
   */
  default <E extends EntityBean> FkProperty<E> newFk(String name, Id<E> idTemplate) {

    return get(name, this, metadata -> accept(new FkProperty<>(name, safeId(idTemplate), metadata), this));
  }

  @SuppressWarnings("rawtypes")
  private <E> Id<E> safeId(Id<E> id) {

    if (id == null) {
      id = LongVersionId.getEmpty();
    }
    AttributeReadOnly lock = getLock();
    if (lock instanceof EntityBean) {
      Class<?> entityType = ((EntityBean) lock).getType().getJavaClass();
      id = ((GenericId) id).withEntityType(entityType);
    }
    return id;
  }

  /**
   * @param <E> type of the referenced {@link Entity}.
   * @param entityClass the {@link Class} reflecting the referenced {@link Entity}.
   * @return a new {@link LinkPropertyBuilder}.
   */
  default <E extends EntityBean> LinkPropertyBuilder<E> newLink(Class<E> entityClass) {

    return builder(new LinkPropertyBuilder<>(getLock(), entityClass), this);
  }

  /**
   * @param <E> type of the referenced {@link Entity}.
   * @param name the {@link Property#getName() property name}.
   * @param entityClass the {@link Class} reflecting the referenced {@link Entity}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   * @return a new {@link LinkProperty}.
   */
  default <E extends EntityBean> LinkProperty<E> newLink(String name, Class<E> entityClass,
      Function<Id<E>, E> resolver) {

    return get(name, this, metadata -> accept(new LinkProperty<E>(name, entityClass, metadata, resolver), this));
  }

}
