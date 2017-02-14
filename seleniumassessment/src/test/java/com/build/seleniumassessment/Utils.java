package com.build.seleniumassessment;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utils class contains general math utilities
 */
public class Utils {
	public double calculateCaTax(double subtotal)
    {
    	double tax = subtotal * 0.0725;
    	tax = round(tax, 2);
    	return tax;
    }
	
    // http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
