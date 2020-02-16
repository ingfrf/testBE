package org.example.service.impl;

import org.example.service.TaxCalculatorService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TaxCalculatorServiceImpl implements TaxCalculatorService {
    private static final BigDecimal INCREMENT = new BigDecimal(String.valueOf(0.05d));
    private static final RoundingMode ROUNDING_MODE = RoundingMode.UP;

    @Override
    public double calculateAmountWithTax(double price, int quantity, double tax) {
        double amount = (quantity * price) * (1 + tax);
        return round(amount);
    }

    public double round(double value) {
        BigDecimal bdValue = new BigDecimal(String.valueOf(value));
        BigDecimal result = bdValue.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal last = result.multiply(hundred).remainder(BigDecimal.TEN);
        if (last.intValue() < 5) {
            result = roundUpLowValue(result);
        }
        return result.doubleValue();
    }

    private BigDecimal roundUpLowValue(BigDecimal value) {
        BigDecimal divided = value.divide(INCREMENT, 0, ROUNDING_MODE);
        return divided.multiply(INCREMENT);
    }
}
