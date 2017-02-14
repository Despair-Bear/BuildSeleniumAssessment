package com.build.seleniumassessment;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

/**
 * Build.com Selenium Assessment test case
 */
public class TestBuildSite
{
	Browser browser;
	Utils util;

	@Before
	public void setup()
	{
    	browser = new Browser();
    	util = new Utils();
	}
	
	@After
	public void teardown()
	{
		browser.teardown();
	}
	
    @Test
    public void testCaTaxAndGrandTotal()
    {	
    	browser.addItemToCart("Kohler K-6626-6U-20", 1);
    	browser.addItemToCart("Kohler K-6626-6U-K4", 1);
    	browser.addItemToCart("Kohler K-5180-ST", 2);
    	browser.checkoutProcedure();
    	
    	double subtotal = Double.parseDouble(browser.waitAndGetById("subtotalamount").getAttribute("data-subtotal"));
    	double actual_total = Double.parseDouble(browser.waitAndGetById("grandtotalamount").getText().replaceAll("[$,]", ""));
    	actual_total = util.round(actual_total, 2);
    	
    	double expected_tax = util.calculateCaTax(subtotal);
    	double actual_tax = Double.parseDouble(browser.waitAndGetById("taxAmount").getAttribute("data-tax"));
    	double expected_total = util.round((subtotal + actual_tax), 2);
    	
    	assertThat(actual_tax).isEqualTo(expected_tax);
    	assertThat(actual_total).isEqualTo(expected_total);
    }
}
