package com.company.gamestore.dao;

import com.company.gamestore.model.Tshirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TshirtDaoTest {
    @Autowired
    ConsoleDao consoleDao;
    @Autowired
    GameDao gameDao;
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    ProcessingFeeDao processingFeeDao;
    @Autowired
    SalesTaxRateDao salesTaxRateDao;
    @Autowired
    TshirtDao tshirtDao;


    @Before
    public void setUp() throws Exception {
        List<Tshirt> tshirtList = tshirtDao.getAllTshirts();
        for(Tshirt tshirt : tshirtList){
            tshirtDao.deleteTshirt(tshirt.getTshirtId());
        }
    }


    @Test
    public void addTshirt() {
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("Blue");
        tshirt.setDescription("A cotton Tshirt");
        tshirt.setPrice(new BigDecimal("20.00"));
        tshirt.setQuantity(3);
        tshirt = tshirtDao.addTshirt(tshirt);

        Tshirt tshirt1 = tshirtDao.getTshirt(tshirt.getTshirtId());
        assertEquals(tshirt1, tshirt);
    }

    @Test
    public void getTshirt() {
        Tshirt tshirt1 = new Tshirt();
        tshirt1.setSize("S");
        tshirt1.setColor("Blue");
        tshirt1.setDescription("A cotton Tshirt");
        tshirt1.setPrice(new BigDecimal("20.00"));
        tshirt1.setQuantity(3);
        tshirt1 = tshirtDao.addTshirt(tshirt1);

        Tshirt tshirt2 = new Tshirt();
        tshirt2.setSize("M");
        tshirt2.setColor("Black");
        tshirt2.setDescription("A cotton Tshirt");
        tshirt2.setPrice(new BigDecimal("100.00"));
        tshirt2.setQuantity(2);
        tshirt2 = tshirtDao.addTshirt(tshirt2);

        Tshirt tshirt3 = tshirtDao.getTshirt(tshirt1.getTshirtId());
        assertEquals(tshirt1, tshirt3);
        tshirt3 = tshirtDao.getTshirt(tshirt2.getTshirtId());
        assertEquals(tshirt2, tshirt3);
    }

    @Test
    public void getAllTshirt() {
        Tshirt tshirt1 = new Tshirt();
        tshirt1.setSize("S");
        tshirt1.setColor("Blue");
        tshirt1.setDescription("A cotton Tshirt");
        tshirt1.setPrice(new BigDecimal("20.00"));
        tshirt1.setQuantity(3);
        tshirt1 = tshirtDao.addTshirt(tshirt1);

        Tshirt tshirt2 = new Tshirt();
        tshirt2.setSize("M");
        tshirt2.setColor("Black");
        tshirt2.setDescription("A cotton Tshirt");
        tshirt2.setPrice(new BigDecimal("100.00"));
        tshirt2.setQuantity(2);
        tshirt2 = tshirtDao.addTshirt(tshirt2);

        List<Tshirt> tshirtList = tshirtDao.getAllTshirts();
        assertEquals(2, tshirtList.size());
    }


    @Test
    public void updateTshirt() {
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("Blue");
        tshirt.setDescription("A cotton Tshirt");
        tshirt.setPrice(new BigDecimal("20.00"));
        tshirt.setQuantity(3);
        tshirt = tshirtDao.addTshirt(tshirt);

        tshirt.setSize("XS");
        tshirt.setColor("Yellow");
        tshirtDao.updateTshirt(tshirt);

        Tshirt tshirt1 = tshirtDao.getTshirt(tshirt.getTshirtId());
        assertEquals(tshirt1, tshirt);
    }


    @Test
    public void deleteTshirt() {
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("Blue");
        tshirt.setDescription("A cotton Tshirt");
        tshirt.setPrice(new BigDecimal("20.00"));
        tshirt.setQuantity(3);
        tshirt = tshirtDao.addTshirt(tshirt);

        tshirtDao.deleteTshirt(tshirt.getTshirtId());
        Tshirt tshirt1 = tshirtDao.getTshirt(tshirt.getTshirtId());
        assertNull(tshirt1);
    }


    @Test
    public void findTshirtByColor() {
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("Blue");
        tshirt.setDescription("A cotton Tshirt");
        tshirt.setPrice(new BigDecimal("20.00"));
        tshirt.setQuantity(3);
        tshirt = tshirtDao.addTshirt(tshirt);

        List<Tshirt> tshirtList = tshirtDao.findTshirtByColor("Blue");
        assertEquals(1, tshirtList.size());
        assertEquals("Blue", tshirtList.get(0).getColor());
    }

    @Test
    public void findTshirtBySize() {
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("Blue");
        tshirt.setDescription("A cotton Tshirt");
        tshirt.setPrice(new BigDecimal("20.00"));
        tshirt.setQuantity(3);
        tshirt = tshirtDao.addTshirt(tshirt);

        List<Tshirt> tshirtList = tshirtDao.findTshirtBySize("S");
        assertEquals(1, tshirtList.size());
        assertEquals("S", tshirtList.get(0).getSize());
    }
}