package io.github.mmm.entity.property.link;

import java.util.function.Function;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdMarshalling;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.link.Link;
import io.github.mmm.marshall.MarshallableObject;
import io.github.mmm.marshall.Marshalling;
import io.github.mmm.marshall.MarshallingConfig;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/**
 * {@link Marshalling} to read and write {@link Link} values.
 */
public interface LinkMarshalling extends Marshalling<Link<?>> {

  @Override
  default void writeObject(StructuredWriter writer, Link<?> link) {

    if (link == null) {
      writer.writeValueAsNull();
      return;
    }
    Id<?> id = link.getId();
    Boolean includeLinkTarget = writer.getFormat().getConfig().get(MarshallingConfig.VAR_LINK_TARGET);
    boolean includeTarget;
    if (includeLinkTarget == null) {
      includeTarget = ((id == null) || id.isTransient());
    } else {
      includeTarget = includeLinkTarget.booleanValue();
    }
    Object target = null;
    if (includeTarget) {
      target = link.getTarget();
    }
    if (target == null) {
      if (id == null) {
        writer.writeValueAsNull();
        return;
      } else if (id.getRevision() == null) {
        IdMarshalling.get().writeObject(writer, id); // write simple PK as atomic value
        return;
      }
    }
    writer.writeStartObject(link);
    if (target == null) {
      writer.writeName(Link.PROPERTY_ID);
      IdMarshalling.get().writeObject(writer, id);
    } else {
      writer.writeName(Link.PROPERTY_TARGET);
      ((MarshallableObject) target).write(writer);
    }
    writer.writeEnd();
  }

  @Override
  default Link<?> readObject(StructuredReader reader) {

    return readObject(reader, null, null);
  }

  /**
   * @param <E> type of the identified entity.
   * @param reader the {@link StructuredReader}.
   * @param type the {@link Id#getEntityClass() entity type}.
   * @param resolver the {@link IdLink#setResolver(Function) resolver} {@link Function}.
   * @return the unmarshalled {@link Id}.
   */
  @SuppressWarnings("unchecked")
  default <E extends EntityBean> Link<E> readObject(StructuredReader reader, Class<E> type,
      Function<Id<E>, E> resolver) {

    Id<E> id = null;
    E entity = null;
    if (reader.readStartObject(LinkMarshallingImpl.EXAMPLE_LINK)) {
      while (!reader.readEnd()) {
        String name = reader.readName();
        if (Link.PROPERTY_ID.equals(name)) {
          id = IdMarshalling.get().readObject(reader, type);
        } else if (Link.PROPERTY_TARGET.equals(name)
            && !Boolean.FALSE.equals(reader.getFormat().getConfig().get(MarshallingConfig.VAR_LINK_TARGET))) {
          if ((type == null) && (id != null)) {
            type = id.getEntityClass();
          }
          if (type == null) {
            throw new IllegalStateException("Missing entity class!");
          }
          entity = BeanFactory.get().create(type);
          entity = (E) entity.read(reader);
        } else {
          reader.skipValue(); // ignore unknown property for compatibility and future extensions...
        }
      }
    } else {
      id = IdMarshalling.get().readObject(reader, type);
    }
    Link<E> link = null;
    if (entity == null) {
      if (id != null) {
        link = IdLink.of(id, resolver);
      }
    } else {
      link = Link.of(entity);
    }
    return link;
  }

  /**
   * @return the singleton instance of this {@link IdMarshalling}.
   */
  static LinkMarshalling get() {

    return LinkMarshallingImpl.INSTANCE;
  }

}
