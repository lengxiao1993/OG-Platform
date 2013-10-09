/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.ircurve.strips;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.time.Tenor;

/**
 *
 */
@BeanDefinition
public class DiscountFactorNode extends CurveNode {

  /** Serialization version */
  private static final long serialVersionUID = 1L;

  /**
   * The tenor.
   */
  @PropertyDefinition(validate = "notNull")
  private Tenor _tenor;

  /* package*/DiscountFactorNode() {
    super();
  }

  /**
   * @param curveNodeIdMapperName The curve node id mapper name, not null
   * @param tenor The tenor, not null
   */
  public DiscountFactorNode(final String curveNodeIdMapperName, final Tenor tenor) {
    super(curveNodeIdMapperName);
    setTenor(tenor);
  }

    /**
     * @param curveNodeIdMapperName The curve node id mapper name, not null
     * @param tenor The tenor, not null
     * @param name The name
     */
  public DiscountFactorNode(final String curveNodeIdMapperName, final Tenor tenor, final String name) {
    super(curveNodeIdMapperName, name);
    setTenor(tenor);
  }

  @Override
  public Tenor getResolvedMaturity() {
    return _tenor;
  }

  @Override
  public <T> T accept(final CurveNodeVisitor<T> visitor) {
    ArgumentChecker.notNull(visitor, "visitor");
    return visitor.visitDiscountFactorNode(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DiscountFactorNode}.
   * @return the meta-bean, not null
   */
  public static DiscountFactorNode.Meta meta() {
    return DiscountFactorNode.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DiscountFactorNode.Meta.INSTANCE);
  }

  @Override
  public DiscountFactorNode.Meta metaBean() {
    return DiscountFactorNode.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 110246592:  // tenor
        return getTenor();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 110246592:  // tenor
        setTenor((Tenor) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_tenor, "tenor");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DiscountFactorNode other = (DiscountFactorNode) obj;
      return JodaBeanUtils.equal(getTenor(), other.getTenor()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getTenor());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the tenor.
   * @return the value of the property, not null
   */
  public Tenor getTenor() {
    return _tenor;
  }

  /**
   * Sets the tenor.
   * @param tenor  the new value of the property, not null
   */
  public void setTenor(Tenor tenor) {
    JodaBeanUtils.notNull(tenor, "tenor");
    this._tenor = tenor;
  }

  /**
   * Gets the the {@code tenor} property.
   * @return the property, not null
   */
  public final Property<Tenor> tenor() {
    return metaBean().tenor().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DiscountFactorNode}.
   */
  public static class Meta extends CurveNode.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code tenor} property.
     */
    private final MetaProperty<Tenor> _tenor = DirectMetaProperty.ofReadWrite(
        this, "tenor", DiscountFactorNode.class, Tenor.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "tenor");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 110246592:  // tenor
          return _tenor;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DiscountFactorNode> builder() {
      return new DirectBeanBuilder<DiscountFactorNode>(new DiscountFactorNode());
    }

    @Override
    public Class<? extends DiscountFactorNode> beanType() {
      return DiscountFactorNode.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code tenor} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Tenor> tenor() {
      return _tenor;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
