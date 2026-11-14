/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id.generator;

import java.util.UUID;

import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkIdUuid;

/// [IdGenerator] based on [UUID] for [PkIdUuid].
///
/// @since 1.0.0
public class UuidIdGenerator implements IdGenerator {

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <E> Id<E> generate(Id<E> template) {

    Class<E> entityType = template.getEntityClass();
    UUID pk = UUID.randomUUID();
    GenericId gid = (GenericId) template;
    return gid.create(entityType, pk, null).updateRevision();
  }

}
