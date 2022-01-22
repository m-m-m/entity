/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.statement;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.bean.Mandatory;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.property.number.integers.IntegerProperty;
import io.github.mmm.property.string.StringProperty;
import io.github.mmm.property.temporal.DurationInSecondsProperty;

/**
 * {@link EntityBean} for a musical song.
 */
public interface Song extends EntityBean {

  /**
   * @return title of the song.
   */
  @Mandatory
  StringProperty Title();

  /**
   * @return the genre of the song (e.g. "pop" or "rock").
   */
  StringProperty Genre();

  /**
   * @return the track number of the song on the album.
   */
  IntegerProperty TrackNo();

  /**
   * @return the duration in seconds.
   */
  DurationInSecondsProperty Duration();

  /**
   * @return the composer of the song.
   */
  LinkProperty<Person> Composer();

  /**
   * @return a new instance of {@link Song}.
   */
  static Song of() {

    return BeanFactory.get().create(Song.class);
  }

}
