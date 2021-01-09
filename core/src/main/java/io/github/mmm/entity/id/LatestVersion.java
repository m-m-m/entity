/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.id;

/**
 * Placeholder class for latest {@link Id#getVersion() version}.
 *
 * @see AbstractLatestId
 */
public final class LatestVersion implements Comparable<LatestVersion> {

  private LatestVersion() {

  }

  @Override
  public int compareTo(LatestVersion o) {

    return 0;
  }

}
