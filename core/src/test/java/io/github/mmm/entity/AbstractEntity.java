package io.github.mmm.entity;

import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.LongVersionId;

/**
 * Abstract base implementation of {@link Entity}. Intentionally not in {@code src/main/java} as we want to propagate
 * the usage of EntityBean and avoid such stupid boiler-plate code like this.
 */
public abstract class AbstractEntity implements Entity {

  private Id<?> id;

  /**
   * The constructor.
   */
  public AbstractEntity() {

    super();
    this.id = LongVersionId.getEmpty(getClass());
  }

  @Override
  public Id<?> getId() {

    return this.id;
  }

  @Override
  public void setId(Id<?> id) {

    this.id = id;
  }

  @Override
  public String toString() {

    return getClass().getName() + "@" + this.id;
  }

}
