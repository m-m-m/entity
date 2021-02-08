package io.github.mmm.entity.bean;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.bean.property.BeanProperty;
import io.github.mmm.property.number.integers.IntegerProperty;
import io.github.mmm.property.string.StringProperty;

/**
 * {@link EntityBean} for a city.
 */
public interface City extends EntityBean {

  /** @return the name of this city. */
  StringProperty Name();

  /** @return the approximate number of inhabitants. */
  IntegerProperty Inhabitants();

  /** @return the {@link GeoLocation} of this city. */
  BeanProperty<GeoLocation> GeoLocation();

  /**
   * @return a new instance of {@link City}.
   */
  static City of() {

    City city = BeanFactory.get().create(City.class);
    city.GeoLocation().set(GeoLocation.of());
    return city;
  }
}
