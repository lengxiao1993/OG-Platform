/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.curve;

import java.util.Map;
import java.util.Set;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.core.config.Config;
import com.opengamma.financial.analytics.ircurve.strips.CurveNode;

/**
 * Definition for interpolated curves. It contains the curve name, nodes and information about the interpolators. The interpolation method
 * must be provided, but the left and right extrapolation methods are optional.
 */
@BeanDefinition
@Config(description = "Interpolated curve definition")
public class InterpolatedCurveDefinition extends CurveDefinition {

  /** Serialization version */
  private static final long serialVersionUID = 1L;

  /**
   * The interpolator name.
   */
  @PropertyDefinition(validate = "notNull")
  private String _interpolatorName;

  /**
   * The right extrapolator name.
   */
  @PropertyDefinition
  private String _rightExtrapolatorName;

  /**
   * The left extrapolator name.
   */
  @PropertyDefinition
  private String _leftExtrapolatorName;

  /**
   * For the builder.
   */
  /* package*/InterpolatedCurveDefinition() {
  }

  /**
   * @param name The name of the curve, not null
   * @param nodes The nodes of the curve, not null
   * @param interpolatorName The interpolator name, not null
   */
  public InterpolatedCurveDefinition(final String name, final Set<CurveNode> nodes, final String interpolatorName) {
    super(name, nodes);
    setInterpolatorName(interpolatorName);
  }

  /**
   * @param name The name of the curve, not null
   * @param nodes The nodes of the curve, not null
   * @param interpolatorName The interpolator name, not null
   * @param extrapolatorName The name of the left and right extrapolators
   */
  public InterpolatedCurveDefinition(final String name, final Set<CurveNode> nodes, final String interpolatorName, final String extrapolatorName) {
    super(name, nodes);
    setInterpolatorName(interpolatorName);
    setRightExtrapolatorName(extrapolatorName);
    setLeftExtrapolatorName(extrapolatorName);
  }

  /**
   * @param name The name of the curve, not null
   * @param nodes The nodes of the curve, not null
   * @param interpolatorName The interpolator name, not null
   * @param rightExtrapolatorName The right extrapolator name
   * @param leftExtrapolatorName The left extrapolator name
   */
  public InterpolatedCurveDefinition(final String name, final Set<CurveNode> nodes, final String interpolatorName, final String rightExtrapolatorName, final String leftExtrapolatorName) {
    super(name, nodes);
    setInterpolatorName(interpolatorName);
    setRightExtrapolatorName(rightExtrapolatorName);
    setLeftExtrapolatorName(leftExtrapolatorName);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code InterpolatedCurveDefinition}.
   * @return the meta-bean, not null
   */
  public static InterpolatedCurveDefinition.Meta meta() {
    return InterpolatedCurveDefinition.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(InterpolatedCurveDefinition.Meta.INSTANCE);
  }

  @Override
  public InterpolatedCurveDefinition.Meta metaBean() {
    return InterpolatedCurveDefinition.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -1247314958:  // interpolatorName
        return getInterpolatorName();
      case -556150150:  // rightExtrapolatorName
        return getRightExtrapolatorName();
      case -718701979:  // leftExtrapolatorName
        return getLeftExtrapolatorName();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -1247314958:  // interpolatorName
        setInterpolatorName((String) newValue);
        return;
      case -556150150:  // rightExtrapolatorName
        setRightExtrapolatorName((String) newValue);
        return;
      case -718701979:  // leftExtrapolatorName
        setLeftExtrapolatorName((String) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_interpolatorName, "interpolatorName");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      InterpolatedCurveDefinition other = (InterpolatedCurveDefinition) obj;
      return JodaBeanUtils.equal(getInterpolatorName(), other.getInterpolatorName()) &&
          JodaBeanUtils.equal(getRightExtrapolatorName(), other.getRightExtrapolatorName()) &&
          JodaBeanUtils.equal(getLeftExtrapolatorName(), other.getLeftExtrapolatorName()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getInterpolatorName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRightExtrapolatorName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getLeftExtrapolatorName());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the interpolator name.
   * @return the value of the property, not null
   */
  public String getInterpolatorName() {
    return _interpolatorName;
  }

  /**
   * Sets the interpolator name.
   * @param interpolatorName  the new value of the property, not null
   */
  public void setInterpolatorName(String interpolatorName) {
    JodaBeanUtils.notNull(interpolatorName, "interpolatorName");
    this._interpolatorName = interpolatorName;
  }

  /**
   * Gets the the {@code interpolatorName} property.
   * @return the property, not null
   */
  public final Property<String> interpolatorName() {
    return metaBean().interpolatorName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the right extrapolator name.
   * @return the value of the property
   */
  public String getRightExtrapolatorName() {
    return _rightExtrapolatorName;
  }

  /**
   * Sets the right extrapolator name.
   * @param rightExtrapolatorName  the new value of the property
   */
  public void setRightExtrapolatorName(String rightExtrapolatorName) {
    this._rightExtrapolatorName = rightExtrapolatorName;
  }

  /**
   * Gets the the {@code rightExtrapolatorName} property.
   * @return the property, not null
   */
  public final Property<String> rightExtrapolatorName() {
    return metaBean().rightExtrapolatorName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the left extrapolator name.
   * @return the value of the property
   */
  public String getLeftExtrapolatorName() {
    return _leftExtrapolatorName;
  }

  /**
   * Sets the left extrapolator name.
   * @param leftExtrapolatorName  the new value of the property
   */
  public void setLeftExtrapolatorName(String leftExtrapolatorName) {
    this._leftExtrapolatorName = leftExtrapolatorName;
  }

  /**
   * Gets the the {@code leftExtrapolatorName} property.
   * @return the property, not null
   */
  public final Property<String> leftExtrapolatorName() {
    return metaBean().leftExtrapolatorName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code InterpolatedCurveDefinition}.
   */
  public static class Meta extends CurveDefinition.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code interpolatorName} property.
     */
    private final MetaProperty<String> _interpolatorName = DirectMetaProperty.ofReadWrite(
        this, "interpolatorName", InterpolatedCurveDefinition.class, String.class);
    /**
     * The meta-property for the {@code rightExtrapolatorName} property.
     */
    private final MetaProperty<String> _rightExtrapolatorName = DirectMetaProperty.ofReadWrite(
        this, "rightExtrapolatorName", InterpolatedCurveDefinition.class, String.class);
    /**
     * The meta-property for the {@code leftExtrapolatorName} property.
     */
    private final MetaProperty<String> _leftExtrapolatorName = DirectMetaProperty.ofReadWrite(
        this, "leftExtrapolatorName", InterpolatedCurveDefinition.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "interpolatorName",
        "rightExtrapolatorName",
        "leftExtrapolatorName");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1247314958:  // interpolatorName
          return _interpolatorName;
        case -556150150:  // rightExtrapolatorName
          return _rightExtrapolatorName;
        case -718701979:  // leftExtrapolatorName
          return _leftExtrapolatorName;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends InterpolatedCurveDefinition> builder() {
      return new DirectBeanBuilder<InterpolatedCurveDefinition>(new InterpolatedCurveDefinition());
    }

    @Override
    public Class<? extends InterpolatedCurveDefinition> beanType() {
      return InterpolatedCurveDefinition.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code interpolatorName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> interpolatorName() {
      return _interpolatorName;
    }

    /**
     * The meta-property for the {@code rightExtrapolatorName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> rightExtrapolatorName() {
      return _rightExtrapolatorName;
    }

    /**
     * The meta-property for the {@code leftExtrapolatorName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> leftExtrapolatorName() {
      return _leftExtrapolatorName;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
