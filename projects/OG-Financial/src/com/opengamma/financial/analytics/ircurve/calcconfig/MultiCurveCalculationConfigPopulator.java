/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.ircurve.calcconfig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opengamma.financial.analytics.ircurve.StripInstrumentTypeDeprecated;
import com.opengamma.financial.analytics.model.curve.interestrate.MultiYieldCurvePropertiesAndDefaults;
import com.opengamma.master.config.ConfigDocument;
import com.opengamma.master.config.ConfigMaster;
import com.opengamma.master.config.ConfigMasterUtils;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.Currency;

/**
 * 
 */
public class MultiCurveCalculationConfigPopulator {

  public MultiCurveCalculationConfigPopulator(final ConfigMaster configMaster) {
    ArgumentChecker.notNull(configMaster, "config master");
    populateConfigMaster(configMaster);
  }

  private static void populateConfigMaster(final ConfigMaster configMaster) {
    final String discountingCurveName = "Discounting";
    final String forward3MCurveName = "Forward3M";
    final String forward6MCurveName = "Forward6M";
    final MultiCurveCalculationConfig defaultUSDConfig = new MultiCurveCalculationConfig("DefaultTwoCurveUSDConfig", new String[] {discountingCurveName, forward3MCurveName},
        Currency.USD, MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING, getTwoCurveUSDInstrumentConfig(discountingCurveName, forward3MCurveName));
    final MultiCurveCalculationConfig defaultGBPConfig = new MultiCurveCalculationConfig("DefaultTwoCurveGBPConfig", new String[] {discountingCurveName, forward6MCurveName},
        Currency.GBP, MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING, getTwoCurveGBPInstrumentConfig(discountingCurveName, forward6MCurveName));
    final MultiCurveCalculationConfig defaultEURConfig = new MultiCurveCalculationConfig("DefaultTwoCurveEURConfig", new String[] {discountingCurveName, forward6MCurveName},
        Currency.EUR, MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING, getTwoCurveEURInstrumentConfig(discountingCurveName, forward6MCurveName));
    final MultiCurveCalculationConfig defaultJPYConfig = new MultiCurveCalculationConfig("DefaultTwoCurveJPYConfig", new String[] {discountingCurveName, forward6MCurveName},
        Currency.JPY, MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING, getTwoCurveJPYInstrumentConfig(discountingCurveName, forward6MCurveName));
    final MultiCurveCalculationConfig defaultCHFConfig = new MultiCurveCalculationConfig("DefaultTwoCurveCHFConfig", new String[] {discountingCurveName, forward6MCurveName},
        Currency.CHF, MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING, getTwoCurveCHFInstrumentConfig(discountingCurveName, forward6MCurveName));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(defaultUSDConfig));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(defaultGBPConfig));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(defaultEURConfig));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(defaultJPYConfig));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(defaultCHFConfig));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(getAUDThreeCurveConfig()));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(getAUDDiscountingCurveConfig()));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(getAUDForwardCurvesConfig()));
    ConfigMasterUtils.storeByName(configMaster, makeConfigDocument(getSingleAUDCurveConfig()));
  }

  private static ConfigDocument<MultiCurveCalculationConfig> makeConfigDocument(final MultiCurveCalculationConfig curveConfig) {
    final ConfigDocument<MultiCurveCalculationConfig> configDocument = new ConfigDocument<MultiCurveCalculationConfig>(MultiCurveCalculationConfig.class);
    configDocument.setName(curveConfig.getCalculationConfigName());
    configDocument.setValue(curveConfig);
    return configDocument;
  }

  private static LinkedHashMap<String, CurveInstrumentConfig> getTwoCurveUSDInstrumentConfig(final String discountingCurveName, final String forward3MCurveName) {
    final String[] discountingOnly = new String[] {discountingCurveName};
    final String[] forward3MOnly = new String[] {forward3MCurveName};
    final String[] discountingForward3M = new String[] {discountingCurveName, forward3MCurveName};
    final String[] discountingDiscounting = new String[] {discountingCurveName, discountingCurveName};
    final LinkedHashMap<String, CurveInstrumentConfig> result = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> discountingConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    discountingConfig.put(StripInstrumentTypeDeprecated.CASH, discountingOnly);
    discountingConfig.put(StripInstrumentTypeDeprecated.OIS_SWAP, discountingDiscounting);
    final Map<StripInstrumentTypeDeprecated, String[]> forward3MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forward3MConfig.put(StripInstrumentTypeDeprecated.LIBOR, forward3MOnly);
    forward3MConfig.put(StripInstrumentTypeDeprecated.FRA_3M, discountingForward3M);
    forward3MConfig.put(StripInstrumentTypeDeprecated.SWAP_3M, discountingForward3M);
    result.put(discountingCurveName, new CurveInstrumentConfig(discountingConfig));
    result.put(forward3MCurveName, new CurveInstrumentConfig(forward3MConfig));
    return result;
  }

  private static LinkedHashMap<String, CurveInstrumentConfig> getTwoCurveGBPInstrumentConfig(final String discountingCurveName, final String forward6MCurveName) {
    final String[] discountingOnly = new String[] {discountingCurveName};
    final String[] forward6MOnly = new String[] {forward6MCurveName};
    final String[] discountingForward6M = new String[] {discountingCurveName, forward6MCurveName};
    final String[] discountingDiscounting = new String[] {discountingCurveName, discountingCurveName};
    final LinkedHashMap<String, CurveInstrumentConfig> result = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> discountingConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    discountingConfig.put(StripInstrumentTypeDeprecated.CASH, discountingOnly);
    discountingConfig.put(StripInstrumentTypeDeprecated.OIS_SWAP, discountingDiscounting);
    final Map<StripInstrumentTypeDeprecated, String[]> forward6MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forward6MConfig.put(StripInstrumentTypeDeprecated.LIBOR, forward6MOnly);
    forward6MConfig.put(StripInstrumentTypeDeprecated.FRA_6M, discountingForward6M);
    forward6MConfig.put(StripInstrumentTypeDeprecated.SWAP_6M, discountingForward6M);
    result.put(discountingCurveName, new CurveInstrumentConfig(discountingConfig));
    result.put(forward6MCurveName, new CurveInstrumentConfig(forward6MConfig));
    return result;
  }

  private static LinkedHashMap<String, CurveInstrumentConfig> getTwoCurveEURInstrumentConfig(final String discountingCurveName, final String forward6MCurveName) {
    final String[] discountingOnly = new String[] {discountingCurveName};
    final String[] forward6MOnly = new String[] {forward6MCurveName};
    final String[] discountingForward6M = new String[] {discountingCurveName, forward6MCurveName};
    final String[] discountingDiscounting = new String[] {discountingCurveName, discountingCurveName};
    final LinkedHashMap<String, CurveInstrumentConfig> result = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> discountingConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    discountingConfig.put(StripInstrumentTypeDeprecated.CASH, discountingOnly);
    discountingConfig.put(StripInstrumentTypeDeprecated.OIS_SWAP, discountingDiscounting);
    final Map<StripInstrumentTypeDeprecated, String[]> forward6MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forward6MConfig.put(StripInstrumentTypeDeprecated.EURIBOR, forward6MOnly);
    forward6MConfig.put(StripInstrumentTypeDeprecated.FRA_6M, discountingForward6M);
    forward6MConfig.put(StripInstrumentTypeDeprecated.SWAP_6M, discountingForward6M);
    result.put(discountingCurveName, new CurveInstrumentConfig(discountingConfig));
    result.put(forward6MCurveName, new CurveInstrumentConfig(forward6MConfig));
    return result;
  }

  private static LinkedHashMap<String, CurveInstrumentConfig> getTwoCurveJPYInstrumentConfig(final String discountingCurveName, final String forward6MCurveName) {
    final String[] discountingOnly = new String[] {discountingCurveName};
    final String[] forward6MOnly = new String[] {forward6MCurveName};
    final String[] discountingForward6M = new String[] {discountingCurveName, forward6MCurveName};
    final String[] discountingDiscounting = new String[] {discountingCurveName, discountingCurveName};
    final LinkedHashMap<String, CurveInstrumentConfig> result = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> discountingConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    discountingConfig.put(StripInstrumentTypeDeprecated.CASH, discountingOnly);
    discountingConfig.put(StripInstrumentTypeDeprecated.OIS_SWAP, discountingDiscounting);
    final Map<StripInstrumentTypeDeprecated, String[]> forward3MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forward3MConfig.put(StripInstrumentTypeDeprecated.LIBOR, forward6MOnly);
    forward3MConfig.put(StripInstrumentTypeDeprecated.FRA_6M, discountingForward6M);
    forward3MConfig.put(StripInstrumentTypeDeprecated.SWAP_6M, discountingForward6M);
    result.put(discountingCurveName, new CurveInstrumentConfig(discountingConfig));
    result.put(forward6MCurveName, new CurveInstrumentConfig(forward3MConfig));
    return result;
  }

  private static LinkedHashMap<String, CurveInstrumentConfig> getTwoCurveCHFInstrumentConfig(final String discountingCurveName, final String forward6MCurveName) {
    final String[] discountingOnly = new String[] {discountingCurveName};
    final String[] forward6MOnly = new String[] {forward6MCurveName};
    final String[] discountingForward6M = new String[] {discountingCurveName, forward6MCurveName};
    final String[] discountingDiscounting = new String[] {discountingCurveName, discountingCurveName};
    final LinkedHashMap<String, CurveInstrumentConfig> result = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> discountingConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    discountingConfig.put(StripInstrumentTypeDeprecated.CASH, discountingOnly);
    discountingConfig.put(StripInstrumentTypeDeprecated.OIS_SWAP, discountingDiscounting);
    final Map<StripInstrumentTypeDeprecated, String[]> forward3MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forward3MConfig.put(StripInstrumentTypeDeprecated.LIBOR, forward6MOnly);
    forward3MConfig.put(StripInstrumentTypeDeprecated.FRA_6M, discountingForward6M);
    forward3MConfig.put(StripInstrumentTypeDeprecated.SWAP_6M, discountingForward6M);
    result.put(discountingCurveName, new CurveInstrumentConfig(discountingConfig));
    result.put(forward6MCurveName, new CurveInstrumentConfig(forward3MConfig));
    return result;
  }

  private static MultiCurveCalculationConfig getAUDThreeCurveConfig() {
    final String[] yieldCurveNames = new String[] {"Discounting", "ForwardBasis3M", "ForwardBasis6M"};
    final Currency uniqueId = Currency.AUD;
    final String calculationMethod = MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING;
    final LinkedHashMap<String, CurveInstrumentConfig> curveExposuresForInstruments = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> discountingConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    discountingConfig.put(StripInstrumentTypeDeprecated.CASH, new String[] {"Discounting"});
    discountingConfig.put(StripInstrumentTypeDeprecated.OIS_SWAP, new String[] {"Discounting", "Discounting"});
    final Map<StripInstrumentTypeDeprecated, String[]> forwardBasis3MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forwardBasis3MConfig.put(StripInstrumentTypeDeprecated.BASIS_SWAP, new String[] {"Discounting", "ForwardBasis3M", "ForwardBasis6M"});
    forwardBasis3MConfig.put(StripInstrumentTypeDeprecated.SWAP_3M, new String[] {"Discounting", "ForwardBasis3M"});
    forwardBasis3MConfig.put(StripInstrumentTypeDeprecated.LIBOR, new String[] {"ForwardBasis3M"});
    final Map<StripInstrumentTypeDeprecated, String[]> forwardBasis6MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forwardBasis6MConfig.put(StripInstrumentTypeDeprecated.BASIS_SWAP, new String[] {"Discounting", "ForwardBasis3M", "ForwardBasis6M"});
    forwardBasis6MConfig.put(StripInstrumentTypeDeprecated.SWAP_6M, new String[] {"Discounting", "ForwardBasis6M"});
    forwardBasis6MConfig.put(StripInstrumentTypeDeprecated.LIBOR, new String[] {"ForwardBasis6M"});
    curveExposuresForInstruments.put("Discounting", new CurveInstrumentConfig(discountingConfig));
    curveExposuresForInstruments.put("ForwardBasis3M", new CurveInstrumentConfig(forwardBasis3MConfig));
    curveExposuresForInstruments.put("ForwardBasis6M", new CurveInstrumentConfig(forwardBasis6MConfig));
    return new MultiCurveCalculationConfig("DefaultThreeCurveAUDConfig", yieldCurveNames, uniqueId, calculationMethod, curveExposuresForInstruments);
  }

  private static MultiCurveCalculationConfig getAUDDiscountingCurveConfig() {
    final String[] yieldCurveNames = new String[] {"Discounting"};
    final Currency uniqueId = Currency.AUD;
    final String calculationMethod = MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING;
    final LinkedHashMap<String, CurveInstrumentConfig> curveExposuresForInstruments = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> discountingConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    discountingConfig.put(StripInstrumentTypeDeprecated.CASH, new String[] {"Discounting"});
    discountingConfig.put(StripInstrumentTypeDeprecated.OIS_SWAP, new String[] {"Discounting", "Discounting"});
    curveExposuresForInstruments.put("Discounting", new CurveInstrumentConfig(discountingConfig));
    return new MultiCurveCalculationConfig("DiscountingAUDConfig", yieldCurveNames, uniqueId, calculationMethod, curveExposuresForInstruments);
  }

  private static MultiCurveCalculationConfig getAUDForwardCurvesConfig() {
    final String[] yieldCurveNames = new String[] {"ForwardBasis3M", "ForwardBasis6M"};
    final Currency uniqueId = Currency.AUD;
    final String calculationMethod = MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING;
    final LinkedHashMap<String, CurveInstrumentConfig> curveExposuresForInstruments = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> forwardBasis3MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forwardBasis3MConfig.put(StripInstrumentTypeDeprecated.BASIS_SWAP, new String[] {"Discounting", "ForwardBasis3M", "ForwardBasis6M"});
    forwardBasis3MConfig.put(StripInstrumentTypeDeprecated.SWAP_3M, new String[] {"Discounting", "ForwardBasis3M"});
    forwardBasis3MConfig.put(StripInstrumentTypeDeprecated.LIBOR, new String[] {"ForwardBasis3M"});
    final Map<StripInstrumentTypeDeprecated, String[]> forwardBasis6MConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    forwardBasis6MConfig.put(StripInstrumentTypeDeprecated.BASIS_SWAP, new String[] {"Discounting", "ForwardBasis3M", "ForwardBasis6M"});
    forwardBasis6MConfig.put(StripInstrumentTypeDeprecated.SWAP_6M, new String[] {"Discounting", "ForwardBasis6M"});
    forwardBasis6MConfig.put(StripInstrumentTypeDeprecated.LIBOR, new String[] {"ForwardBasis6M"});
    curveExposuresForInstruments.put("ForwardBasis3M", new CurveInstrumentConfig(forwardBasis3MConfig));
    curveExposuresForInstruments.put("ForwardBasis6M", new CurveInstrumentConfig(forwardBasis6MConfig));
    final LinkedHashMap<String, String[]> exogenousConfigAndCurveNames = new LinkedHashMap<String, String[]>();
    exogenousConfigAndCurveNames.put("DiscountingAUDConfig", new String[] {"Discounting"});
    return new MultiCurveCalculationConfig("ForwardFromDiscountingAUDConfig", yieldCurveNames, uniqueId, calculationMethod, curveExposuresForInstruments,
        exogenousConfigAndCurveNames);
  }

  private static MultiCurveCalculationConfig getSingleAUDCurveConfig() {
    final String[] yieldCurveNames = new String[] {"Single"};
    final String[] twoCurveNames = new String[] {"Single", "Single"};
    final Currency uniqueId = Currency.AUD;
    final String calculationMethod = MultiYieldCurvePropertiesAndDefaults.PAR_RATE_STRING;
    final LinkedHashMap<String, CurveInstrumentConfig> curveExposuresForInstruments = new LinkedHashMap<String, CurveInstrumentConfig>();
    final Map<StripInstrumentTypeDeprecated, String[]> singleConfig = new HashMap<StripInstrumentTypeDeprecated, String[]>();
    singleConfig.put(StripInstrumentTypeDeprecated.FUTURE, twoCurveNames);
    singleConfig.put(StripInstrumentTypeDeprecated.CASH, yieldCurveNames);
    singleConfig.put(StripInstrumentTypeDeprecated.SWAP_3M, twoCurveNames);
    singleConfig.put(StripInstrumentTypeDeprecated.SWAP_6M, twoCurveNames);
    curveExposuresForInstruments.put("Single", new CurveInstrumentConfig(singleConfig));
    return new MultiCurveCalculationConfig("SingleAUDConfig", yieldCurveNames, uniqueId, calculationMethod, curveExposuresForInstruments);
  }
}
