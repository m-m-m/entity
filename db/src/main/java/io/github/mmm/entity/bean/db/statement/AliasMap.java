package io.github.mmm.entity.bean.db.statement;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.text.CaseHelper;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Class to {@link Map#get(Object) map} from {@link AbstractEntitiesClause#getAlias() alias} to
 * {@link AbstractEntitiesClause#getEntity() entity}. A single {@link AliasMap} instance is used per {@link DbStatement}
 * to ensure unique {@link AbstractEntitiesClause#getAlias() aliases}.
 *
 * @since 1.0.0
 */
public class AliasMap {

  private final Map<String, EntityBean> aliasMap;

  /**
   * The constructor.
   */
  public AliasMap() {

    super();
    this.aliasMap = new HashMap<>();
  }

  /**
   * @param alias the {@link AbstractEntitiesClause#getAlias() alias} to remove.
   * @return the removed {@link EntityBean} or {@code null} if none was present or mapped.
   */
  public EntityBean remove(String alias) {

    return this.aliasMap.remove(alias);
  }

  /**
   * @param alias the {@link AbstractEntitiesClause#getAlias() alias} to check.
   * @return {@code true} if the given {@code alias} is currently mapped, {@code false} otherwise.
   */
  public boolean contains(String alias) {

    return this.aliasMap.containsKey(alias);
  }

  /**
   * @param alias the {@link AbstractEntitiesClause#getAlias() alias} to get the mapping for.
   * @return the mapped {@link EntityBean} that is associated with the given {@code alias}. May be {@code null}.
   */
  public EntityBean getEntity(String alias) {

    return this.aliasMap.get(alias);
  }

  /**
   * @param alias the {@link AbstractEntitiesClause#getAlias() alias} to bind.
   * @param entity the {@link EntityBean} to bind to.
   * @return the previously mapped {@link EntityBean} or {@code null}.
   */
  public EntityBean put(String alias, EntityBean entity) {

    if (this.aliasMap.containsKey(alias)) {
      if (entity == null) {
        throw new DuplicateObjectException(alias);
      }
      throw new DuplicateObjectException(entity, alias, this.aliasMap.get(alias));
    }
    return this.aliasMap.put(alias, entity);
  }

  /**
   * @param entityClause the {@link AbstractEntityClause}.
   * @return a unique {@link AbstractEntityClause#getAlias() alias} generated for the given
   *         {@link AbstractEntityClause}.
   */
  public String createAlias(AbstractEntityClause<?, ?, ?> entityClause) {

    String entityName = CaseHelper.uncapitalize(entityClause.getEntityName());
    int len = 1;
    int length = entityName.length();
    String alias;
    while (len < length) {
      alias = entityName.substring(0, len);
      if (!contains(alias)) {
        return alias;
      }
      len++;
    }
    int i = 0;
    while (i < 100) {
      alias = entityName + i;
      if (!contains(alias)) {
        return alias;
      }
      i++;
    }
    throw new IllegalStateException();
  }

}
