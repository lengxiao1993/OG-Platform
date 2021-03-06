/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.provider.description.interestrate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opengamma.analytics.financial.forex.method.FXMatrix;
import com.opengamma.analytics.financial.instrument.index.IborIndex;
import com.opengamma.analytics.financial.instrument.index.IndexON;
import com.opengamma.analytics.financial.model.interestrate.curve.YieldAndDiscountCurve;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.Currency;
import com.opengamma.util.tuple.Pair;

/**
 * Class describing a provider with multi-curves and issuer specific curves.
 * The forward rate are computed as the ratio of discount factors stored in {@link YieldAndDiscountCurve}.
 */
public class IssuerProviderDiscount extends IssuerProvider {

  /**
   * Constructs an empty multi-curve provider and issuer curve map.
   */
  public IssuerProviderDiscount() {
    super();
  }

  /**
   * Constructs and empty multi-curve provider and issuer curve map.
   * @param fxMatrix The FX matrix, not null
   */
  public IssuerProviderDiscount(final FXMatrix fxMatrix) {
    super(fxMatrix);
  }

  /**
   * @param discountingCurves A map from currency to yield curve, not null
   * @param forwardIborCurves A map from ibor index to yield curve, not null
   * @param forwardONCurves A map from overnight index to yield curve, not null
   * @param fxMatrix The FX matrix, not null
   */
  public IssuerProviderDiscount(final Map<Currency, YieldAndDiscountCurve> discountingCurves, final Map<IborIndex, YieldAndDiscountCurve> forwardIborCurves,
      final Map<IndexON, YieldAndDiscountCurve> forwardONCurves, final FXMatrix fxMatrix) {
    super(discountingCurves, forwardIborCurves, forwardONCurves, fxMatrix);
  }

  /**
   * @param discountingCurves A map from currency to yield curve, not null
   * @param forwardIborCurves A map from ibor index to yield curve, not null
   * @param forwardONCurves A map from overnight index to yield curve, not null
   * @param issuerCurves The issuer curves, not null
   * @param fxMatrix The FX matrix, not null
   */
  public IssuerProviderDiscount(final Map<Currency, YieldAndDiscountCurve> discountingCurves, final Map<IborIndex, YieldAndDiscountCurve> forwardIborCurves,
      final Map<IndexON, YieldAndDiscountCurve> forwardONCurves, final Map<Pair<String, Currency>, YieldAndDiscountCurve> issuerCurves, final FXMatrix fxMatrix) {
    super(discountingCurves, forwardIborCurves, forwardONCurves, issuerCurves, fxMatrix);
  }

  /**
   * Constructor from exiting multi-curve provider and issuer map. The given provider and map are used for the new provider (the same maps are used, not copied).
   * @param multicurve The multi-curves provider.
   * @param issuerCurves The issuer specific curves.
   */
  public IssuerProviderDiscount(final MulticurveProviderDiscount multicurve, final Map<Pair<String, Currency>, YieldAndDiscountCurve> issuerCurves) {
    super(multicurve, issuerCurves);
  }

  /**
   * Constructs a provider from an existing multi-curve provider. The maps are not copied
   * @param multicurve The multi-curve provider, notn ull
   */
  public IssuerProviderDiscount(final MulticurveProviderDiscount multicurve) {
    super(multicurve);
  }

  @Override
  public MulticurveProviderDiscount getMulticurveProvider() {
    return (MulticurveProviderDiscount) super.getMulticurveProvider();
  }

  @Override
  public IssuerProviderDiscount getIssuerProvider() {
    return this;
  }

  @Override
  public IssuerProviderDiscount copy() {
    final Map<Pair<String, Currency>, YieldAndDiscountCurve> issuerCurvesNew = new HashMap<>(getIssuerCurves());
    return new IssuerProviderDiscount(getMulticurveProvider().copy(), issuerCurvesNew);
  }

  public void setCurve(final Currency ccy, final YieldAndDiscountCurve curve) {
    getMulticurveProvider().setCurve(ccy, curve);
    // TODO: Should we make sure that we don't set the _multicurveProvider directly (without the list update)
    init();
  }

  public void setCurve(final IborIndex index, final YieldAndDiscountCurve curve) {
    getMulticurveProvider().setCurve(index, curve);
    init();
  }

  public void setCurve(final IndexON index, final YieldAndDiscountCurve curve) {
    getMulticurveProvider().setCurve(index, curve);
    init();
  }

  /**
   * Set all the curves contains in another provider. If a currency, index, or issuer is already present in the map, the associated curve is changed.
   * @param other The other provider.
   */
  public void setAll(final IssuerProviderDiscount other) {
    ArgumentChecker.notNull(other, "Market bundle");
    getMulticurveProvider().setAll(other.getMulticurveProvider());
    getIssuerCurves().putAll(other.getIssuerCurves());
    init();
  }

  @Override
  public IssuerProviderDiscount withIssuerCurrency(final Pair<String, Currency> ic, final YieldAndDiscountCurve replacement) {
    final Map<Pair<String, Currency>, YieldAndDiscountCurve> newIssuerCurves = new LinkedHashMap<>(getIssuerCurves());
    newIssuerCurves.put(ic, replacement);
    return new IssuerProviderDiscount(getMulticurveProvider(), newIssuerCurves);
  }

}
