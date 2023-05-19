/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.orm;

import java.util.Iterator;

import io.github.mmm.bean.ReadableBean;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.db.result.DbResult;
import io.github.mmm.entity.bean.db.result.DbResultEntry;
import io.github.mmm.entity.bean.db.result.DbResultPojo;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;

/**
 * Interface to map a {@link WritableProperty property} to the database and vice versa.
 *
 * @param <V> type of the {@link WritableProperty#get() value} to map.
 * @since 1.0.0
 */
public interface DbPropertyMapper<V> {

  /**
   * @return the {@link ReadableProperty#getName() property name}.
   */
  String getPropertyName();

  /**
   * @param javaBean the {@link ReadableBean} to map.
   * @param dbResult the {@link DbResultPojo} where to
   *        {@link DbResultPojo#addEntry(io.github.mmm.entity.bean.db.result.DbResultEntry) add} the collected
   *        {@link io.github.mmm.entity.bean.db.result.DbResultEntry database data}.
   */
  void java2db(ReadableBean javaBean, DbResultPojo dbResult);

  /**
   * @param javaProperty the {@link ReadableProperty} to map.
   * @param dbResult the {@link DbResultPojo} where to
   *        {@link DbResultPojo#addEntry(io.github.mmm.entity.bean.db.result.DbResultEntry) add} the collected
   *        {@link io.github.mmm.entity.bean.db.result.DbResultEntry database data}.
   */
  default void java2db(ReadableProperty<V> javaProperty, DbResultPojo dbResult) {

    V value = null;
    if (javaProperty != null) {
      javaProperty.get();
    }
    java2dbValue(value, dbResult);
  }

  /**
   * @param javaValue the Java value to map.
   * @param dbResult the {@link DbResultPojo} where to
   *        {@link DbResultPojo#addEntry(io.github.mmm.entity.bean.db.result.DbResultEntry) add} the collected
   *        {@link io.github.mmm.entity.bean.db.result.DbResultEntry database data}.
   */
  void java2dbValue(V javaValue, DbResultPojo dbResult);

  /**
   * @param dbEntryIterator the {@link Iterator} of the {@link DbResultEntry database entries} received from the
   *        database to convert to Java.
   * @param javaBean the {@link WritableBean} where to map the {@link DbResult} to.
   */
  void db2java(Iterator<DbResultEntry<?>> dbEntryIterator, WritableBean javaBean);

}
