/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.generator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkIdString;

/// [IdGenerator] for [PkIdString] using [UUID] as fallback.
///
/// @since 1.0.0
public class IdGeneratorString implements IdGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(IdGeneratorString.class);

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <E> Id<E> generate(Id<E> template) {

    Class<E> entityType = template.getEntityClass();
    LOG.warn("Generating primary key of type String for {} using UUID. Please note that if you want to auto-generate primary keys, avoid using String. "
        + "The type String is preferred for non-technical primary keys that will be assigned manually.", entityType);
    UUID pk = UUID.randomUUID();
    GenericId gid = (GenericId) template;
    return gid.create(entityType, pk.toString(), null).updateRevision();
  }

}
