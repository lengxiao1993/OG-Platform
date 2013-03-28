/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.interestrate.future.provider;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;
import org.threeten.bp.Period;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.instrument.future.DeliverableSwapFuturesSecurityDefinition;
import com.opengamma.analytics.financial.instrument.future.DeliverableSwapFuturesTransactionDefinition;
import com.opengamma.analytics.financial.instrument.index.GeneratorSwapFixedIbor;
import com.opengamma.analytics.financial.instrument.index.GeneratorSwapFixedIborMaster;
import com.opengamma.analytics.financial.instrument.index.IborIndex;
import com.opengamma.analytics.financial.instrument.swap.SwapFixedIborDefinition;
import com.opengamma.analytics.financial.interestrate.future.derivative.DeliverableSwapFuturesTransaction;
import com.opengamma.analytics.financial.model.interestrate.TestsDataSetHullWhite;
import com.opengamma.analytics.financial.model.interestrate.definition.HullWhiteOneFactorPiecewiseConstantParameters;
import com.opengamma.analytics.financial.provider.calculator.hullwhite.PresentValueCurveSensitivityHullWhiteCalculator;
import com.opengamma.analytics.financial.provider.calculator.hullwhite.PresentValueHullWhiteCalculator;
import com.opengamma.analytics.financial.provider.description.MulticurveProviderDiscountDataSets;
import com.opengamma.analytics.financial.provider.description.interestrate.HullWhiteOneFactorProviderDiscount;
import com.opengamma.analytics.financial.provider.description.interestrate.MulticurveProviderDiscount;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.MulticurveSensitivity;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.MultipleCurrencyMulticurveSensitivity;
import com.opengamma.analytics.financial.schedule.ScheduleCalculator;
import com.opengamma.analytics.financial.util.AssertSensivityObjects;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.util.money.Currency;
import com.opengamma.util.money.MultipleCurrencyAmount;
import com.opengamma.util.time.DateUtils;

public class DeliverableSwapFuturesTransactionHullWhiteMethodTest {

  private static final String NOT_USED = "Not used";
  private static final String[] NOT_USED_A = {NOT_USED, NOT_USED, NOT_USED };

  private static final MulticurveProviderDiscount MULTICURVES = MulticurveProviderDiscountDataSets.createMulticurveEurUsd();
  private static final IborIndex[] INDEX_LIST = MulticurveProviderDiscountDataSets.getIndexesIborMulticurveEurUsd();
  private static final IborIndex USDLIBOR3M = INDEX_LIST[2];
  private static final Currency USD = USDLIBOR3M.getCurrency();
  private static final Calendar NYC = USDLIBOR3M.getCalendar();
  private static final GeneratorSwapFixedIbor USD6MLIBOR3M = GeneratorSwapFixedIborMaster.getInstance().getGenerator("USD6MLIBOR3M", NYC);
  private static final ZonedDateTime EFFECTIVE_DATE = DateUtils.getUTCDate(2013, 6, 19);
  private static final ZonedDateTime LAST_TRADING_DATE = ScheduleCalculator.getAdjustedDate(EFFECTIVE_DATE, -USD6MLIBOR3M.getSpotLag(), NYC);
  private static final Period TENOR = Period.ofYears(10);
  private static final double NOTIONAL = 100000;
  private static final double RATE = 0.0175;
  private static final SwapFixedIborDefinition SWAP_DEFINITION = SwapFixedIborDefinition.from(EFFECTIVE_DATE, TENOR, USD6MLIBOR3M, 1.0, RATE, false);
  private static final DeliverableSwapFuturesSecurityDefinition SWAP_FUTURES_SECURITY_DEFINITION = new DeliverableSwapFuturesSecurityDefinition(LAST_TRADING_DATE, SWAP_DEFINITION, NOTIONAL);
  private static final ZonedDateTime TRAN_DATE = DateUtils.getUTCDate(2013, 3, 28);
  private static final double TRAN_PRICE = 0.98 + 31.0 / 32.0 / 100.0; // price quoted in 32nd of 1%
  private static final int QUANTITY = 1234;
  private static final DeliverableSwapFuturesTransactionDefinition SWAP_FUTURES_TRANSACTION_DEFINITION =
      new DeliverableSwapFuturesTransactionDefinition(SWAP_FUTURES_SECURITY_DEFINITION, TRAN_DATE, TRAN_PRICE, QUANTITY);

  private static final ZonedDateTime REFERENCE_DATE = DateUtils.getUTCDate(2013, 3, 28);
  private static final double LASTMARG_PRICE = 0.99 + 8.0 / 32.0 / 100.0; // price quoted in 32nd of 1%
  private static final DeliverableSwapFuturesTransaction SWAP_FUTURES_TRANSACTION = SWAP_FUTURES_TRANSACTION_DEFINITION.toDerivative(REFERENCE_DATE, LASTMARG_PRICE, NOT_USED_A);

