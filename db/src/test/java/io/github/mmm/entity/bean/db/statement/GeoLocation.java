package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.property.number.doubles.DoubleProperty;

/**
 * Embeddable for WGS84 geo-location.
 */
public interface GeoLocation extends WritableBean {

  /**
   * @return the latitude (y coordinate in direction south/north).
   */
  DoubleProperty Latitude();

  /**
   * @return the longitude (x coordinate in direction west/east).
   */
  DoubleProperty Longitude();

  /**
   * @return a new instance of {@link GeoLocation}.
   */
  static GeoLocation of() {

    return BeanFactory.get().create(GeoLocation.class);
  }

}
