package com.company.gamestore.service;

import com.company.gamestore.dao.*;
import com.company.gamestore.model.*;
import com.company.gamestore.viewmodel.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class InvoiceServiceTest {

    private ConsoleDao consoleDao;
    private GameDao gameDao;
    private TshirtDao tshirtDao;
    private InvoiceDao invoiceDao;
    private ProcessingFeeDao processingFeeDao;
    private SalesTaxRateDao salesTaxRateDao;

    private InvoiceService invoiceService;



    @Before
    public void setUp() throws Exception{
        setUpConsoleDaoMock();
        setUpGameDaoMock();
        setUpTshirtDaoMock();
        setUpInvoiceDaoMock();
        setUpProcessingFeeDaoMock();
        setUpSalesTaxRateDaoMock();
        invoiceService = new InvoiceService(consoleDao, gameDao, invoiceDao, processingFeeDao,
                salesTaxRateDao, tshirtDao);
    }

    public void setUpConsoleDaoMock(){
        consoleDao = mock(ConsoleDaoJdbcTemplateImpl.class);

        Console console = new Console();
        console.setConsoleId(1);
        console.setModel("xbox");
        console.setManufacturer("Sony");
        console.setMemoryAmount("230");
        console.setProcessor("intel");
        console.setPrice(new BigDecimal("200.88"));
        console.setQuantity(3);

        Console console1 = new Console();
        console1.setModel("xbox");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("230");
        console1.setProcessor("intel");
        console1.setPrice(new BigDecimal("200.88"));
        console1.setQuantity(3);

        List<Console> consoleList = new ArrayList<>();
        consoleList.add(console);
        doReturn(console).when(consoleDao).addConsole(console1);
        doReturn(console).when(consoleDao).getConsole(1);
        doReturn(console).when(consoleDao).updateConsole(console);
        doReturn(consoleList).when(consoleDao).findConsolesbyManufacturer("Sony");
    }

    public void setUpGameDaoMock(){
        gameDao = mock(GameDaoJdbcTemplateImpl.class);

        Game game = new Game();
        game.setGameId(5);
        game.setTitle("Pacman");
        game.setErsbRating("B");
        game.setDescription("Very good game.");
        game.setPrice(new BigDecimal("150.33"));
        game.setStudio("PQube");
        game.setQuantity(4);

        Game game1 = new Game();
        game1.setTitle("Pacman");
        game1.setErsbRating("B");
        game1.setDescription("Very good game.");
        game1.setPrice(new BigDecimal("150.33"));
        game1.setStudio("PQube");
        game1.setQuantity(4);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);

        doReturn(game).when(gameDao).addGame(game1);
        doReturn(game).when(gameDao).getGame(5);
        doReturn(gameList).when(gameDao).findGamesByStudio("PQube");
        doReturn(gameList).when(gameDao).findGamesByERSBRating("B");
        doReturn(gameList).when(gameDao).findGamesByTitle("Pacman");
    }

    public void setUpTshirtDaoMock(){
        tshirtDao = mock(TshirtDaoJdbcTemplateImpl.class);

        Tshirt tshirt = new Tshirt();
        tshirt.setTshirtId(3);
        tshirt.setSize("S");
        tshirt.setColor("Blue");
        tshirt.setDescription("A cotton shirt.");
        tshirt.setPrice(new BigDecimal("30.66"));
        tshirt.setQuantity(2);

        Tshirt tshirt1 = new Tshirt();
        tshirt1.setSize("S");
        tshirt1.setColor("Blue");
        tshirt1.setDescription("A cotton shirt.");
        tshirt1.setPrice(new BigDecimal("30.66"));
        tshirt1.setQuantity(2);

        List<Tshirt> tshirtList = new ArrayList<>();
        tshirtList.add(tshirt);

        doReturn(tshirt).when(tshirtDao).addTshirt(tshirt1);
        doReturn(tshirt).when(tshirtDao).getTshirt(3);
        doReturn(tshirtList).when(tshirtDao).findTshirtBySize("S");
        doReturn(tshirtList).when(tshirtDao).findTshirtByColor("Blue");

    }

    public void setUpProcessingFeeDaoMock(){
        processingFeeDao = mock(ProcessingFeeDaoJdbcTemplateImpl.class);

        ProcessingFee processingFee = new ProcessingFee();
        processingFee.setProductType("Consoles");
        processingFee.setFee(new BigDecimal("14.99"));

        doReturn(processingFee).when(processingFeeDao).getProcessingFeeByItemType("Consoles");
        doReturn(processingFee).when(processingFeeDao).getProcessingFeeByItemType("Tshirts");
        doReturn(processingFee).when(processingFeeDao).getProcessingFeeByItemType("Games");

    }
    public void setUpSalesTaxRateDaoMock(){
        salesTaxRateDao = mock(SalesTaxRateDaoJdbcTemplateImpl.class);

        SalesTaxRate salesTaxRate = new SalesTaxRate();
        salesTaxRate.setState("MN");
        salesTaxRate.setRate(new BigDecimal(".06"));

        doReturn(salesTaxRate).when(salesTaxRateDao).getTaxByState("MN");
    }

    public void setUpInvoiceDaoMock(){
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setName("Amy");
        invoice.setStreet("Woonton rd");
        invoice.setCity("New  Burnswick");
        invoice.setState("NJ");
        invoice.setZipcode("80907");
        invoice.setItemType("Consoles");
        invoice.setItemId(1);
        invoice.setUnitPrice(new BigDecimal("200.88"));
        invoice.setQuantity(1);
        invoice.setSubtotal(new BigDecimal("200.88"));
        BigDecimal taxAmount = invoice.getUnitPrice().multiply(new BigDecimal(".06"));
        invoice.setTax(taxAmount);
        invoice.setProcessingFee(new BigDecimal("14.99"));
        invoice.setTotal(invoice.getSubtotal().add(invoice.getProcessingFee()));

        doReturn(invoice).when(invoiceDao).addInvoice(any(Invoice.class));

    }

    @Test
    public void purchase() {
        PurchaseViewModel purchaseViewModel = new PurchaseViewModel();
        purchaseViewModel.setName("Amy");
        purchaseViewModel.setStreet("Woonton rd");
        purchaseViewModel.setCity("New  Burnswick");
        purchaseViewModel.setState("MN");
        purchaseViewModel.setZip("80907");
        purchaseViewModel.setItemType("Consoles");
        purchaseViewModel.setItemId(1);
        purchaseViewModel.setQuantity(1);
        InvoiceViewModel  invoiceViewModel = invoiceService.purchase(purchaseViewModel);
        assertEquals(1, invoiceViewModel.getInvoiceId());
        assertEquals(BigDecimal.valueOf(227.92), invoiceViewModel.getTotal());

        purchaseViewModel.setQuantity(2);
        invoiceViewModel = invoiceService.purchase(purchaseViewModel);
        assertEquals(BigDecimal.valueOf(440.86), invoiceViewModel.getTotal());

    }

    @Test
    public void addConsole() {
        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        consoleViewModel.setModel("xbox");
        consoleViewModel.setManufacturer("Sony");
        consoleViewModel.setMemoryAmount("230");
        consoleViewModel.setProcessor("intel");
        consoleViewModel.setPrice(new BigDecimal("200.88"));
        consoleViewModel.setQuantity(3);

        consoleViewModel = invoiceService.addConsole(consoleViewModel);
        assertEquals(1, consoleViewModel.getConsoleId());
    }

    @Test
    public void getConsole() {
        ConsoleViewModel consoleViewModel = invoiceService.getConsole(1);
        assertEquals(consoleViewModel.getConsoleId(), 1);
    }

