/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.link;

import java.util.function.Function;

import io.github.mmm.entity.Entity;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.AbstractId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdFactory;
import io.github.mmm.entity.id.IdMarshalling;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.PredicateOperator;
import io.github.mmm.property.object.ObjectProperty;
import io.github.mmm.value.PropertyPath;

/**
 * {@link ObjectProperty} with {@link Link} {@link #getValue() value} {@link Link#getTarget() pointing to} an
 * {@link io.github.mmm.entity.bean.EntityBean entity}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link io.github.mmm.entity.bean.EntityBean
 *        entity}.
 *
 * @since 1.0.0
 */
public class LinkProperty<E extends EntityBean> extends ObjectProperty<Link<E>> {

  private Class<E> entityClass;

  private Function<Id<E>, E> resolver;

  private IdFactory<?, ?> idFactory;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public LinkProperty(String name, Class<E> entityClass, PropertyMetadata<Link<E>> metadata) {

    this(name, entityClass, metadata, null, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   */
  public LinkProperty(String name, Class<E> entityClass, PropertyMetadata<Link<E>> metadata,
      Function<Id<E>, E> resolver) {

    this(name, entityClass, metadata, resolver, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   * @param idFactory the optional {@link IdFactory}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public LinkProperty(String name, Class<E> entityClass, PropertyMetadata<Link<E>> metadata,
      Function<Id<E>, E> resolver, IdFactory<?, ?> idFactory) {

    super(name, (Class) Link.class, metadata);
    this.entityClass = entityClass;
    this.idFactory = idFactory;
    this.resolver = resolver;
  }

  @Override
  protected void doSet(Link<E> newValue) {

    if (newValue != null) {
      Id<E> id = newValue.getId();
      if (id != null) {
        if (this.idFactory == null) {
          this.idFactory = ((AbstractId<?, ?, ?>) id).getFactory();
        } else {
          assert this.idFactory.accept(id);
        }
        if (this.entityClass == null) {
          this.entityClass = id.getType();
        } else if (newValue instanceof IdLink) {
          newValue = ((IdLink<E>) newValue).withType(this.entityClass);
        }
      }
    }
    super.doSet(newValue);
  }

  /**
   * @return the {@link Id#getType() entity class}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class<E> getEntityClass() {

    if (this.entityClass == null) {
      Link<E> link = get();
      if (link != null) {
        Id<E> id = link.getId();
        if (id != null) {
          this.entityClass = id.getType();
        } else if (link.isResolved()) {
          E target = link.getTarget();
          this.entityClass = (Class) ((EntityBean) target).getType().getJavaClass();
        }
      }
    }
    return this.entityClass;
  }

  @Override
  public void read(StructuredReader reader) {

    Id<E> id = IdMarshalling.get().readObject(reader, this.entityClass);
    IdLink<E> link = IdLink.of(id, this.resolver);
    setValue(link);
  }

  @Override
  public void write(StructuredWriter writer) {

    Id<E> id = null;
    Link<E> link = getValue();
    if (link != null) {
      id = link.getId();
    }
    IdMarshalling.get().writeObject(writer, id);
  }

  /**
   * @see #eq(Object)
   * @param other the literal {@link Id} to compare with using {@link PredicateOperator#EQ = (equal)}.
   * @return the resulting {@link CriteriaPredicate}.
   */
  public CriteriaPredicate eq(Id<E> other) {

    return eq(Link.of(other));
  }

  /**
   * @see #eq(Object)
   * @param other the {@link IdProperty} to compare with using {@link PredicateOperator#EQ = (equal)}.
   * @return the resulting {@link CriteriaPredicate}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public CriteriaPredicate eq(IdProperty other) {

    return predicate((ReadableProperty) this, PredicateOperator.EQ, (PropertyPath) other);
  }

  /**
   * @see #eq(Object)
   * @param other the literal {@link Entity} whose {@link Entity#getId() ID} to compare with using
   *        {@link PredicateOperator#EQ = (equal)}.
   * @return the resulting {@link CriteriaPredicate}.
   */
  public CriteriaPredicate eq(E other) {

    if ((other != null) && (other.getId() == null)) {
      return eq(other.Id());
    }
    return eq(Link.of(other));
  }

  /**
   * @see #neq(Object)
   * @param other the literal {@link Id} to compare with using {@link PredicateOperator#NEQ != (not-equal)}.
   * @return the resulting {@link CriteriaPredicate}.
   */
  public CriteriaPredicate neq(Id<E> other) {

    return neq(Link.of(other));
  }

  /**
   * @see #neq(Object)
   * @param other the literal {@link Entity} whose {@link Entity#getId() ID} to compare with using
   *        {@link PredicateOperator#NEQ != (not-equal)}.
   * @return the resulting {@link CriteriaPredicate}.
   */
  public CriteriaPredicate neq(E other) {

    if ((other != null) && (other.getId() == null)) {
      return neq(other.Id());
    }
    return neq(Link.of(other));
  }

  /**
   * @see #neq(Object)
   * @param other the {@link IdProperty} to compare with using {@link PredicateOperator#NEQ != (not-equal)}.
   * @return the resulting {@link CriteriaPredicate}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public CriteriaPredicate neq(IdProperty other) {

    return predicate((ReadableProperty) this, PredicateOperator.NEQ, (PropertyPath) other);
  }

  private <V> CriteriaPredicate predicate(ReadableProperty<V> property1, PredicateOperator op,
      PropertyPath<V> property2) {

    return CriteriaPredicate.of(property1, op, property2);
  }

}
