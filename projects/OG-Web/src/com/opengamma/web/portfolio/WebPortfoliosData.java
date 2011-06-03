/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.portfolio;

import java.util.Map;

import javax.ws.rs.core.UriInfo;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.BasicBeanBuilder;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.id.UniqueIdentifier;
import com.opengamma.master.portfolio.ManageablePortfolioNode;
import com.opengamma.master.portfolio.PortfolioDocument;
import com.opengamma.master.portfolio.PortfolioMaster;
import com.opengamma.master.position.PositionMaster;

/**
 * Data class for web-based portfolios.
 */
@BeanDefinition
public class WebPortfoliosData extends DirectBean {

  /**
   * The portfolio master.
   */
  @PropertyDefinition
  private PortfolioMaster _portfolioMaster;
  /**
   * The position master.
   */
  @PropertyDefinition
  private PositionMaster _positionMaster;
  /**
   * The JSR-311 URI information.
   */
  @PropertyDefinition
  private UriInfo _uriInfo;
  /**
   * The portfolio id from the input URI.
   */
  @PropertyDefinition
  private String _uriPortfolioId;
  /**
   * The node id from the input URI.
   */
  @PropertyDefinition
  private String _uriNodeId;
  /**
   * The position id from the input URI.
   */
  @PropertyDefinition
  private String _uriPositionId;
  /**
   * The version id from the URI.
   */
  @PropertyDefinition
  private String _uriVersionId;
  /**
   * The portfolio.
   */
  @PropertyDefinition
  private PortfolioDocument _portfolio;
  /**
   * The parent node.
   */
  @PropertyDefinition
  private ManageablePortfolioNode _parentNode;
  /**
   * The node.
   */
  @PropertyDefinition
  private ManageablePortfolioNode _node;
  /**
   * The versioned portfolio.
   */
  @PropertyDefinition
  private PortfolioDocument _versioned;

  /**
   * Creates an instance.
   */
  public WebPortfoliosData() {
  }

  /**
   * Creates an instance.
   * @param uriInfo  the URI information
   */
  public WebPortfoliosData(final UriInfo uriInfo) {
    setUriInfo(uriInfo);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the best available portfolio id.
   * @param overrideId  the override id, null derives the result from the data
   * @return the id, may be null
   */
  public String getBestPortfolioUriId(final UniqueIdentifier overrideId) {
    if (overrideId != null) {
      return overrideId.toLatest().toString();
    }
    return getPortfolio() != null ? getPortfolio().getUniqueId().toLatest().toString() : getUriPortfolioId();
  }

  /**
   * Gets the best available node id.
   * @param overrideId  the override id, null derives the result from the data
   * @return the id, may be null
   */
  public String getBestNodeUriId(final UniqueIdentifier overrideId) {
    if (overrideId != null) {
      return overrideId.toLatest().toString();
    }
    return getNode() != null ? getNode().getUniqueId().toLatest().toString() : getUriNodeId();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code WebPortfoliosData}.
   * @return the meta-bean, not null
   */
  public static WebPortfoliosData.Meta meta() {
    return WebPortfoliosData.Meta.INSTANCE;
  }

  @Override
  public WebPortfoliosData.Meta metaBean() {
    return WebPortfoliosData.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    switch (propertyName.hashCode()) {
      case -772274742:  // portfolioMaster
        return getPortfolioMaster();
      case -1840419605:  // positionMaster
        return getPositionMaster();
      case -173275078:  // uriInfo
        return getUriInfo();
      case -72522889:  // uriPortfolioId
        return getUriPortfolioId();
      case 1130377033:  // uriNodeId
        return getUriNodeId();
      case 1240319664:  // uriPositionId
        return getUriPositionId();
      case 666567687:  // uriVersionId
        return getUriVersionId();
      case 1121781064:  // portfolio
        return getPortfolio();
      case -244857396:  // parentNode
        return getParentNode();
      case 3386882:  // node
        return getNode();
      case -1407102089:  // versioned
        return getVersioned();
    }
    return super.propertyGet(propertyName);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue) {
    switch (propertyName.hashCode()) {
      case -772274742:  // portfolioMaster
        setPortfolioMaster((PortfolioMaster) newValue);
        return;
      case -1840419605:  // positionMaster
        setPositionMaster((PositionMaster) newValue);
        return;
      case -173275078:  // uriInfo
        setUriInfo((UriInfo) newValue);
        return;
      case -72522889:  // uriPortfolioId
        setUriPortfolioId((String) newValue);
        return;
      case 1130377033:  // uriNodeId
        setUriNodeId((String) newValue);
        return;
      case 1240319664:  // uriPositionId
        setUriPositionId((String) newValue);
        return;
      case 666567687:  // uriVersionId
        setUriVersionId((String) newValue);
        return;
      case 1121781064:  // portfolio
        setPortfolio((PortfolioDocument) newValue);
        return;
      case -244857396:  // parentNode
        setParentNode((ManageablePortfolioNode) newValue);
        return;
      case 3386882:  // node
        setNode((ManageablePortfolioNode) newValue);
        return;
      case -1407102089:  // versioned
        setVersioned((PortfolioDocument) newValue);
        return;
    }
    super.propertySet(propertyName, newValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      WebPortfoliosData other = (WebPortfoliosData) obj;
      return JodaBeanUtils.equal(getPortfolioMaster(), other.getPortfolioMaster()) &&
          JodaBeanUtils.equal(getPositionMaster(), other.getPositionMaster()) &&
          JodaBeanUtils.equal(getUriInfo(), other.getUriInfo()) &&
          JodaBeanUtils.equal(getUriPortfolioId(), other.getUriPortfolioId()) &&
          JodaBeanUtils.equal(getUriNodeId(), other.getUriNodeId()) &&
          JodaBeanUtils.equal(getUriPositionId(), other.getUriPositionId()) &&
          JodaBeanUtils.equal(getUriVersionId(), other.getUriVersionId()) &&
          JodaBeanUtils.equal(getPortfolio(), other.getPortfolio()) &&
          JodaBeanUtils.equal(getParentNode(), other.getParentNode()) &&
          JodaBeanUtils.equal(getNode(), other.getNode()) &&
          JodaBeanUtils.equal(getVersioned(), other.getVersioned());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getPortfolioMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPositionMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUriInfo());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUriPortfolioId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUriNodeId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUriPositionId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUriVersionId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPortfolio());
    hash += hash * 31 + JodaBeanUtils.hashCode(getParentNode());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNode());
    hash += hash * 31 + JodaBeanUtils.hashCode(getVersioned());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the portfolio master.
   * @return the value of the property
   */
  public PortfolioMaster getPortfolioMaster() {
    return _portfolioMaster;
  }

