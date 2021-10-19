/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides Data-Access-Object (DAO) API for {@code mmm-entity-bean}.
 */
module io.github.mmm.entity.dao {

  requires transitive io.github.mmm.entity.bean;

  requires transitive reactor.core;

  requires transitive org.reactivestreams;

  exports io.github.mmm.entity.bean.dao;

}
