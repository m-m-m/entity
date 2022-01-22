package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.bean.property.BeanProperty;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.PropertyPathParser;
import io.github.mmm.scanner.CharStreamScanner;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.ReadablePath;

/**
 * Implementation of {@link PropertyPathParser} that resolves {@link PropertyPath paths} directly against a given
 * {@link EntityBean} without any alias.
 *
 * @since 1.0.0
 */
class EntityPathParser implements PropertyPathParser {

  private final EntityBean entity;

  EntityPathParser(EntityBean entity) {

    super();
    this.entity = entity;
  }

  @Override
  public PropertyPath<?> parse(CharStreamScanner scanner, String segment) {

    return parsePath(scanner, this.entity, segment);
  }

  static WritableProperty<?> resolvePath(WritableBean bean, ReadablePath path) {

    return resolvePath(bean, path, false);
  }

  static WritableProperty<?> resolvePath(WritableBean bean, ReadablePath path, boolean withoutAlias) {

    ReadablePath parent = path.parentPath();
    if (parent == null) {
      if (withoutAlias) {
        return bean.getRequiredProperty(path.pathSegment());
      } else {
        return null;
      }
    } else {
      String segment = path.pathSegment();
      WritableProperty<?> property = resolvePath(bean, parent, withoutAlias);
      if (property == null) {
        assert (!withoutAlias);
        return bean.getRequiredProperty(segment);
      } else {
        return traverseProperty(property, segment);
      }
    }
  }

  static WritableProperty<?> parsePath(CharStreamScanner scanner, WritableBean bean) {

    return parsePath(scanner, bean, null);
  }

  static WritableProperty<?> parsePath(CharStreamScanner scanner, WritableBean bean, String segment) {

    WritableProperty<?> p = null;
    do {
      if (segment == null) {
        segment = PropertyPathParser.parseSegment(scanner);
      }
      if (p == null) {
        p = bean.getRequiredProperty(segment);
      } else {
        p = traverseProperty(p, segment);
      }
      segment = null;
    } while (scanner.expectOne('.'));
    return p;
  }

  static WritableProperty<?> traverseProperty(WritableProperty<?> property, String segment) {

    WritableBean childBean = null;
    if (property instanceof BeanProperty) {
      childBean = ((BeanProperty<?>) property).get();
    }
    if (childBean == null) {
      throw new IllegalArgumentException("Cannot traverse from property " + property.path() + " to " + segment);
    }
    return childBean.getRequiredProperty(segment);
  }

}
