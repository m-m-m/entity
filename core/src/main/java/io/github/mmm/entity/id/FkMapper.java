package io.github.mmm.entity.id;

import io.github.mmm.value.converter.AtomicTypeMapper;
import io.github.mmm.value.converter.ValueMapper;

/**
 * {@link ValueMapper} to convert from {@link Id} to {@link Id#getPk() primary key} and vice versa.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FkMapper extends AtomicTypeMapper<Id, Object> {

  private static final FkMapper DEFAULT = new FkMapper(PkIdEmpty.getEmpty());

  private final GenericId idTemplate;

  private FkMapper(GenericId idTemplate) {

    super();
    this.idTemplate = idTemplate;
  }

  @Override
  public Class<? extends Id> getSourceType() {

    return this.idTemplate.getClass();
  }

  @Override
  public Class<? extends Object> getTargetType() {

    return this.idTemplate.getPkClass();
  }

  @Override
  public Object toTarget(Id id) {

    return id.getPk();
  }

  @Override
  public Id toSource(Object id) {

    return this.idTemplate.withPk(id);
  }

  /**
   * @return the default {@link FkMapper}.
   */
  public static FkMapper get() {

    return DEFAULT;
  }

  /**
   * @param id the {@link Id} to use as template.
   * @return the {@link FkMapper}.
   */
  public static FkMapper of(Id id) {

    if (id == null) {
      return get();
    }
    GenericId genericId = (GenericId) id;
    return new FkMapper(genericId.withPk(null));
  }

}
