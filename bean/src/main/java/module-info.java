/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
@SuppressWarnings("rawtypes") //
module net.sf.mmm.entity.bean {

  requires transitive net.sf.mmm.entity;

  requires transitive net.sf.mmm.bean;

  provides net.sf.mmm.property.factory.PropertyFactory //
      with net.sf.mmm.entity.property.id.PropertyFactoryId, //
      net.sf.mmm.entity.property.link.PropertyFactoryLink, //
      net.sf.mmm.entity.property.linklist.PropertyFactoryLinkList, //
      net.sf.mmm.entity.property.linkset.PropertyFactoryLinkSet; //

  exports net.sf.mmm.entity.bean;

  exports net.sf.mmm.entity.property.id;

  exports net.sf.mmm.entity.property.link;

  exports net.sf.mmm.entity.property.linklist;

  exports net.sf.mmm.entity.property.linkset;

}
