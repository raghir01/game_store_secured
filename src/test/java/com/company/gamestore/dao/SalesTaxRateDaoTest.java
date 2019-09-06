package com.company.gamestore.dao;

import com.company.gamestore.model.SalesTaxRate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SalesTaxRateDaoTest {
    @Autowired
    SalesTaxRateDao salesTaxRateDao;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getTaxByState() {
        SalesTaxRate mnsalesTaxRate = salesTaxRateDao.getTaxByState("MN");
        SalesTaxRate njsalesTaxRate = salesTaxRateDao.getTaxByState("NJ");
        SalesTaxRate nysalesTaxRate = salesTaxRateDao.getTaxByState("NY");
        assertEquals(BigDecimal.valueOf(0.06), mnsalesTaxRate.getRate());
        assertEquals(BigDecimal.valueOf(0.05), njsalesTaxRate.getRate());
        assertEquals(BigDecimal.valueOf(0.06), nysalesTaxRate.getRate());
    }
}