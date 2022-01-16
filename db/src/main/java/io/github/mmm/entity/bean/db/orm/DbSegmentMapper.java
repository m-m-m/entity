package io.github.mmm.entity.bean.db.orm;

import java.util.Iterator;

import io.github.mmm.base.lang.Builder;
import io.github.mmm.entity.bean.db.result.DbResultEntry;
import io.github.mmm.entity.bean.db.result.DbResultEntryObjectWithDeclaration;
import io.github.mmm.entity.bean.db.result.DbResultPojo;
import io.github.mmm.value.converter.TypeMapper;

/**
 * A node of the mapping tree for mapping from Java to database and vice versa.
 *
 * @param <S> the {@link TypeMapper#getSourceType() source type}.
 * @param <T> the {@link TypeMapper#getTargetType() target type}.
 * @since 1.0.0
 */
public final class DbSegmentMapper<S, T> {

  /** The {@link TypeMapper} to wrap. */
  private final TypeMapper<S, T> typeMapper;

  private final DbResultEntryObjectWithDeclaration<T> entry;

  private final DbSegmentMapper<T, ?> child;

  /** The {@link TypeMapper#next()} {@link DbSegmentMapper}. */
  private final DbSegmentMapper<S, ?> next;

  /**
   * The constructor.
   *
   * @param typeMapper the {@link #getTypeMapper() type mapper}.
   * @param entry the {@link #getEntry() entry}.
   * @param next the {@link #getNext() next}.
   */
  public DbSegmentMapper(TypeMapper<S, T> typeMapper, DbResultEntryObjectWithDeclaration<T> entry,
      DbSegmentMapper<S, ?> next) {

    this(typeMapper, entry, null, next);
    assert (typeMapper.getDeclaration() == entry.getDeclaration());
  }

  /**
   * The constructor.
   *
   * @param typeMapper the {@link #getTypeMapper() type mapper}.
   * @param child the {@link #getChild() child}.
   * @param next the {@link #getNext() next}.
   */
  public DbSegmentMapper(TypeMapper<S, T> typeMapper, DbSegmentMapper<T, ?> child, DbSegmentMapper<S, ?> next) {

    this(typeMapper, null, child, next);
  }

  /**
   * The constructor.
   *
   * @param typeMapper the {@link #getTypeMapper() type mapper}.
   * @param entry the {@link #getEntry() entry}.
   * @param child the {@link #getChild() child}.
   * @param next the {@link #getNext() next}.
   */
  private DbSegmentMapper(TypeMapper<S, T> typeMapper, DbResultEntryObjectWithDeclaration<T> entry,
      DbSegmentMapper<T, ?> child, DbSegmentMapper<S, ?> next) {

    super();
    this.typeMapper = typeMapper;
    this.entry = entry;
    this.child = child;
    if ((this.child == null) == (this.entry == null)) {
      throw new IllegalArgumentException(); // exactly on of child or entry must not be null
    }
    this.next = next;
  }

  /**
   * @return the {@link TypeMapper} to wrap. May not be {@code null}.
   */
  protected TypeMapper<S, T> getTypeMapper() {

    return this.typeMapper;
  }

  /**
   * @return entry the {@link DbResultEntryObjectWithDeclaration} to use as template. Will be {@code null} if this is no
   *         leaf node.
   */
  protected DbResultEntryObjectWithDeclaration<T> getEntry() {

    return this.entry;
  }

  /**
   * @return child the {@link DbSegmentMapper} to chain as child. Will be {@code null} if leaf node.
   */
  protected DbSegmentMapper<T, ?> getChild() {

    return this.child;
  }

  /**
   * @return the {@link TypeMapper#next() siblings} as {@link DbSegmentMapper}s. Will be {@code null} if this instance
   *         itself is already a sibling of another instance.
   */
  protected DbSegmentMapper<S, ?> getNext() {

    return this.next;
  }

  /**
   * @param javaValue the Java value to convert and send to the database.
   * @param dbResult the {@link DbResultPojo} where to {@link DbResultPojo#addEntry(DbResultEntry) add} the collected
   *        {@link DbResultEntry database data}.
   */
  public void java2db(S javaValue, DbResultPojo dbResult) {

    T dbValue;
    if (javaValue == null) {
      dbValue = this.typeMapper.toTargetNull();
    } else {
      dbValue = this.typeMapper.toTarget(javaValue);
    }
    if (this.child != null) {
      this.child.java2db(dbValue, dbResult);
    } else {
      dbResult.addEntry(this.entry.withValue(dbValue));
    }
    DbSegmentMapper<S, ?> current = this.next;
    while (current != null) {
      current.java2db(javaValue, dbResult);
      current = current.next;
    }
  }

  /**
   * @param dbEntryIterator the {@link Iterator} of the {@link DbResultEntry database entries} received from the
   *        database to convert to Java.
   * @param javaBuilder the optional {@link Builder} to build the converted Java value. May be {@code null}.
   * @return the Java value.
   */
  @SuppressWarnings("unchecked")
  public S db2java(Iterator<DbResultEntry<?>> dbEntryIterator, Builder<S> javaBuilder) {

    T dbValue;
    if (this.child != null) {
      dbValue = this.child.db2java(dbEntryIterator, null);
    } else {
      DbResultEntry<?> dbEntry = dbEntryIterator.next();
      assert (dbEntry.getDbName().equals(this.entry.getDbName()));
      dbValue = (T) dbEntry.getValue();
    }
    S source;
    if (javaBuilder == null) {
      if (this.next == null) {
        source = this.typeMapper.toSource(dbValue);
      } else {
        javaBuilder = this.typeMapper.sourceBuilder();
        this.typeMapper.with(javaBuilder, dbValue);
        DbSegmentMapper<S, ?> current = this.next;
        while (current != null) {
          current.db2java(dbEntryIterator, javaBuilder);
          current = current.next;
        }
        source = javaBuilder.build();
      }
    } else {
      this.typeMapper.with(javaBuilder, dbValue);
      source = null;
    }
    return source;
  }

}
