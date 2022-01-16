package io.github.mmm.entity.bean.db.result;

import java.util.Iterator;

import io.github.mmm.base.collection.ArrayIterator;
import io.github.mmm.entity.bean.db.statement.select.SelectFrom;

/**
 * Implementation of {@link DbResult} as simple object.
 *
 * @see SelectFrom
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DbResultObject implements DbResult {

  private final DbResultEntryObject[] entries;

  /**
   * The constructor.
   *
   * @param entries the {@link #getEntry(int) entries}.
   */
  public DbResultObject(DbResultEntryObject... entries) {

    super();
    this.entries = entries;
  }

  @Override
  public <V> DbResultEntry<V> getEntry(int i) {

    return this.entries[i];
  }

  @Override
  public int getSize() {

    return this.entries.length;
  }

  @Override
  public Iterator<DbResultEntry<?>> iterator() {

    return new ArrayIterator<>(this.entries);
  }

}
