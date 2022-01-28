/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides database support for {@code mmm-entity-bean}.
 *
 * @provides io.github.mmm.entity.bean.db.dialect.DbDialect
 * @uses io.github.mmm.entity.bean.db.dialect.DbDialect
 */
module io.github.mmm.entity.db {

  requires transitive io.github.mmm.entity.bean;

  uses io.github.mmm.entity.bean.db.dialect.DbDialect;

  provides io.github.mmm.entity.bean.db.dialect.DbDialect //
      with io.github.mmm.entity.bean.db.dialect.h2.H2Dialect, //
      io.github.mmm.entity.bean.db.dialect.postgresql.PostgreSqlDialect, //
      io.github.mmm.entity.bean.db.dialect.sqlserver.SqlServerDialect //
  ;

  exports io.github.mmm.entity.bean.db.constraint;

  exports io.github.mmm.entity.bean.db.dialect;

  exports io.github.mmm.entity.bean.db.dialect.h2;

  exports io.github.mmm.entity.bean.db.dialect.postgresql;

  exports io.github.mmm.entity.bean.db.dialect.sqlserver;

  exports io.github.mmm.entity.bean.db.naming;

  exports io.github.mmm.entity.bean.db.orm;

  exports io.github.mmm.entity.bean.db.result;

  exports io.github.mmm.entity.bean.db.statement;

  exports io.github.mmm.entity.bean.db.statement.create;

  exports io.github.mmm.entity.bean.db.statement.delete;

  exports io.github.mmm.entity.bean.db.statement.insert;

  exports io.github.mmm.entity.bean.db.statement.merge;

  exports io.github.mmm.entity.bean.db.statement.select;

  exports io.github.mmm.entity.bean.db.statement.update;

  exports io.github.mmm.entity.bean.db.statement.upsert;

  exports io.github.mmm.entity.bean.db.typemapping;

}