  /**
   * Sets the portfolio master.
   * @param portfolioMaster  the new value of the property
   */
  public void setPortfolioMaster(PortfolioMaster portfolioMaster) {
    this._portfolioMaster = portfolioMaster;
  }

  /**
   * Gets the the {@code portfolioMaster} property.
   * @return the property, not null
   */
  public final Property<PortfolioMaster> portfolioMaster() {
    return metaBean().portfolioMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the position master.
   * @return the value of the property
   */
  public PositionMaster getPositionMaster() {
    return _positionMaster;
  }

  /**
   * Sets the position master.
   * @param positionMaster  the new value of the property
   */
  public void setPositionMaster(PositionMaster positionMaster) {
    this._positionMaster = positionMaster;
  }

  /**
   * Gets the the {@code positionMaster} property.
   * @return the property, not null
   */
  public final Property<PositionMaster> positionMaster() {
    return metaBean().positionMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the JSR-311 URI information.
   * @return the value of the property
   */
  public UriInfo getUriInfo() {
    return _uriInfo;
  }

  /**
   * Sets the JSR-311 URI information.
   * @param uriInfo  the new value of the property
   */
  public void setUriInfo(UriInfo uriInfo) {
    this._uriInfo = uriInfo;
  }

  /**
   * Gets the the {@code uriInfo} property.
   * @return the property, not null
   */
  public final Property<UriInfo> uriInfo() {
    return metaBean().uriInfo().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the portfolio id from the input URI.
   * @return the value of the property
   */
  public String getUriPortfolioId() {
    return _uriPortfolioId;
  }

  /**
   * Sets the portfolio id from the input URI.
   * @param uriPortfolioId  the new value of the property
   */
  public void setUriPortfolioId(String uriPortfolioId) {
    this._uriPortfolioId = uriPortfolioId;
  }

  /**
   * Gets the the {@code uriPortfolioId} property.
   * @return the property, not null
   */
  public final Property<String> uriPortfolioId() {
    return metaBean().uriPortfolioId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the node id from the input URI.
   * @return the value of the property
   */
  public String getUriNodeId() {
    return _uriNodeId;
  }

  /**
   * Sets the node id from the input URI.
   * @param uriNodeId  the new value of the property
   */
  public void setUriNodeId(String uriNodeId) {
    this._uriNodeId = uriNodeId;
  }

  /**
   * Gets the the {@code uriNodeId} property.
   * @return the property, not null
   */
  public final Property<String> uriNodeId() {
    return metaBean().uriNodeId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the position id from the input URI.
   * @return the value of the property
   */
  public String getUriPositionId() {
    return _uriPositionId;
  }

  /**
   * Sets the position id from the input URI.
   * @param uriPositionId  the new value of the property
   */
  public void setUriPositionId(String uriPositionId) {
    this._uriPositionId = uriPositionId;
  }

  /**
   * Gets the the {@code uriPositionId} property.
   * @return the property, not null
   */
  public final Property<String> uriPositionId() {
    return metaBean().uriPositionId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the version id from the URI.
   * @return the value of the property
   */
  public String getUriVersionId() {
    return _uriVersionId;
  }

  /**
   * Sets the version id from the URI.
   * @param uriVersionId  the new value of the property
   */
  public void setUriVersionId(String uriVersionId) {
    this._uriVersionId = uriVersionId;
  }

  /**
   * Gets the the {@code uriVersionId} property.
   * @return the property, not null
   */
  public final Property<String> uriVersionId() {
    return metaBean().uriVersionId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the portfolio.
   * @return the value of the property
   */
  public PortfolioDocument getPortfolio() {
    return _portfolio;
  }

  /**
   * Sets the portfolio.
   * @param portfolio  the new value of the property
   */
  public void setPortfolio(PortfolioDocument portfolio) {
    this._portfolio = portfolio;
  }

  /**
   * Gets the the {@code portfolio} property.
   * @return the property, not null
   */
  public final Property<PortfolioDocument> portfolio() {
    return metaBean().portfolio().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the parent node.
   * @return the value of the property
   */
  public ManageablePortfolioNode getParentNode() {
    return _parentNode;
  }

  /**
   * Sets the parent node.
   * @param parentNode  the new value of the property
   */
  public void setParentNode(ManageablePortfolioNode parentNode) {
    this._parentNode = parentNode;
  }

  /**
   * Gets the the {@code parentNode} property.
   * @return the property, not null
   */
  public final Property<ManageablePortfolioNode> parentNode() {
    return metaBean().parentNode().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the node.
   * @return the value of the property
   */
  public ManageablePortfolioNode getNode() {
    return _node;
  }

  /**
   * Sets the node.
   * @param node  the new value of the property
   */
  public void setNode(ManageablePortfolioNode node) {
    this._node = node;
  }

  /**
   * Gets the the {@code node} property.
   * @return the property, not null
   */
  public final Property<ManageablePortfolioNode> node() {
    return metaBean().node().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the versioned portfolio.
   * @return the value of the property
   */
  public PortfolioDocument getVersioned() {
    return _versioned;
  }

  /**
   * Sets the versioned portfolio.
   * @param versioned  the new value of the property
   */
  public void setVersioned(PortfolioDocument versioned) {
    this._versioned = versioned;
  }

  /**
   * Gets the the {@code versioned} property.
   * @return the property, not null
   */
  public final Property<PortfolioDocument> versioned() {
    return metaBean().versioned().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code WebPortfoliosData}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code portfolioMaster} property.
     */
    private final MetaProperty<PortfolioMaster> _portfolioMaster = DirectMetaProperty.ofReadWrite(
        this, "portfolioMaster", WebPortfoliosData.class, PortfolioMaster.class);
    /**
     * The meta-property for the {@code positionMaster} property.
     */
    private final MetaProperty<PositionMaster> _positionMaster = DirectMetaProperty.ofReadWrite(
        this, "positionMaster", WebPortfoliosData.class, PositionMaster.class);
    /**
     * The meta-property for the {@code uriInfo} property.
     */
    private final MetaProperty<UriInfo> _uriInfo = DirectMetaProperty.ofReadWrite(
        this, "uriInfo", WebPortfoliosData.class, UriInfo.class);
    /**
     * The meta-property for the {@code uriPortfolioId} property.
     */
    private final MetaProperty<String> _uriPortfolioId = DirectMetaProperty.ofReadWrite(
        this, "uriPortfolioId", WebPortfoliosData.class, String.class);
    /**
     * The meta-property for the {@code uriNodeId} property.
     */
    private final MetaProperty<String> _uriNodeId = DirectMetaProperty.ofReadWrite(
        this, "uriNodeId", WebPortfoliosData.class, String.class);
    /**
     * The meta-property for the {@code uriPositionId} property.
     */
    private final MetaProperty<String> _uriPositionId = DirectMetaProperty.ofReadWrite(
        this, "uriPositionId", WebPortfoliosData.class, String.class);
    /**
     * The meta-property for the {@code uriVersionId} property.
     */
    private final MetaProperty<String> _uriVersionId = DirectMetaProperty.ofReadWrite(
        this, "uriVersionId", WebPortfoliosData.class, String.class);
    /**
     * The meta-property for the {@code portfolio} property.
     */
    private final MetaProperty<PortfolioDocument> _portfolio = DirectMetaProperty.ofReadWrite(
        this, "portfolio", WebPortfoliosData.class, PortfolioDocument.class);
    /**
     * The meta-property for the {@code parentNode} property.
     */
    private final MetaProperty<ManageablePortfolioNode> _parentNode = DirectMetaProperty.ofReadWrite(
        this, "parentNode", WebPortfoliosData.class, ManageablePortfolioNode.class);
    /**
     * The meta-property for the {@code node} property.
     */
    private final MetaProperty<ManageablePortfolioNode> _node = DirectMetaProperty.ofReadWrite(
        this, "node", WebPortfoliosData.class, ManageablePortfolioNode.class);
    /**
     * The meta-property for the {@code versioned} property.
     */
    private final MetaProperty<PortfolioDocument> _versioned = DirectMetaProperty.ofReadWrite(
        this, "versioned", WebPortfoliosData.class, PortfolioDocument.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
        this, null,
        "portfolioMaster",
        "positionMaster",
        "uriInfo",
        "uriPortfolioId",
        "uriNodeId",
        "uriPositionId",
        "uriVersionId",
        "portfolio",
        "parentNode",
        "node",
        "versioned");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -772274742:  // portfolioMaster
          return _portfolioMaster;
        case -1840419605:  // positionMaster
          return _positionMaster;
        case -173275078:  // uriInfo
          return _uriInfo;
        case -72522889:  // uriPortfolioId
          return _uriPortfolioId;
        case 1130377033:  // uriNodeId
          return _uriNodeId;
        case 1240319664:  // uriPositionId
          return _uriPositionId;
        case 666567687:  // uriVersionId
          return _uriVersionId;
        case 1121781064:  // portfolio
          return _portfolio;
        case -244857396:  // parentNode
          return _parentNode;
        case 3386882:  // node
          return _node;
        case -1407102089:  // versioned
          return _versioned;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends WebPortfoliosData> builder() {
      return new BasicBeanBuilder<WebPortfoliosData>(new WebPortfoliosData());
    }

    @Override
    public Class<? extends WebPortfoliosData> beanType() {
      return WebPortfoliosData.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code portfolioMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PortfolioMaster> portfolioMaster() {
      return _portfolioMaster;
    }

    /**
     * The meta-property for the {@code positionMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PositionMaster> positionMaster() {
      return _positionMaster;
    }

    /**
     * The meta-property for the {@code uriInfo} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UriInfo> uriInfo() {
      return _uriInfo;
    }

    /**
     * The meta-property for the {@code uriPortfolioId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriPortfolioId() {
      return _uriPortfolioId;
    }

    /**
     * The meta-property for the {@code uriNodeId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriNodeId() {
      return _uriNodeId;
    }

    /**
     * The meta-property for the {@code uriPositionId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriPositionId() {
      return _uriPositionId;
    }

    /**
     * The meta-property for the {@code uriVersionId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriVersionId() {
      return _uriVersionId;
    }

    /**
     * The meta-property for the {@code portfolio} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PortfolioDocument> portfolio() {
      return _portfolio;
    }

    /**
     * The meta-property for the {@code parentNode} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ManageablePortfolioNode> parentNode() {
      return _parentNode;
    }

    /**
     * The meta-property for the {@code node} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ManageablePortfolioNode> node() {
      return _node;
    }

    /**
     * The meta-property for the {@code versioned} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PortfolioDocument> versioned() {
      return _versioned;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
