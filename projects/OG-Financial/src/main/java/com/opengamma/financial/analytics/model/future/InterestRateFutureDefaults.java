/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.future;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.target.ComputationTargetType;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.financial.analytics.OpenGammaFunctionExclusions;
import com.opengamma.financial.analytics.model.multicurve.MultiCurvePricingFunction;
import com.opengamma.financial.property.DefaultPropertyFunction;
import com.opengamma.financial.security.FinancialSecurityUtils;
import com.opengamma.financial.security.future.InterestRateFutureSecurity;
import com.opengamma.util.ArgumentChecker;

/**
 * Dummy function for injecting default curve names into the dependency graph.
 * @deprecated These properties are no longer needed when using {@link MultiCurvePricingFunction}
 * and related classes.
 */
@Deprecated
public class InterestRateFutureDefaults extends DefaultPropertyFunction {
  private static final Logger s_logger = LoggerFactory.getLogger(InterestRateFutureDefaults.class);
  private static final String[] s_valueNames = new String[] {
    ValueRequirementNames.PRESENT_VALUE,
    ValueRequirementNames.PV01,
    ValueRequirementNames.YIELD_CURVE_NODE_SENSITIVITIES,
    ValueRequirementNames.VALUE_THETA
  };
  private final Map<String, String> _currencyAndCurveConfigNames;

  public InterestRateFutureDefaults(final String... currencyAndCurveConfigNames) {
    super(ComputationTargetType.TRADE, true);
    ArgumentChecker.notNull(currencyAndCurveConfigNames, "currency and curve config names");
    final int nPairs = currencyAndCurveConfigNames.length;
    ArgumentChecker.isTrue(nPairs % 2 == 0, "Must have one curve config name per currency");
    _currencyAndCurveConfigNames = new HashMap<>();
    for (int i = 0; i < currencyAndCurveConfigNames.length; i += 2) {
      _currencyAndCurveConfigNames.put(currencyAndCurveConfigNames[i], currencyAndCurveConfigNames[i + 1]);
    }
  }

  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    if (!(target.getTrade().getSecurity() instanceof InterestRateFutureSecurity)) {
      return false;
    }
    final String currency = FinancialSecurityUtils.getCurrency(target.getTrade().getSecurity()).getCode();
    return _currencyAndCurveConfigNames.containsKey(currency);
  }

  @Override
  protected void getDefaults(final PropertyDefaults defaults) {
    for (final String valueName : s_valueNames) {
      defaults.addValuePropertyName(valueName, ValuePropertyNames.CURVE_CALCULATION_CONFIG);
    }
  }

  @Override
  protected Set<String> getDefaultValue(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue, final String propertyName) {
    if (ValuePropertyNames.CURVE_CALCULATION_CONFIG.equals(propertyName)) {
      final String currencyName = FinancialSecurityUtils.getCurrency(target.getTrade().getSecurity()).getCode();
      final String configName = _currencyAndCurveConfigNames.get(currencyName);
      if (configName == null) {
        s_logger.error("Could not get config for currency " + currencyName + "; should never happen");
        return null;
      }
      return Collections.singleton(configName);
    }
    return null;
  }

  @Override
  public String getMutualExclusionGroup() {
    return OpenGammaFunctionExclusions.INTEREST_RATE_FUTURE;
  }

}
