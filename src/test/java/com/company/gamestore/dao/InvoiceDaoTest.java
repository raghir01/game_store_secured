package com.company.gamestore.dao;

import com.company.gamestore.model.Invoice;
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
public class InvoiceDaoTest {
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
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        for(Invoice invoice : invoiceList){
            invoiceDao.deleteInvoice(invoice.getInvoiceId());
        }
    }

    @Test
    public void addInvoice() {
        Invoice invoice = new Invoice();
        invoice.setName("Raghi");
        invoice.setStreet("Lancaster Court");
        invoice.setCity("Hillsborough");
        invoice.setState("NJ");
        invoice.setZipcode("08907");
        invoice.setItemType("game");
        invoice.setItemId(100);
        invoice.setUnitPrice(new BigDecimal("400.12"));
        invoice.setQuantity(2);
        invoice.setSubtotal(new BigDecimal("800.24"));
        invoice.setTax(new BigDecimal("1.99"));
        invoice.setProcessingFee(new BigDecimal("10.23"));
        invoice.setTotal(new BigDecimal("812.46"));
        invoice = invoiceDao.addInvoice(invoice);

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        assertEquals(1, invoiceList.size());
    }
}