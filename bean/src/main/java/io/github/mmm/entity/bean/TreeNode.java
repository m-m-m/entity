/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean;

import io.github.mmm.bean.AbstractInterface;
import io.github.mmm.entity.property.link.LinkProperty;

/**
 * {@link EntityBean} that represents a node in a tree. The tree hierarchy is defined by the {@link #Parent() parent}
 * relation.
 *
 * @param <T> type of the tree-nodes.
 * @since 1.0.0
 */
@AbstractInterface
public abstract interface TreeNode<T extends TreeNode<T>> extends EntityBean {

  /**
   * @return the parent node or {@code null} if this is the root node.
   */
  LinkProperty<T> Parent();

}
