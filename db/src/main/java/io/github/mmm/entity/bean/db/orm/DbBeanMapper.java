package io.github.mmm.entity.bean.db.orm;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.db.result.DbResult;

/**
 * Interface to map a {@link WritableBean} to the database and vice-versa.
 *
 * @param <B> type of the {@link WritableBean} to map.
 * @since 1.0.0
 */
public interface DbBeanMapper<B extends WritableBean> {

  /**
   * @param javaBean the {@link WritableBean} to map.
   * @return the mapped {@link DbResult}.
   */
  DbResult java2db(B javaBean);

  /**
   * @param dbResult the {@link DbResult} to map.
   * @return the mapped Java {@link WritableBean bean}.
   */
  B db2java(DbResult dbResult);

}
