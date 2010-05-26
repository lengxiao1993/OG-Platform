/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.model.option.definition;

import org.apache.commons.lang.Validate;

import com.opengamma.util.time.Expiry;

/**
 * 
 * Definition for a European-style vanilla option.
 * <p>
 * When the spot price is <i>S</i>, an option with strike <i>K</i> has payoff
 * <i>max(0, S - K)</i> for a call and <i>max(0, K - S)</i> for a put.
 * 
 */
public class EuropeanVanillaOptionDefinition extends OptionDefinition {
  private final OptionPayoffFunction<StandardOptionDataBundle> _payoffFunction = new OptionPayoffFunction<StandardOptionDataBundle>() {

    @Override
    public Double getPayoff(final StandardOptionDataBundle data, final Double optionPrice) {
      Validate.notNull(data);
      final double spot = data.getSpot();
      return isCall() ? Math.max(0, spot - getStrike()) : Math.max(0, getStrike() - spot);
    }
  };
  private final OptionExerciseFunction<StandardOptionDataBundle> _exerciseFunction = new OptionExerciseFunction<StandardOptionDataBundle>() {

    @Override
    public Boolean shouldExercise(final StandardOptionDataBundle data, final Double optionPrice) {
      return false;
    }
  };

  public EuropeanVanillaOptionDefinition(final double strike, final Expiry expiry, final boolean isCall) {
    super(strike, expiry, isCall);
  }

  @Override
  public OptionExerciseFunction<StandardOptionDataBundle> getExerciseFunction() {
    return _exerciseFunction;
  }

  @Override
  public OptionPayoffFunction<StandardOptionDataBundle> getPayoffFunction() {
    return _payoffFunction;
  }

  @Override
  public String toString() {
    return "Vanilla European" + (isCall() ? " call " : " put ") + "[K = " + getStrike() + ", " + getExpiry() + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + _exerciseFunction.hashCode();
    result = prime * result + _payoffFunction.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    return false;
  }

}
