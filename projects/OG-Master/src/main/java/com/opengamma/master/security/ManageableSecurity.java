/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.security;

import java.io.Serializable;
import java.util.HashMap;
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

import com.opengamma.core.security.Security;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.MutableUniqueIdentifiable;
import com.opengamma.id.UniqueId;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.PublicSPI;

/**
 * A security that it may be possible to hold a position in.
 * <p>
 * A security generically defined as anything that a position can be held in.
 * This includes the security defined in "OTC" trades, permitting back-to-back
 * trades to be linked correctly.
 */
@PublicSPI
@BeanDefinition
public class ManageableSecurity extends DirectBean implements Serializable, Security, MutableUniqueIdentifiable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The security type name.
   */
  private static final String SECURITY_TYPE = "MANAGEABLE";

  /**
   * The unique identifier of the security.
   * This must be null when adding to a master and not null when retrieved from a master.
   */
  @PropertyDefinition
  private UniqueId _uniqueId;
  /**
   * The bundle of external identifiers that define the security.
   * Each external system will typically refer to a security using a different identifier.
   * Thus the bundle consists of a set of identifiers, one for each external system.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalIdBundle _externalIdBundle = ExternalIdBundle.EMPTY;
  /**
   * The name of the security intended for display purposes.
   */
  @PropertyDefinition(validate = "notNull")
  private String _name = "";
  /**
   * The security type.
   */
  @PropertyDefinition(validate = "notNull", set = "")
  private final String _securityType;

  /**
   * The general purpose trade attributes, which can be used for aggregating in portfolios.
   */
  @PropertyDefinition
  private final Map<String, String> _attributes = new HashMap<String, String>();

  /**
   * Creates an empty instance.
   * <p>
   * The security details should be set before use.
   */
  public ManageableSecurity() {
    _securityType = SECURITY_TYPE;
  }

  /**
   * Creates an instance with a security type.
   * 
   * @param securityType  the security type, not null
   */
  public ManageableSecurity(String securityType) {
    ArgumentChecker.notEmpty(securityType, "securityType");
    _securityType = securityType;
  }

  /**
   * Creates a fully populated instance.
   * 
   * @param uniqueId  the security unique identifier, may be null
   * @param name  the display name, not null
   * @param securityType  the security type, not null
   * @param bundle  the security external identifier bundle, not null
   */
  public ManageableSecurity(UniqueId uniqueId, String name, String securityType, ExternalIdBundle bundle) {
    this(securityType);
    setUniqueId(uniqueId);
    setName(name);
    setExternalIdBundle(bundle);
  }

  //-------------------------------------------------------------------------
  /**
   * Adds an external identifier to the bundle representing this security.
   * 
   * @param externalId  the identifier to add, not null
   */
  public void addExternalId(final ExternalId externalId) {
    setExternalIdBundle(getExternalIdBundle().withExternalId(externalId));
  }

  @Override
  public void addAttribute(String key, String value) {
    ArgumentChecker.notNull(key, "key");
    ArgumentChecker.notNull(value, "value");
    _attributes.put(key, value);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ManageableSecurity}.
   * @return the meta-bean, not null
   */
  public static ManageableSecurity.Meta meta() {
    return ManageableSecurity.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ManageableSecurity.Meta.INSTANCE);
  }

  @Override
  public ManageableSecurity.Meta metaBean() {
    return ManageableSecurity.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -294460212:  // uniqueId
        return getUniqueId();
      case -736922008:  // externalIdBundle
        return getExternalIdBundle();
      case 3373707:  // name
        return getName();
      case 808245914:  // securityType
        return getSecurityType();
      case 405645655:  // attributes
        return getAttributes();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -294460212:  // uniqueId
        setUniqueId((UniqueId) newValue);
        return;
      case -736922008:  // externalIdBundle
        setExternalIdBundle((ExternalIdBundle) newValue);
        return;
      case 3373707:  // name
        setName((String) newValue);
        return;
      case 808245914:  // securityType
        if (quiet) {
          return;
        }
        throw new UnsupportedOperationException("Property cannot be written: securityType");
      case 405645655:  // attributes
        setAttributes((Map<String, String>) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_externalIdBundle, "externalIdBundle");
    JodaBeanUtils.notNull(_name, "name");
    JodaBeanUtils.notNull(_securityType, "securityType");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ManageableSecurity other = (ManageableSecurity) obj;
      return JodaBeanUtils.equal(getUniqueId(), other.getUniqueId()) &&
          JodaBeanUtils.equal(getExternalIdBundle(), other.getExternalIdBundle()) &&
          JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(getSecurityType(), other.getSecurityType()) &&
          JodaBeanUtils.equal(getAttributes(), other.getAttributes());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getUniqueId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExternalIdBundle());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSecurityType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getAttributes());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the unique identifier of the security.
   * This must be null when adding to a master and not null when retrieved from a master.
   * @return the value of the property
   */
  public UniqueId getUniqueId() {
    return _uniqueId;
  }

  /**
   * Sets the unique identifier of the security.
   * This must be null when adding to a master and not null when retrieved from a master.
   * @param uniqueId  the new value of the property
   */
  public void setUniqueId(UniqueId uniqueId) {
    this._uniqueId = uniqueId;
  }

  /**
   * Gets the the {@code uniqueId} property.
   * This must be null when adding to a master and not null when retrieved from a master.
   * @return the property, not null
   */
  public final Property<UniqueId> uniqueId() {
    return metaBean().uniqueId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the bundle of external identifiers that define the security.
   * Each external system will typically refer to a security using a different identifier.
   * Thus the bundle consists of a set of identifiers, one for each external system.
   * @return the value of the property, not null
   */
  public ExternalIdBundle getExternalIdBundle() {
    return _externalIdBundle;
  }

  /**
   * Sets the bundle of external identifiers that define the security.
   * Each external system will typically refer to a security using a different identifier.
   * Thus the bundle consists of a set of identifiers, one for each external system.
   * @param externalIdBundle  the new value of the property, not null
   */
  public void setExternalIdBundle(ExternalIdBundle externalIdBundle) {
    JodaBeanUtils.notNull(externalIdBundle, "externalIdBundle");
    this._externalIdBundle = externalIdBundle;
  }

  /**
   * Gets the the {@code externalIdBundle} property.
   * Each external system will typically refer to a security using a different identifier.
   * Thus the bundle consists of a set of identifiers, one for each external system.
   * @return the property, not null
   */
  public final Property<ExternalIdBundle> externalIdBundle() {
    return metaBean().externalIdBundle().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name of the security intended for display purposes.
   * @return the value of the property, not null
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the name of the security intended for display purposes.
   * @param name  the new value of the property, not null
   */
  public void setName(String name) {
    JodaBeanUtils.notNull(name, "name");
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the security type.
   * @return the value of the property, not null
   */
  public String getSecurityType() {
    return _securityType;
  }

  /**
   * Gets the the {@code securityType} property.
   * @return the property, not null
   */
  public final Property<String> securityType() {
    return metaBean().securityType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the general purpose trade attributes, which can be used for aggregating in portfolios.
   * @return the value of the property
   */
  public Map<String, String> getAttributes() {
    return _attributes;
  }

  /**
   * Sets the general purpose trade attributes, which can be used for aggregating in portfolios.
   * @param attributes  the new value of the property
   */
  public void setAttributes(Map<String, String> attributes) {
    this._attributes.clear();
    this._attributes.putAll(attributes);
  }

  /**
   * Gets the the {@code attributes} property.
   * @return the property, not null
   */
  public final Property<Map<String, String>> attributes() {
    return metaBean().attributes().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ManageableSecurity}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code uniqueId} property.
     */
    private final MetaProperty<UniqueId> _uniqueId = DirectMetaProperty.ofReadWrite(
        this, "uniqueId", ManageableSecurity.class, UniqueId.class);
    /**
     * The meta-property for the {@code externalIdBundle} property.
     */
    private final MetaProperty<ExternalIdBundle> _externalIdBundle = DirectMetaProperty.ofReadWrite(
        this, "externalIdBundle", ManageableSecurity.class, ExternalIdBundle.class);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", ManageableSecurity.class, String.class);
    /**
     * The meta-property for the {@code securityType} property.
     */
    private final MetaProperty<String> _securityType = DirectMetaProperty.ofReadOnly(
        this, "securityType", ManageableSecurity.class, String.class);
    /**
     * The meta-property for the {@code attributes} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<String, String>> _attributes = DirectMetaProperty.ofReadWrite(
        this, "attributes", ManageableSecurity.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "uniqueId",
        "externalIdBundle",
        "name",
        "securityType",
        "attributes");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -294460212:  // uniqueId
          return _uniqueId;
        case -736922008:  // externalIdBundle
          return _externalIdBundle;
        case 3373707:  // name
          return _name;
        case 808245914:  // securityType
          return _securityType;
        case 405645655:  // attributes
          return _attributes;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ManageableSecurity> builder() {
      return new DirectBeanBuilder<ManageableSecurity>(new ManageableSecurity());
    }

    @Override
    public Class<? extends ManageableSecurity> beanType() {
      return ManageableSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code uniqueId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueId> uniqueId() {
      return _uniqueId;
    }

    /**
     * The meta-property for the {@code externalIdBundle} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalIdBundle> externalIdBundle() {
      return _externalIdBundle;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code securityType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> securityType() {
      return _securityType;
    }

    /**
     * The meta-property for the {@code attributes} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<String, String>> attributes() {
      return _attributes;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------

}
