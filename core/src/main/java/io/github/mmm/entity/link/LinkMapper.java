package io.github.mmm.entity.link;

import java.util.function.Function;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.id.Id;
import io.github.mmm.value.converter.AtomicTypeMapper;

/**
 * {@link io.github.mmm.value.converter.TypeMapper} to convert from {@link Link} to {@link Id} and vice versa.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LinkMapper extends AtomicTypeMapper<Link, Id> {

  private final Function resolver;

  /**
   * The constructor.
   *
   * @param resolver the {@link IdLink#IdLink(Id, Function) resolver} {@link Function}.
   */
  public LinkMapper(Function<? extends Id, ? extends Entity> resolver) {

    super();
    this.resolver = resolver;
  }

  @Override
  public Class<? extends Link> getSourceType() {

    return Link.class;
  }

  @Override
  public Class<? extends Id> getTargetType() {

    return Id.class;
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
