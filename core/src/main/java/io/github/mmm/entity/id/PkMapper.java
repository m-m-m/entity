package io.github.mmm.entity.id;

import java.util.Objects;

import io.github.mmm.base.lang.Builder;
import io.github.mmm.value.converter.CompositeTypeMapper;
import io.github.mmm.value.converter.ValueMapper;

/**
 * {@link ValueMapper} to convert from {@link Id} to {@link Id#getPk() primary key} and vice versa.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class PkMapper extends CompositeTypeMapper<Id, Object> {

  /** {@link GenericId} to use as template for {@link Builder}. */
  protected final GenericId idTemplate;

  PkMapper(GenericId idTemplate, String suffix, PkMapper next) {

    super(suffix, next);
    if (idTemplate == null) {
      this.idTemplate = next.idTemplate;
    } else {
      this.idTemplate = idTemplate;
    }
  }

  @Override
  public Class<? extends Id> getSourceType() {

    return this.idTemplate.getClass();
  }

  @Override
  public String mapName(String name, String separator) {

    return getSuffix();
  }

  @Override
  public Builder<Id> sourceBuilder() {

    return new IdBuilder(this.idTemplate);
  }

  private static class IdBuilder implements Builder<Id> {

    private GenericId id;

    private IdBuilder(GenericId id) {

      super();
      this.id = id;
    }

    private void withId(Object newId) {

      this.id = this.id.withPk(newId);
    }

    private void withRevision(Object newRevision) {

      this.id = this.id.withRevision((Comparable) newRevision);
    }

    @Override
    public GenericId build() {

      return this.id;
    }
  }

  private static class PkMapperRevision extends PkMapper {

    private PkMapperRevision(GenericId idTemplate) {

      super(idTemplate, Id.COLUMN_REVISION, null);
    }

    @Override
    public Class<? extends Object> getTargetType() {

      return this.idTemplate.getRevisionType();
    }

    @Override
    public Object toTarget(Id id) {

      return id.getRevision();
    }

    @Override
    public void with(Builder<Id> builder, Object revision) {

      IdBuilder idBuilder = (IdBuilder) builder;
      idBuilder.withRevision(revision);
    }

  }

  private static class PkMapperId extends PkMapper {

    /**
     * The constructor.
     *
     * @param next the {@link PkMapperRevision}.
     */
    public PkMapperId(PkMapperRevision next) {

      super(null, "Id", next);
    }

    /**
     * The constructor.
     *
     * @param idTemplate the {@link GenericId} as template.
     * @param next the {@link PkMapperRevision}.
     */
    public PkMapperId(GenericId idTemplate, PkMapperRevision next) {

      super(idTemplate, "Id", next);
    }

    @Override
    public Class<? extends Object> getTargetType() {

      return this.idTemplate.getPkClass();
    }

    @Override
    public String mapName(String name, String separator) {

      return name;
    }

    @Override
    public Id toSource(Object target) {

      assert (next() == null); // no revision field
      assert (!this.idTemplate.hasRevisionField());
      return this.idTemplate.withPkAndRevision(target, null);
    }

    @Override
    public Object toTarget(Id id) {

      return id.getPk();
    }

    @Override
    public void with(Builder<Id> builder, Object id) {

      IdBuilder idBuilder = (IdBuilder) builder;
      idBuilder.withId(id);
    }
  }

  /**
   * @param id the {@link Id} to use as template.
   * @return the {@link PkMapper}.
   */
  public static PkMapper of(Id<?> id) {

    Objects.requireNonNull(id);
    GenericId genericId = (GenericId) id;
    PkMapperRevision revMapper = null;
    if (genericId.hasRevisionField()) {
      revMapper = new PkMapperRevision(genericId.withPkAndRevision(null, null));
    }
    return new PkMapperId(genericId, revMapper);
  }

}
