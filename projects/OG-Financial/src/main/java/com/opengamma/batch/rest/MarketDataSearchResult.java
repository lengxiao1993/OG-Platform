/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.batch.rest;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.batch.domain.MarketData;
import com.opengamma.util.PublicSPI;
import com.opengamma.util.paging.Paging;

/**
 * Result from searching for live data values.
 * <p>
 */
@PublicSPI
@BeanDefinition
public class MarketDataSearchResult extends DirectBean {

  /**
   * Creates an instance.
   */
  public MarketDataSearchResult() {
  }
  
  /**
   * The paging information, not null if correctly created.
   */
  @PropertyDefinition
  private Paging _paging;
  /**
   * The values, not null.
   */
  @PropertyDefinition
  private final List<MarketData> _marketDatas = new ArrayList<MarketData>();
  

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code MarketDataSearchResult}.
   * @return the meta-bean, not null
   */
  public static MarketDataSearchResult.Meta meta() {
    return MarketDataSearchResult.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(MarketDataSearchResult.Meta.INSTANCE);
  }

  @Override
  public MarketDataSearchResult.Meta metaBean() {
    return MarketDataSearchResult.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -995747956:  // paging
        return getPaging();
      case 259966765:  // marketDatas
        return getMarketDatas();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -995747956:  // paging
        setPaging((Paging) newValue);
        return;
      case 259966765:  // marketDatas
        setMarketDatas((List<MarketData>) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      MarketDataSearchResult other = (MarketDataSearchResult) obj;
      return JodaBeanUtils.equal(getPaging(), other.getPaging()) &&
          JodaBeanUtils.equal(getMarketDatas(), other.getMarketDatas());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getPaging());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMarketDatas());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the paging information, not null if correctly created.
   * @return the value of the property
   */
  public Paging getPaging() {
    return _paging;
  }

  /**
   * Sets the paging information, not null if correctly created.
   * @param paging  the new value of the property
   */
  public void setPaging(Paging paging) {
    this._paging = paging;
  }

  /**
   * Gets the the {@code paging} property.
   * @return the property, not null
   */
  public final Property<Paging> paging() {
    return metaBean().paging().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the values, not null.
   * @return the value of the property
   */
  public List<MarketData> getMarketDatas() {
    return _marketDatas;
  }

  /**
   * Sets the values, not null.
   * @param marketDatas  the new value of the property
   */
  public void setMarketDatas(List<MarketData> marketDatas) {
    this._marketDatas.clear();
    this._marketDatas.addAll(marketDatas);
  }

  /**
   * Gets the the {@code marketDatas} property.
   * @return the property, not null
   */
  public final Property<List<MarketData>> marketDatas() {
    return metaBean().marketDatas().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code MarketDataSearchResult}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code paging} property.
     */
    private final MetaProperty<Paging> _paging = DirectMetaProperty.ofReadWrite(
        this, "paging", MarketDataSearchResult.class, Paging.class);
    /**
     * The meta-property for the {@code marketDatas} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<MarketData>> _marketDatas = DirectMetaProperty.ofReadWrite(
        this, "marketDatas", MarketDataSearchResult.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "paging",
        "marketDatas");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -995747956:  // paging
          return _paging;
        case 259966765:  // marketDatas
          return _marketDatas;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends MarketDataSearchResult> builder() {
      return new DirectBeanBuilder<MarketDataSearchResult>(new MarketDataSearchResult());
    }

    @Override
    public Class<? extends MarketDataSearchResult> beanType() {
      return MarketDataSearchResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code paging} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Paging> paging() {
      return _paging;
    }

    /**
     * The meta-property for the {@code marketDatas} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<MarketData>> marketDatas() {
      return _marketDatas;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
