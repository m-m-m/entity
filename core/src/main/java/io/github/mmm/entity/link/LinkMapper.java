package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.Id;
import io.github.mmm.value.converter.AtomicTypeMapper;
import io.github.mmm.value.converter.ValueMapper;

/**
 * {@link ValueMapper} to convert from {@link Link} to {@link Id} and vice versa.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LinkMapper extends AtomicTypeMapper<Link, Id> {

  private final Class<? extends Id> targetType;

  private final Function resolver;

  /**
   * The constructor.
   *
   * @param targetType the {@link #getTargetType() target type}.
   * @param resolver the {@link IdLink#IdLink(Id, Function) resolver} {@link Function}.
   */
  public LinkMapper(Class<? extends Id> targetType, Function<? extends Id, ? extends Entity> resolver) {

    super(Link.class, "");
    this.targetType = targetType;
    this.resolver = resolver;
  }

  @Override
  public Class<? extends Id> getTargetType() {

    return this.targetType;
  }

  @Override
  public Id toTarget(Link sourceValue) {

    return Link.getId(sourceValue);
  }

  @Override
  public Link toSource(Id targetValue) {

    return IdLink.of(targetValue, this.resolver);
  }

}
