package io.github.mmm.entity;

/**
 * Dummy implementation of {@link Entity} for testing.
 */
public class DummyEntity extends AbstractEntity {

  private String name;

  /**
   * @return the name.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getName()}.
   */
  public void setName(String name) {

    this.name = name;
  }

}
