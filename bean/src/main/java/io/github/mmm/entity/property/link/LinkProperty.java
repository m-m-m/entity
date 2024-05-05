/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.property.link;

import java.util.function.Function;
import java.util.function.Supplier;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.IdMarshalling;
import io.github.mmm.entity.link.AbstractLink;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.link.LinkMapper;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.PropertyMetadata;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.PredicateOperator;
import io.github.mmm.property.object.ObjectProperty;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.ReadableValue;
import io.github.mmm.value.converter.TypeMapper;

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

  private LinkMapper typeMapper;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public LinkProperty(String name, Class<E> entityClass, PropertyMetadata<Link<E>> metadata) {

    this(name, entityClass, metadata, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param entityClass the optional {@link Class} of the linked entity.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public LinkProperty(String name, Class<E> entityClass, PropertyMetadata<Link<E>> metadata,
      Function<Id<E>, E> resolver) {

    super(name, (Class) Link.class, metadata);
    this.entityClass = entityClass;
    this.resolver = resolver;
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param value the (initial) {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   */
  public LinkProperty(String name, Link<E> value, PropertyMetadata<Link<E>> metadata) {

    this(name, value, metadata, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param value the (initial) {@link #get() value}.
   * @param metadata the {@link #getMetadata() metadata}.
   * @param resolver the optional {@link IdLink#of(Id, Function) resolver function}.
   */
  public LinkProperty(String name, Link<E> value, PropertyMetadata<Link<E>> metadata, Function<Id<E>, E> resolver) {

    super(name, value, metadata);
    this.entityClass = value.getId().getEntityClass();
    this.resolver = resolver;
  }

  @Override
  protected void doSet(Link<E> newValue) {

    if (newValue != null) {
      Id<E> id = newValue.getId();
      if (id != null) {
        if (this.entityClass == null) {
          this.entityClass = id.getEntityClass();
        } else {
          Class<E> idEntityType = id.getEntityClass();
          if (idEntityType == null) {
            if (newValue instanceof IdLink) {
              newValue = ((IdLink<E>) newValue).withType(this.entityClass);
            }
          } else if (!this.entityClass.isAssignableFrom(idEntityType)) {
            throw new IllegalArgumentException("Cannot set link of type " + idEntityType.getName() + " to property "
                + getName() + " with incompatible entity type " + this.entityClass.getName());
          }
        }
      }
    }
    super.doSet(newValue);
  }

  /**
   * @return the linked {@link EntityBean entity}.
   * @see Link#getTarget()
   */
  public E getEntity() {

    Link<E> link = get();
    if (link == null) {
      return null;
    }
    return link.getTarget();
  }

  /**
   * @return the {@link Id#getEntityClass() entity class}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class<E> getEntityClass() {

    if (this.entityClass == null) {
      Link<E> link = get();
      if (link != null) {
        Id<E> id = link.getId();
        if (id != null) {
          this.entityClass = id.getEntityClass();
        } else if (link.isResolved()) {
          E target = link.getTarget();
          this.entityClass = (Class) ((EntityBean) target).getType().getJavaClass();
        }
      }
    }
    return this.entityClass;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public TypeMapper<Link<E>, Id<E>> getTypeMapper() {

    if (this.typeMapper == null) {
      this.typeMapper = new LinkMapper(this.resolver);
    }
    return (TypeMapper) this.typeMapper;
  }

  @Override
  public boolean isValueMutable() {

    return true; // IdLink without resolver function is actually immutable but lets keep it simple
  }

  @Override
  protected Supplier<? extends Link<E>> createReadOnlyExpression() {

    final ReadOnlyLink readOnlyLink = new ReadOnlyLink();
    return () -> {
      Link<E> link = get();
      if (link == null) {
        return null;
      }
      return readOnlyLink;
    };
  }

  @Override
  protected void readValue(StructuredReader reader) {

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

  /**
   * <b>ATTENTION:<b><br>
   * This is an internal method for framework code.
   *
   * @param resolver new resolver {@link Function}.
   * @see IdLink#setResolver(Function)
   */
  @SuppressWarnings("unchecked")
  public void setResolver(Function<Id<E>, E> resolver) {

    // TODO: checks?
    this.resolver = resolver;
    this.typeMapper = null;
    Link<E> link = doGet();
    if ((link != null) && !link.isResolved()) {
      if (link instanceof IdLink idLink) {
        idLink.setResolver(resolver);
      }
    }
  }

  @Override
  public void copyValue(ReadableValue<Link<E>> other) {

    Link<E> link = other.get();
    if ((link != null) && link.isResolved()) {
      link = Link.of(link.getId());
    }
    set(link);
  }

  private class ReadOnlyLink extends AbstractLink<E> {
    @Override
    public Id<E> getId() {

      return get().getId();
    }

    @Override
    public E getTarget() {

      E target = get().getTarget();
      if (target != null) {
        target = WritableBean.getReadOnly(target);
      }
      return target;
    }

    @Override
    public boolean isResolved() {

      return get().isResolved();
    }

  }

}
