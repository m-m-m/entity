package io.github.mmm.entity.id;

import java.util.Objects;

import io.github.mmm.entity.link.Link;
import io.github.mmm.value.converter.AtomicTypeMapper;
import io.github.mmm.value.converter.ValueMapper;

/**
 * {@link ValueMapper} to convert from {@link Link} to {@link Id} and vice versa.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class IdMapper extends AtomicTypeMapper<Id, Object> {

  private static final IdMapper DEFAULT = new IdMapper(LongVersionId.getEmpty());

  private final GenericId idTemplate;

  private IdMapper(GenericId idTemplate) {

    super();
    this.idTemplate = idTemplate;
  }

  @Override
  public Class<? extends Id> getSourceType() {

    return this.idTemplate.getClass();
  }

  @Override
  public Class<? extends Object> getTargetType() {

    return this.idTemplate.getIdType();
  }

  @Override
  public Object toTarget(Id id) {

    return id.get();
  }

  @Override
  public Id toSource(Object id) {

    return this.idTemplate.withIdAndRevision(id, null);
  }

  /**
   * @return the default {@link IdMapper}.
   */
  public static IdMapper of() {

    return DEFAULT;
  }

  /**
   * @param id the {@link Id} to use as template.
   * @return the {@link IdMapper}.
   */
  public static IdMapper of(Id id) {

    Objects.requireNonNull(id);
    GenericId genericId = (GenericId) id;
    return new IdMapper(genericId.withIdAndRevision(null, null));
  }

}
