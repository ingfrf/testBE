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
public class PrintReceiptServiceTest {

    @Autowired
    PrintReceiptService printReceiptService;

    @Test
    public void testDependency() {
        assertEquals("PrintReceiptServiceImpl", printReceiptService.getClass().getSimpleName());
    }
}