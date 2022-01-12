/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides the API for (persistent) entities.
 */
module io.github.mmm.entity {

  requires static io.github.mmm.marshall;

  requires transitive io.github.mmm.value.converter;

  exports io.github.mmm.entity;

  exports io.github.mmm.entity.id;

  exports io.github.mmm.entity.link;
}
