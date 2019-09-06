package com.company.gamestore.dao;


import com.company.gamestore.model.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleDaoTest {
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
        List<Console> consoleList = consoleDao.getAllConsoles();
        for(Console console : consoleList){
            consoleDao.deleteConsole(console.getConsoleId());
        }

    }

    @Test
    public void addConsole() {
        Console console = new Console();
        console.setModel("ps4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("100");
        console.setProcessor("intel");
        console.setPrice(BigDecimal.valueOf(800.45));
        console.setQuantity(2);
        console = consoleDao.addConsole(console);

        Console console1 = consoleDao.getConsole(console.getConsoleId());
        assertEquals(console1,console);

    }

    @Test
    public void getConsole() {
        Console console1 = new Console();
        console1.setModel("ps8");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("100");
        console1.setProcessor("intel");
        console1.setPrice(BigDecimal.valueOf(300.45));
        console1.setQuantity(2);
        console1 = consoleDao.addConsole(console1);

        Console console2 = new Console();
        console2.setModel("ps4");
        console2.setManufacturer("Logitech");
        console2.setMemoryAmount("100");
        console2.setProcessor("intel");
        console2.setPrice(BigDecimal.valueOf(400.45));
        console2.setQuantity(5);
        console2 = consoleDao.addConsole(console2);

        Console console3 = consoleDao.getConsole(console1.getConsoleId());
        assertEquals(console1, console3);
        console3 = consoleDao.getConsole(console2.getConsoleId());
        assertEquals(console2,console3);

    }

    @Test
    public void getAllConsole() {
        Console console1 = new Console();
        console1.setModel("ps8");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("100");
        console1.setProcessor("intel");
        console1.setPrice(BigDecimal.valueOf(800.45));
        console1.setQuantity(2);
        console1 = consoleDao.addConsole(console1);

        Console console2 = new Console();
        console2.setModel("ps4");
        console2.setManufacturer("Logitech");
        console2.setMemoryAmount("100");
        console2.setProcessor("intel");
        console2.setPrice(BigDecimal.valueOf(100.45));
        console2.setQuantity(5);
        console2 = consoleDao.addConsole(console2);

        List<Console> consoleList = consoleDao.getAllConsoles();

        assertEquals(consoleList.size(), 2);
    }

    @Test
    public void updateConsole() {
        Console console = new Console();
        console.setModel("ps4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("100");
        console.setProcessor("intel");
        console.setPrice(BigDecimal.valueOf(800.45));
        console.setQuantity(2);
        console = consoleDao.addConsole(console);

        console.setModel("xbox");
        console.setQuantity(9);
        consoleDao.updateConsole(console);

        Console console1 = consoleDao.getConsole(console.getConsoleId());
        assertEquals(console1, console);
    }

    @Test
    public void deleteConsole() {
        Console console = new Console();
        console.setModel("ps4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("100");
        console.setProcessor("intel");
        console.setPrice(BigDecimal.valueOf(800.45));
        console.setQuantity(2);
        console = consoleDao.addConsole(console);

        consoleDao.deleteConsole(console.getConsoleId());
        Console console1 = consoleDao.getConsole(console.getConsoleId());
        assertNull(console1);


    }

    @Test
    public void findConsolesbyManufacturer() {
        Console console = new Console();
        console.setModel("ps4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("100");
        console.setProcessor("intel");
        console.setPrice(BigDecimal.valueOf(800.45));
        console.setQuantity(2);
        console = consoleDao.addConsole(console);

        List<Console> consoleList = consoleDao.findConsolesbyManufacturer("Sony");
        assertEquals(1,consoleList.size());
        assertEquals("Sony", consoleList.get(0).getManufacturer());
    }
}