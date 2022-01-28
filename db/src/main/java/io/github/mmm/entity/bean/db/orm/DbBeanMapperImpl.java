package io.github.mmm.entity.bean.db.orm;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.bean.ReadableBean;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.db.result.DbResult;
import io.github.mmm.entity.bean.db.result.DbResultPojo;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link TypeMapper} to map from {@link WritableBean} to {@link DbResult} and vice-versa.
 *
 * @param <B> type of the {@link WritableBean} to map.
 * @since 1.0.0
 */
public class DbBeanMapperImpl<B extends WritableBean> implements DbBeanMapper<B> {

  private final B bean;

  private final List<DbPropertyMapper<?>> propertyMappers;

  /**
   * The constructor.
   *
   * @param bean the {@link WritableBean} template.
   */
  public DbBeanMapperImpl(B bean) {

    super();
    this.bean = bean;
    this.propertyMappers = new ArrayList<>(bean.getProperties().size());
  }

  /**
   * @param propertyMapper the {@link DbPropertyMapper} to add.
   */
  public void add(DbPropertyMapper<?> propertyMapper) {

    assert (this.bean.getProperty(propertyMapper.getPropertyName()) != null);
    this.propertyMappers.add(propertyMapper);
  }

  @Override
  public DbResult java2db(B source) {

    DbResultPojo dbResult = new DbResultPojo();
    for (DbPropertyMapper<?> mapper : this.propertyMappers) {
      mapper.java2db(source, dbResult);
    }
    return dbResult;
  }

  @Override
  public B db2java(DbResult dbResult) {

    B resultBean = ReadableBean.copy(this.bean, false);
    for (DbPropertyMapper<?> mapper : this.propertyMappers) {
      mapper.db2java(dbResult.iterator(), resultBean);
    }
    return resultBean;
  }

}
