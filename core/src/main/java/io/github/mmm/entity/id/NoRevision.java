package io.github.mmm.entity.id;

/**
 * Placeholder for {@link AbstractRevisionlessId#getRevision() revision} of a
 * {@link AbstractRevisionlessId#getRevision() revisionless ID}. Since {@link Void} does not implement
 * {@link Comparable} it cannot be bound here and we use this class as placeholder instead.
 */
public final class NoRevision implements Comparable<NoRevision> {

  private NoRevision() {

  }

  @Override
  public int compareTo(NoRevision o) {

    return 0;
  }

}
