/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.BasicBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.master.AbstractHistoryResult;
import com.opengamma.util.PublicSPI;

/**
 * Result providing the history of a portfolio tree.
 * <p>
 * The returned documents may be a mixture of versions and corrections.
 * The document instant fields are used to identify which are which.
 * See {@link PortfolioHistoryRequest} for more details.
 */
@PublicSPI
@BeanDefinition
public class PortfolioHistoryResult extends AbstractHistoryResult<PortfolioDocument> {

  /**
   * Creates an instance.
   */
  public PortfolioHistoryResult() {
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the returned portfolios from within the documents.
   * 
   * @return the portfolios, not null
   */
  public List<ManageablePortfolio> getPortfolios() {
    List<ManageablePortfolio> result = new ArrayList<ManageablePortfolio>();
    if (getDocuments() != null) {
      for (PortfolioDocument doc : getDocuments()) {
        result.add(doc.getPortfolio());
      }
    }
    return result;
  }

  /**
   * Gets the first portfolio, or null if no documents.
   * 
   * @return the first portfolio, null if none
   */
  public ManageablePortfolio getFirstPortfolio() {
    return getDocuments().size() > 0 ? getDocuments().get(0).getPortfolio() : null;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code PortfolioHistoryResult}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static PortfolioHistoryResult.Meta meta() {
    return PortfolioHistoryResult.Meta.INSTANCE;
  }

  @Override
  public PortfolioHistoryResult.Meta metaBean() {
    return PortfolioHistoryResult.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    return super.propertyGet(propertyName);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue) {
    super.propertySet(propertyName, newValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code PortfolioHistoryResult}.
   */
  public static class Meta extends AbstractHistoryResult.Meta<PortfolioDocument> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends PortfolioHistoryResult> builder() {
      return new BasicBeanBuilder<PortfolioHistoryResult>(new PortfolioHistoryResult());
    }

    @Override
    public Class<? extends PortfolioHistoryResult> beanType() {
      return PortfolioHistoryResult.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
