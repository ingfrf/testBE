package org.example.service;

import org.example.SpringBootConsoleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootConsoleApplication.class})
@SpringBootTest
public class TaxCalculatorServiceTest {
    private static final double DELTA = 1e-10;

    @Autowired
    TaxCalculatorService taxCalculatorService;

    @Test
    public void testDependency() {
        assertEquals("TaxCalculatorServiceImpl", taxCalculatorService.getClass().getSimpleName());
    }

    @Test
    public void calculateAmountWithTax() {
        assertEquals(32.19, taxCalculatorService.calculateAmountWithTax(27.99, 1, 0.15), DELTA);
        assertEquals(20.89, taxCalculatorService.calculateAmountWithTax(18.99, 1, 0.10), DELTA);
        assertEquals(9.75, taxCalculatorService.calculateAmountWithTax(9.75, 1, 0), DELTA);
        assertEquals(11.85, taxCalculatorService.calculateAmountWithTax(11.25, 1, 0.05), DELTA);
    }
}