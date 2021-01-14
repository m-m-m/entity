/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides entity-beans based on {@code mmm-bean} and {@code mmm-entity}.
 *
 * @provides io.github.mmm.property.factory.PropertyFactory
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.entity.bean {

  requires transitive io.github.mmm.entity;

  requires transitive io.github.mmm.bean.factory;

  provides io.github.mmm.property.factory.PropertyFactory //
      with io.github.mmm.entity.property.id.PropertyFactoryId, //
      io.github.mmm.entity.property.link.PropertyFactoryLink //
  ;

  exports io.github.mmm.entity.bean;

  exports io.github.mmm.entity.property.builder;

  exports io.github.mmm.entity.property.id;

  exports io.github.mmm.entity.property.link;

}
