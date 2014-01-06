/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.bondcurves;

import static com.opengamma.engine.value.ValuePropertyNames.CURVE;
import static com.opengamma.engine.value.ValueRequirementNames.CURVE_BUNDLE;
import static com.opengamma.engine.value.ValueRequirementNames.PV01;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;

import com.google.common.collect.Iterables;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivative;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivativeVisitor;
import com.opengamma.analytics.financial.provider.calculator.discounting.PV01CurveParametersCalculator;
import com.opengamma.analytics.financial.provider.calculator.issuer.PresentValueCurveSensitivityIssuerCalculator;
import com.opengamma.analytics.financial.provider.description.interestrate.ParameterIssuerProviderInterface;
import com.opengamma.analytics.util.amount.ReferenceAmount;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.analytics.model.BondFunctionUtils;
import com.opengamma.util.async.AsynchronousExecution;
import com.opengamma.util.money.Currency;
import com.opengamma.util.tuple.Pair;

/**
 * Calculates the PV01 of a bond or bond future for all curves to which the instruments are sensitive.
 */
public class BondPV01Function extends BondFromCurvesFunction<ParameterIssuerProviderInterface, ReferenceAmount<Pair<String, Currency>>> {
  /** The logger */
  private static final Logger s_logger = LoggerFactory.getLogger(BondPV01Function.class);
  /** The PV01 calculator */
  private static final InstrumentDerivativeVisitor<ParameterIssuerProviderInterface, ReferenceAmount<Pair<String, Currency>>> CALCULATOR =
      new PV01CurveParametersCalculator<>(PresentValueCurveSensitivityIssuerCalculator.getInstance());

  /**
   * Sets the value requirement name to {@link ValueRequirementNames#PV01} and
   * sets the calculator to {@link PV01CurveParametersCalculator}
   */
  public BondPV01Function() {
    super(PV01, CALCULATOR);
  }

  @Override
  public Set<ComputedValue> execute(final FunctionExecutionContext context, final FunctionInputs inputs, final ComputationTarget target,
      final Set<ValueRequirement> desiredValues) throws AsynchronousExecution {
    final ValueRequirement desiredValue = Iterables.getOnlyElement(desiredValues);
    final ValueProperties properties = desiredValue.getConstraints();
    final ZonedDateTime now = ZonedDateTime.now(context.getValuationClock());
    final InstrumentDerivative derivative = BondFunctionUtils.getDerivative(context, target, now);
    final ParameterIssuerProviderInterface issuerCurves = (ParameterIssuerProviderInterface) inputs.getValue(CURVE_BUNDLE);
    final String desiredCurveName = properties.getStrictValue(CURVE);
    final ReferenceAmount<Pair<String, Currency>> pv01 = derivative.accept(CALCULATOR, issuerCurves);
    final Set<ComputedValue> results = new HashSet<>();
    boolean curveNameFound = false;
    for (final Map.Entry<Pair<String, Currency>, Double> entry : pv01.getMap().entrySet()) {
      final String curveName = entry.getKey().getFirst();
      if (desiredCurveName.equals(curveName)) {
        curveNameFound = true;
      }
      final ValueProperties curveSpecificProperties = properties.copy()
          .withoutAny(CURVE)
          .with(CURVE, curveName)
          .get();
      final ValueSpecification spec = new ValueSpecification(PV01, target.toSpecification(), curveSpecificProperties);
      results.add(new ComputedValue(spec, entry.getValue()));
    }
    if (!curveNameFound) {
      s_logger.error("Could not get sensitivities to " + desiredCurveName + " for " + target.getName());
      return Collections.emptySet();
    }
    return results;
  }

  @Override
  public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    final Set<String> curveNames = desiredValue.getConstraints().getValues(CURVE);
    if (curveNames == null || curveNames.size() != 1) {
      return null;
    }
    return super.getRequirements(context, target, desiredValue);
  }

  @Override
  protected ValueProperties.Builder getResultProperties(final ComputationTarget target) {
    return super.getResultProperties(target)
        .withAny(CURVE);
  }

}