/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.entity.bean.db.naming;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.base.text.CaseSyntax;

/**
 * Implementation of {@link DbNamingStrategy} based on {@link CaseSyntax}.
 *
 * @since 1.0.0
 */
class DbNamingStrategyCaseSyntax implements DbNamingStrategy {

  private static final Map<CaseSyntax, DbNamingStrategyCaseSyntax> MAP = new HashMap<>();

  private final CaseSyntax syntax;

  DbNamingStrategyCaseSyntax(CaseSyntax syntax) {

    super();
    this.syntax = syntax;
  }

  @Override
  public String getColumnName(String propertyName) {

    return convert(propertyName);
  }

  @Override
  public String getTableName(String beanName) {

    return convert(beanName);
  }

  private String convert(String string) {

    return this.syntax.convert(string);
  }

  static DbNamingStrategyCaseSyntax of(CaseSyntax caseSyntax) {

    return MAP.computeIfAbsent(caseSyntax, s -> new DbNamingStrategyCaseSyntax(s));
  }

}