  private static final HullWhiteOneFactorPiecewiseConstantParameters PARAMETERS_HW = TestsDataSetHullWhite.createHullWhiteParameters();
  private static final HullWhiteOneFactorProviderDiscount MULTICURVES_HW = new HullWhiteOneFactorProviderDiscount(MULTICURVES, PARAMETERS_HW, USD);

  private static final DeliverableSwapFuturesSecurityHullWhiteMethod METHOD_SWAP_FUT_SEC_HW = DeliverableSwapFuturesSecurityHullWhiteMethod.getInstance();
  private static final DeliverableSwapFuturesTransactionHullWhiteMethod METHOD_SWAP_FUT_TRA_HW = DeliverableSwapFuturesTransactionHullWhiteMethod.getInstance();
  private static final PresentValueHullWhiteCalculator PVHWC = PresentValueHullWhiteCalculator.getInstance();
  private static final PresentValueCurveSensitivityHullWhiteCalculator PVCSHWC = PresentValueCurveSensitivityHullWhiteCalculator.getInstance();

  private static final double TOLERANCE_PV = 1.0E-2;
  private static final double TOLERANCE_PV_DELTA = 1.0E-8;

  @Test
  public void presentValue() {
    double price = METHOD_SWAP_FUT_SEC_HW.price(SWAP_FUTURES_TRANSACTION.getUnderlying(), MULTICURVES_HW);
    MultipleCurrencyAmount pvComputed = METHOD_SWAP_FUT_TRA_HW.presentValue(SWAP_FUTURES_TRANSACTION, MULTICURVES_HW);
    MultipleCurrencyAmount pvExpected1 = METHOD_SWAP_FUT_TRA_HW.presentValueFromPrice(SWAP_FUTURES_TRANSACTION, price);
    assertEquals("DeliverableSwapFuturesTransactionHullWhiteMethod: present value", pvExpected1.getAmount(USD), pvComputed.getAmount(USD), TOLERANCE_PV);
    double pvExpected2 = (price - SWAP_FUTURES_TRANSACTION.getReferencePrice()) * SWAP_FUTURES_SECURITY_DEFINITION.getNotional() * QUANTITY;
    assertEquals("DeliverableSwapFuturesTransactionHullWhiteMethod: present value", pvExpected2, pvComputed.getAmount(USD), TOLERANCE_PV);
  }

  @Test
  public void presentValueMethodVsCalculator() {
    MultipleCurrencyAmount pvMethod = METHOD_SWAP_FUT_TRA_HW.presentValue(SWAP_FUTURES_TRANSACTION, MULTICURVES_HW);
    MultipleCurrencyAmount pvCalculator = SWAP_FUTURES_TRANSACTION.accept(PVHWC, MULTICURVES_HW);
    assertEquals("DeliverableSwapFuturesTransactionHullWhiteMethod: present value", pvMethod.getAmount(USD), pvCalculator.getAmount(USD), TOLERANCE_PV);
  }

  @Test
  public void presentValueCurveSensitivity() {
    MulticurveSensitivity pricecs = METHOD_SWAP_FUT_SEC_HW.priceCurveSensitivity(SWAP_FUTURES_TRANSACTION.getUnderlying(), MULTICURVES_HW);
    MultipleCurrencyMulticurveSensitivity pvcsComputed = METHOD_SWAP_FUT_TRA_HW.presentValueCurveSensitivity(SWAP_FUTURES_TRANSACTION, MULTICURVES_HW);
    MultipleCurrencyMulticurveSensitivity pvcsExpected1 = METHOD_SWAP_FUT_TRA_HW.presentValueCurveSensitivity(SWAP_FUTURES_TRANSACTION, pricecs);
    AssertSensivityObjects.assertEquals("DeliverableSwapFuturesTransactionHullWhiteMethod: present value curve sensitivity",
        pvcsExpected1, pvcsComputed, TOLERANCE_PV_DELTA);
    MultipleCurrencyMulticurveSensitivity pvcsExpected2 =
        MultipleCurrencyMulticurveSensitivity.of(USD, pricecs.multipliedBy(SWAP_FUTURES_SECURITY_DEFINITION.getNotional() * QUANTITY));
    AssertSensivityObjects.assertEquals("DeliverableSwapFuturesTransactionHullWhiteMethod: present value curve sensitivity",
        pvcsExpected2, pvcsComputed, TOLERANCE_PV_DELTA);
  }

  @Test
  public void presentValueCurveSensitivityMethodVsCalculator() {
    MultipleCurrencyMulticurveSensitivity pvcsMethod = METHOD_SWAP_FUT_TRA_HW.presentValueCurveSensitivity(SWAP_FUTURES_TRANSACTION, MULTICURVES_HW);
    MultipleCurrencyMulticurveSensitivity pvcsCalculator = SWAP_FUTURES_TRANSACTION.accept(PVCSHWC, MULTICURVES_HW);
    AssertSensivityObjects.assertEquals("DeliverableSwapFuturesTransactionHullWhiteMethod: present value curve sensitivity",
        pvcsMethod, pvcsCalculator, TOLERANCE_PV_DELTA);
  }

}