//    @Test
//    public void getAllConsole() {
//        ConsoleViewModel consoleViewModel = invoiceService.getAllConsole();
//        assertEquals(consoleViewModel.getConsoleId(), 1);
//    }

    @Test
    public void updateConsole() {

        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        consoleViewModel.setConsoleId(1);
        consoleViewModel.setModel("xbox");
        consoleViewModel.setManufacturer("Sony");
        consoleViewModel.setMemoryAmount("230");
        consoleViewModel.setProcessor("intel");
        consoleViewModel.setPrice(new BigDecimal("200.88"));
        consoleViewModel.setQuantity(3);

        consoleViewModel = invoiceService.updateConsole(consoleViewModel);
        assertEquals("Sony", consoleViewModel.getManufacturer());

    }

    @Test
    public void deleteConsole() {
        invoiceService.deleteConsole(1);
    }

    @Test
    public void findConsolesByManufacturer(){
        List<ConsoleViewModel>  consoleViewModelList = invoiceService.findConsolesByManufacturer("Sony");
        assertEquals(1, consoleViewModelList.size());

    }

    @Test
    public void addGame() {
        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setTitle("Pacman");
        gameViewModel.setErsbRating("B");
        gameViewModel.setDescription("Very good game.");
        gameViewModel.setPrice(new BigDecimal("150.33"));
        gameViewModel.setStudio("PQube");
        gameViewModel.setQuantity(4);

        gameViewModel = invoiceService.addGame(gameViewModel);
        assertEquals(5, gameViewModel.getGameId());
    }

    @Test
    public void getGame() {
        GameViewModel gameViewModel = invoiceService.getGame(5);
        assertEquals(gameViewModel.getGameId(),5);

    }

    @Test
    public void findGamesByStudio(){
        List<GameViewModel>  gameViewModelList = invoiceService.findGamesByStudio("PQube");
        assertEquals(1, gameViewModelList.size());
    }

    @Test
    public void findGamesByERSBRating(){
        List<GameViewModel> gameViewModelList = invoiceService.findGamesByERSBRating("B");
        assertEquals(1, gameViewModelList.size());
    }

    @Test
    public void findGamesByTitle(){
        List<GameViewModel> gameViewModelList = invoiceService.findGamesByTitle("Pacman");
        assertEquals(1, gameViewModelList.size());

    }

    @Test
    public void addTshirt(){
        TshirtVeiwModel tshirtVeiwModel = new TshirtVeiwModel();
        tshirtVeiwModel.setSize("S");
        tshirtVeiwModel.setColor("Blue");
        tshirtVeiwModel.setDescription("A cotton shirt.");
        tshirtVeiwModel.setPrice(new BigDecimal("30.66"));
        tshirtVeiwModel.setQuantity(2);

        tshirtVeiwModel = invoiceService.addTshirt(tshirtVeiwModel);
        assertEquals(3, tshirtVeiwModel.getTshirtId());
    }

    @Test
    public void getTshirt(){
        TshirtVeiwModel tshirtVeiwModel = invoiceService.getTshirt(3);
        assertEquals(tshirtVeiwModel.getTshirtId(), 3);
    }

    @Test
    public void findTshirtByColor(){
        List<TshirtVeiwModel> tshirtVeiwModelList = invoiceService.findTshirtByColor("Blue");
        assertEquals(1, tshirtVeiwModelList.size());
    }

    @Test
    public void findTshirtBySize(){
        List<TshirtVeiwModel> tshirtVeiwModelList = invoiceService.findTshirtBySize("S");
        assertEquals(1, tshirtVeiwModelList.size());
    }


}
