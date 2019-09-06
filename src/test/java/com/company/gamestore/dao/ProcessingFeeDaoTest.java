package com.company.gamestore.dao;
import static org.junit.Assert.assertEquals;

import com.company.gamestore.model.ProcessingFee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
 public class ProcessingFeeDaoTest {
    @Autowired
    ProcessingFeeDao processingFeeDao;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getProcessingFeeByItemType() {
        ProcessingFee consoleFee = processingFeeDao.getProcessingFeeByItemType("Consoles");
        ProcessingFee tshirtFee = processingFeeDao.getProcessingFeeByItemType("T-Shirts");
        ProcessingFee gameFee = processingFeeDao.getProcessingFeeByItemType("Games");

        assertEquals(BigDecimal.valueOf(14.99), consoleFee.getFee());
        assertEquals(BigDecimal.valueOf(1.98), tshirtFee.getFee());
        assertEquals(BigDecimal.valueOf(1.49), gameFee.getFee());


    }
}