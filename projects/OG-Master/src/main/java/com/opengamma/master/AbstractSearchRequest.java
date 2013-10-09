/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.Instant;

import com.google.common.base.Objects;
import com.opengamma.id.VersionCorrection;
import com.opengamma.util.OpenGammaClock;
import com.opengamma.util.PublicSPI;
import com.opengamma.util.paging.PagingRequest;

/**
 * Request for searching for documents.
 * <p>
 * Documents will be returned that match the search criteria.
 * This abstract class provides the ability to page the results and to search
 * as at a specific version and correction instant.
 * See {@link AbstractHistoryRequest} for more details on how history works.
 */
@PublicSPI
@BeanDefinition
public abstract class AbstractSearchRequest extends DirectBean implements PagedRequest {

  /**
   * The request for paging.
   * By default all matching items will be returned.
   */
  @PropertyDefinition
  private PagingRequest _pagingRequest = PagingRequest.ALL;
  /**
   * The version-correction locator to search at, not null.
   */
  @PropertyDefinition(set = "manual")
  private VersionCorrection _versionCorrection = VersionCorrection.LATEST;

  /**
   * Creates an instance.
   */
  public AbstractSearchRequest() {
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the version-correction locator to search at, null for the latest.
   *
   * @param versionCorrection  the version-correction, null converted to LATEST
   */
  public void setVersionCorrection(VersionCorrection versionCorrection) {
    this._versionCorrection = Objects.firstNonNull(versionCorrection, VersionCorrection.LATEST);
  }

  //-------------------------------------------------------------------------
  /**
   * Checks if this search matches the specified document.
   *
   * @param document  the document to match, null or inappropriate document type returns false
   * @return true if matches
   */
  public boolean matches(AbstractDocument document) {
    if (getVersionCorrection() == null) {
      return true;
    }
    Instant versionFrom = Objects.firstNonNull(document.getVersionFromInstant(), Instant.MIN);
    Instant versionTo = Objects.firstNonNull(document.getVersionToInstant(), Instant.MAX);
    Instant correctionFrom = Objects.firstNonNull(document.getCorrectionFromInstant(), Instant.MIN);
    Instant correctionTo = Objects.firstNonNull(document.getCorrectionToInstant(), Instant.MAX);
    VersionCorrection locked = getVersionCorrection().withLatestFixed(OpenGammaClock.getInstance().instant());
    Instant versionPoint = locked.getVersionAsOf();
    Instant corrrectionPoint = locked.getCorrectedTo();
    return versionPoint.isBefore(versionTo) &&
        !versionFrom.isAfter(versionPoint) &&
        corrrectionPoint.isBefore(correctionTo) &&
        !correctionFrom.isAfter(corrrectionPoint);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code AbstractSearchRequest}.
   * @return the meta-bean, not null
   */
  public static AbstractSearchRequest.Meta meta() {
    return AbstractSearchRequest.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(AbstractSearchRequest.Meta.INSTANCE);
  }

  @Override
  public AbstractSearchRequest.Meta metaBean() {
    return AbstractSearchRequest.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -2092032669:  // pagingRequest
        return getPagingRequest();
      case -2031293866:  // versionCorrection
        return getVersionCorrection();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -2092032669:  // pagingRequest
        setPagingRequest((PagingRequest) newValue);
        return;
      case -2031293866:  // versionCorrection
        setVersionCorrection((VersionCorrection) newValue);
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
      AbstractSearchRequest other = (AbstractSearchRequest) obj;
      return JodaBeanUtils.equal(getPagingRequest(), other.getPagingRequest()) &&
          JodaBeanUtils.equal(getVersionCorrection(), other.getVersionCorrection());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getPagingRequest());
    hash += hash * 31 + JodaBeanUtils.hashCode(getVersionCorrection());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the request for paging.
   * By default all matching items will be returned.
   * @return the value of the property
   */
  public PagingRequest getPagingRequest() {
    return _pagingRequest;
  }

  /**
   * Sets the request for paging.
   * By default all matching items will be returned.
   * @param pagingRequest  the new value of the property
   */
  public void setPagingRequest(PagingRequest pagingRequest) {
    this._pagingRequest = pagingRequest;
  }

  /**
   * Gets the the {@code pagingRequest} property.
   * By default all matching items will be returned.
   * @return the property, not null
   */
  public final Property<PagingRequest> pagingRequest() {
    return metaBean().pagingRequest().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the version-correction locator to search at, not null.
   * @return the value of the property
   */
  public VersionCorrection getVersionCorrection() {
    return _versionCorrection;
  }

  /**
   * Gets the the {@code versionCorrection} property.
   * @return the property, not null
   */
  public final Property<VersionCorrection> versionCorrection() {
    return metaBean().versionCorrection().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code AbstractSearchRequest}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code pagingRequest} property.
     */
    private final MetaProperty<PagingRequest> _pagingRequest = DirectMetaProperty.ofReadWrite(
        this, "pagingRequest", AbstractSearchRequest.class, PagingRequest.class);
    /**
     * The meta-property for the {@code versionCorrection} property.
     */
    private final MetaProperty<VersionCorrection> _versionCorrection = DirectMetaProperty.ofReadWrite(
        this, "versionCorrection", AbstractSearchRequest.class, VersionCorrection.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "pagingRequest",
        "versionCorrection");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -2092032669:  // pagingRequest
          return _pagingRequest;
        case -2031293866:  // versionCorrection
          return _versionCorrection;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends AbstractSearchRequest> builder() {
      throw new UnsupportedOperationException("AbstractSearchRequest is an abstract class");
    }

    @Override
    public Class<? extends AbstractSearchRequest> beanType() {
      return AbstractSearchRequest.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code pagingRequest} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PagingRequest> pagingRequest() {
      return _pagingRequest;
    }

    /**
     * The meta-property for the {@code versionCorrection} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<VersionCorrection> versionCorrection() {
      return _versionCorrection;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
