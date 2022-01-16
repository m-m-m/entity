package io.github.mmm.entity.bean.db.orm;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.db.naming.DbNamingStrategy;
import io.github.mmm.entity.bean.db.result.DbResultEntryObjectWithDeclaration;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.ProjectionProperty;
import io.github.mmm.value.CriteriaSelection;
import io.github.mmm.value.ReadablePath;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link Orm}.
 *
 * @since 1.0.0
 */
public class OrmImpl implements Orm {

  private final TypeMapping typeMapping;

  private final DbNamingStrategy namingStrategy;

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   * @param namingStrategy the {@link DbNamingStrategy}.
   */
  public OrmImpl(TypeMapping typeMapping, DbNamingStrategy namingStrategy) {

    super();
    this.typeMapping = typeMapping;
    this.namingStrategy = namingStrategy;
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <B extends WritableBean> DbBeanMapper<B> createBeanMapping(B bean,
      Iterable<? extends WritableProperty<?>> properties) {

    DbBeanMapperImpl<B> beanMapper = new DbBeanMapperImpl<>(bean);
    for (WritableProperty<?> property : properties) {
      if (!property.isReadOnly()) {
        DbSegmentMapper valueMapper = createSegmentMapper(property);
        DbPropertyMapper propertyMapper = new DbPropertyMapperImpl<>(property.getName(), valueMapper);
        beanMapper.add(propertyMapper);
      }
    }
    return beanMapper;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <B extends WritableBean> DbBeanMapper<B> createBeanMappingProjection(B bean,
      Iterable<? extends ProjectionProperty<?>> properties) {

    DbBeanMapperImpl<B> beanMapper = new DbBeanMapperImpl<>(bean);
    for (ProjectionProperty<?> projectionProperty : properties) {
      WritableProperty property = (WritableProperty) projectionProperty.getProperty();
      String columnName = this.namingStrategy.getColumnName(property);
      DbSegmentMapper valueMapper = createSegmentMapper(projectionProperty.getSelection(), columnName,
          property.getValueClass());
      ReadablePath parent = property.parentPath();
      if (parent != null) {
        parent = parent.parentPath();
        if (parent instanceof WritableProperty) {

        }
      }
      DbPropertyMapper propertyMapper = new DbPropertyMapperImpl<>(property.getName(), valueMapper);
      beanMapper.add(propertyMapper);
    }
    return beanMapper;
  }

  private <V> DbSegmentMapper<V, ?> createSegmentMapper(WritableProperty<V> property) {

    String columnName = this.namingStrategy.getColumnName(property);
    return createSegmentMapper(property, columnName, property.getValueClass());
  }

  private <V> DbSegmentMapper<V, ?> createSegmentMapper(CriteriaSelection<?> selection, String columnName,
      Class<V> valueClass) {

    TypeMapper<V, ?> typeMapper = this.typeMapping.getTypeMapper(valueClass);
    if (typeMapper == null) {
      throw new IllegalStateException("No type mapping could be found for type " + valueClass + " and selection "
          + selection + "[" + selection.getClass().getSimpleName() + "]");
    }
    return createSegmentMapper(selection, columnName, typeMapper);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <V> DbSegmentMapper<V, ?> createSegmentMapper(CriteriaSelection<?> selection, String columnName,
      TypeMapper<V, ?> typeMapper) {

    DbSegmentMapper<V, ?> nextSegment = null;
    TypeMapper<V, ?> next = typeMapper.next();
    if (next != null) {
      nextSegment = createSegmentMapper(selection, columnName, next);
    }
    String newColumnName = typeMapper.mapName(columnName);
    if (typeMapper.hasDeclaration()) {
      DbResultEntryObjectWithDeclaration entry = new DbResultEntryObjectWithDeclaration<>(selection, null,
          newColumnName, typeMapper.getDeclaration());
      return new DbSegmentMapper<>(typeMapper, entry, nextSegment);
    } else {
      DbSegmentMapper child = createSegmentMapper(selection, newColumnName, typeMapper.getTargetType());
      return new DbSegmentMapper<>(typeMapper, child, nextSegment);
    }
  }

}
