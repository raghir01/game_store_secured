package com.company.gamestore.service;

import com.company.gamestore.dao.*;
import com.company.gamestore.model.*;
import com.company.gamestore.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    ConsoleDao consoleDao;
    GameDao gameDao;
    InvoiceDao invoiceDao;
    ProcessingFeeDao processingFeeDao;
    SalesTaxRateDao salesTaxRateDao;
    TshirtDao tshirtDao;

    @Autowired
    public InvoiceService(ConsoleDao consoleDao, GameDao gameDao, InvoiceDao invoiceDao, ProcessingFeeDao processingFeeDao, SalesTaxRateDao salesTaxRateDao, TshirtDao tshirtDao) {
        this.consoleDao = consoleDao;
        this.gameDao = gameDao;
        this.invoiceDao = invoiceDao;
        this.processingFeeDao = processingFeeDao;
        this.salesTaxRateDao = salesTaxRateDao;
        this.tshirtDao = tshirtDao;
    }

    @Transactional
    public InvoiceViewModel purchase(PurchaseViewModel purchaseViewModel) {

        if(!purchaseViewModel.getItemType().equals("Consoles") && !purchaseViewModel.getItemType().equals("Games")
                && !purchaseViewModel.getItemType().equals("Tshirts")){
            throw new IllegalArgumentException("ItemType must be one of Consoles, Games or Tshirts.");
        }
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        BigDecimal itemPrice = BigDecimal.ZERO;
        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        if (purchaseViewModel.getItemType().equals("Consoles")) {
            Console console = consoleDao.getConsole(purchaseViewModel.getItemId());
            if(console == null){
                throw new IllegalArgumentException("No Console exists with itemId  " + purchaseViewModel.getItemId());
            }
            if (console.getQuantity() < purchaseViewModel.getQuantity()) {
                throw new IllegalArgumentException("Requested quantity exceeds stock for " + purchaseViewModel.getItemType());
            }
            itemPrice = console.getPrice();
            consoleViewModel.setConsoleId(console.getConsoleId());
            consoleViewModel.setModel(console.getModel());
            consoleViewModel.setManufacturer(console.getManufacturer());
            consoleViewModel.setMemoryAmount(console.getMemoryAmount());
            consoleViewModel.setProcessor(console.getProcessor());
            consoleViewModel.setPrice(console.getPrice());
            consoleViewModel.setQuantity(console.getQuantity());
            console.setQuantity(console.getQuantity() - purchaseViewModel.getQuantity());
            consoleDao.updateConsole(console);
            invoiceViewModel.setConsole(consoleViewModel);
        }

        GameViewModel gameViewModel = new GameViewModel();
        if (purchaseViewModel.getItemType().equals("Games")) {
            Game game = gameDao.getGame(purchaseViewModel.getItemId());
            if(game == null){
                throw new IllegalArgumentException("No Game exists with itemId  " + purchaseViewModel.getItemId());
            }
            if (game.getQuantity() < purchaseViewModel.getQuantity()) {
                throw new IllegalArgumentException("Requested quantity exceeds stock for " + purchaseViewModel.getItemType());
            }
            itemPrice = game.getPrice();
            gameViewModel.setGameId(game.getGameId());
            gameViewModel.setTitle(game.getTitle());
            gameViewModel.setErsbRating(game.getErsbRating());
            gameViewModel.setDescription(game.getDescription());
            gameViewModel.setPrice(game.getPrice());
            gameViewModel.setStudio(game.getStudio());
            gameViewModel.setQuantity(game.getQuantity() - purchaseViewModel.getQuantity());
            game.setQuantity(game.getQuantity() - purchaseViewModel.getQuantity());
            gameDao.updateGame(game);
            invoiceViewModel.setGame(gameViewModel);
        }

        TshirtVeiwModel tshirtVeiwModel = new TshirtVeiwModel();
        if (purchaseViewModel.getItemType().equals("Tshirts")) {
            Tshirt tshirt = tshirtDao.getTshirt(purchaseViewModel.getItemId());
            if(tshirt == null){
                throw new IllegalArgumentException("No Tshirts exists with itemId  " + purchaseViewModel.getItemId());
            }
            if (tshirt.getQuantity() < purchaseViewModel.getQuantity()) {
                throw new IllegalArgumentException("Requested quantity exceeds stock for " + purchaseViewModel.getItemType());
            }

            itemPrice = tshirt.getPrice();
            tshirtVeiwModel.setTshirtId(tshirt.getTshirtId());
            tshirtVeiwModel.setSize(tshirt.getSize());
            tshirtVeiwModel.setColor(tshirt.getColor());
            tshirtVeiwModel.setDescription(tshirt.getDescription());
            tshirtVeiwModel.setPrice(tshirt.getPrice());
            tshirtVeiwModel.setQuantity(tshirt.getQuantity());
            tshirt.setQuantity(tshirt.getQuantity() - purchaseViewModel.getQuantity());
            tshirtDao.updateTshirt(tshirt);
            invoiceViewModel.setTshirt(tshirtVeiwModel);
        }
        ProcessingFee processingFee = processingFeeDao.getProcessingFeeByItemType(purchaseViewModel.getItemType());
        if(processingFee == null){
            throw new IllegalArgumentException("No processing fee exist for itemType" + purchaseViewModel.getItemType());
        }
        BigDecimal fee = processingFee.getFee();
        if (purchaseViewModel.getQuantity() > 10) {
            fee = fee.add(BigDecimal.valueOf(15.49));
        }
        SalesTaxRate salesTaxRate = salesTaxRateDao.getTaxByState(purchaseViewModel.getState());
        if(salesTaxRate == null){
            throw new IllegalArgumentException("No salesTaxRate fee exist for state" + purchaseViewModel.getState());
        }
        BigDecimal subTotal = itemPrice.multiply(BigDecimal.valueOf(purchaseViewModel.getQuantity()));
        BigDecimal taxAmount = subTotal.multiply(salesTaxRate.getRate());
        BigDecimal total = subTotal.add(taxAmount).add(fee);
        total = total.setScale(2, RoundingMode.HALF_UP);
        invoiceViewModel.setTax(taxAmount);
        invoiceViewModel.setProcessingFee(fee);
        invoiceViewModel.setSubtotal(subTotal);
        invoiceViewModel.setTotal(total);
        invoiceViewModel.setName(purchaseViewModel.getName());
        invoiceViewModel.setStreet(purchaseViewModel.getStreet());
        invoiceViewModel.setCity(purchaseViewModel.getCity());
        invoiceViewModel.setState(purchaseViewModel.getState());
        invoiceViewModel.setZipcode(purchaseViewModel.getZip());
        invoiceViewModel.setItemType(purchaseViewModel.getItemType());
        invoiceViewModel.setUnitPrice(itemPrice);
        invoiceViewModel.setQuantity(purchaseViewModel.getQuantity());

        // Create invoice


        Invoice invoice = new Invoice();
        invoice.setName(purchaseViewModel.getName());
        invoice.setStreet(purchaseViewModel.getStreet());
        invoice.setCity(purchaseViewModel.getCity());
        invoice.setState(purchaseViewModel.getState());
        invoice.setZipcode(purchaseViewModel.getZip());
        invoice.setItemType(purchaseViewModel.getItemType());
        invoice.setItemId(purchaseViewModel.getItemId());
        invoice.setUnitPrice(itemPrice);
        invoice.setQuantity(purchaseViewModel.getQuantity());
        invoice.setSubtotal(subTotal);
        invoice.setTax(taxAmount);
        invoice.setProcessingFee(fee);
        invoice.setTotal(total);

        invoice = invoiceDao.addInvoice(invoice);

        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        return invoiceViewModel;
    }
 //Console

    public ConsoleViewModel addConsole(ConsoleViewModel consoleViewModel) {
        Console console = new Console();
        console.setModel(consoleViewModel.getModel());
        console.setManufacturer(consoleViewModel.getManufacturer());
        console.setMemoryAmount(consoleViewModel.getMemoryAmount());
        console.setProcessor(consoleViewModel.getProcessor());
        console.setPrice(consoleViewModel.getPrice());
        console.setQuantity(consoleViewModel.getQuantity());

        console = consoleDao.addConsole(console);

        consoleViewModel.setConsoleId(console.getConsoleId());
        return consoleViewModel;
    }

    public ConsoleViewModel getConsole(int consoleId) {
        Console console = consoleDao.getConsole(consoleId);
        if(console == null){
            return null;
        }
        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        consoleViewModel.setConsoleId(console.getConsoleId());
        consoleViewModel.setModel(console.getModel());
        consoleViewModel.setManufacturer(console.getManufacturer());
        consoleViewModel.setMemoryAmount(console.getMemoryAmount());
        consoleViewModel.setProcessor(console.getProcessor());
        consoleViewModel.setPrice(console.getPrice());
        consoleViewModel.setQuantity(console.getQuantity());
        return consoleViewModel;
    }

    public List<ConsoleViewModel> getAllConsole(){
        List<Console> consoleList = consoleDao.getAllConsoles();
        List<ConsoleViewModel> result = new ArrayList<>();
        for(Console console : consoleList){
            ConsoleViewModel consoleViewModel = new ConsoleViewModel();
            consoleViewModel.setConsoleId(console.getConsoleId());
            consoleViewModel.setModel(console.getModel());
            consoleViewModel.setManufacturer(console.getManufacturer());
            consoleViewModel.setMemoryAmount(console.getMemoryAmount());
            consoleViewModel.setProcessor(console.getProcessor());
            consoleViewModel.setPrice(console.getPrice());
            consoleViewModel.setQuantity(console.getQuantity());

            result.add(consoleViewModel);
        }
        return result;
    }

    public ConsoleViewModel updateConsole(ConsoleViewModel consoleViewModel) {
        Console console = new Console();
        console.setModel(consoleViewModel.getModel());
        console.setManufacturer(consoleViewModel.getManufacturer());
        console.setMemoryAmount(consoleViewModel.getMemoryAmount());
        console.setProcessor(consoleViewModel.getProcessor());
        console.setPrice(consoleViewModel.getPrice());
        console.setQuantity(consoleViewModel.getQuantity());
        console.setConsoleId(consoleViewModel.getConsoleId());
        consoleDao.updateConsole(console);
        return consoleViewModel;
    }

    public void deleteConsole(int consoleId) {
        consoleDao.deleteConsole(consoleId);
    }

    public List<ConsoleViewModel> findConsolesByManufacturer(String manufacturer){
        List<Console> consoleList = consoleDao.findConsolesbyManufacturer(manufacturer);
        List<ConsoleViewModel> consoleViewModelList = new ArrayList<>();
        for(Console console : consoleList){
            ConsoleViewModel consoleViewModel = new ConsoleViewModel();
            consoleViewModel.setConsoleId(console.getConsoleId());
            consoleViewModel.setModel(console.getModel());
            consoleViewModel.setManufacturer(console.getManufacturer());
            consoleViewModel.setMemoryAmount(console.getMemoryAmount());
            consoleViewModel.setProcessor(console.getProcessor());
            consoleViewModel.setPrice(console.getPrice());
            consoleViewModel.setQuantity(console.getQuantity());
            consoleViewModelList.add(consoleViewModel);
        }
        return consoleViewModelList;
    }

//Game

    public GameViewModel addGame(GameViewModel gameViewModel){
        Game game = new Game();
        game.setTitle(gameViewModel.getTitle());
        game.setErsbRating(gameViewModel.getErsbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setPrice(gameViewModel.getPrice());
        game.setStudio(gameViewModel.getStudio());
        game.setQuantity(gameViewModel.getQuantity());

        game = gameDao.addGame(game);

        gameViewModel.setGameId(game.getGameId());
        return gameViewModel;
    }

    public GameViewModel getGame(int gameId){
        Game game = gameDao.getGame(gameId);
        if(game == null){
            return null;
        }
        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setGameId(game.getGameId());
        gameViewModel.setTitle(game.getTitle());
        gameViewModel.setErsbRating(game.getErsbRating());
        gameViewModel.setDescription(game.getDescription());
        gameViewModel.setPrice(game.getPrice());
        gameViewModel.setStudio(game.getStudio());
        gameViewModel.setQuantity(game.getQuantity());
        return gameViewModel;
    }

    public List<GameViewModel> getAllGame() {
        List<Game> gameList = gameDao.getAllGames();
        List<GameViewModel> result = new ArrayList<>();
        for (Game game : gameList) {
            GameViewModel gameViewModel = new GameViewModel();
            gameViewModel.setGameId(game.getGameId());
            gameViewModel.setTitle(game.getTitle());
            gameViewModel.setErsbRating(game.getErsbRating());
            gameViewModel.setDescription(game.getDescription());
            gameViewModel.setPrice(game.getPrice());
            gameViewModel.setStudio(game.getStudio());
            gameViewModel.setQuantity(game.getQuantity());

            result.add(gameViewModel);
        }
        return result;
    }

    public GameViewModel updateGame(GameViewModel gameViewModel){
        Game game = new Game();
        game.setTitle(game.getTitle());
        game.setErsbRating(gameViewModel.getErsbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setPrice(gameViewModel.getPrice());
        game.setStudio(gameViewModel.getStudio());
        game.setQuantity(gameViewModel.getQuantity());
        game.setGameId(gameViewModel.getGameId());
        gameDao.updateGame(game);
        return gameViewModel;
    }

    public void deleteGame(int gameId){
        gameDao.deleteGame(gameId);
    }


    public List<GameViewModel> findGamesByStudio(String studio){
        List<Game> gameList = gameDao.findGamesByStudio(studio);
        List<GameViewModel> gameViewModelList = new ArrayList<>();
        for(Game game : gameList){
            GameViewModel gameViewModel = new GameViewModel();
            gameViewModel.setGameId(game.getGameId());
            gameViewModel.setTitle(game.getTitle());
            gameViewModel.setErsbRating(game.getErsbRating());
            gameViewModel.setDescription(game.getDescription());
            gameViewModel.setPrice(game.getPrice());
            gameViewModel.setStudio(game.getStudio());
            gameViewModel.setQuantity(game.getQuantity());
            gameViewModelList.add(gameViewModel);

        }
        return gameViewModelList;
    }

    public List<GameViewModel> findGamesByERSBRating(String ersbRating){
        List<Game> gameList = gameDao.findGamesByERSBRating(ersbRating);
        List<GameViewModel> gameViewModelList = new ArrayList<>();
        for(Game game : gameList){
            GameViewModel gameViewModel = new GameViewModel();
            gameViewModel.setGameId(game.getGameId());
            gameViewModel.setTitle(game.getTitle());
            gameViewModel.setErsbRating(game.getErsbRating());
            gameViewModel.setDescription(game.getDescription());
            gameViewModel.setPrice(game.getPrice());
            gameViewModel.setStudio(game.getStudio());
            gameViewModel.setQuantity(game.getQuantity());
            gameViewModelList.add(gameViewModel);

        }
        return gameViewModelList;
    }

    public List<GameViewModel> findGamesByTitle(String title){
        List<Game> gameList = gameDao.findGamesByTitle(title);
        List<GameViewModel> gameViewModelList = new ArrayList<>();
        for(Game game : gameList){
            GameViewModel gameViewModel = new GameViewModel();
            gameViewModel.setGameId(game.getGameId());
            gameViewModel.setTitle(game.getTitle());
            gameViewModel.setErsbRating(game.getErsbRating());
            gameViewModel.setDescription(game.getDescription());
            gameViewModel.setPrice(game.getPrice());
            gameViewModel.setStudio(game.getStudio());
            gameViewModel.setQuantity(game.getQuantity());
            gameViewModelList.add(gameViewModel);

        }
        return gameViewModelList;
    }

//T_shirt

    public TshirtVeiwModel addTshirt(TshirtVeiwModel tshirtVeiwModel){
        Tshirt tshirt = new Tshirt();
        tshirt.setSize(tshirtVeiwModel.getSize());
        tshirt.setColor(tshirtVeiwModel.getColor());
        tshirt.setDescription(tshirtVeiwModel.getDescription());
        tshirt.setPrice(tshirtVeiwModel.getPrice());
        tshirt.setQuantity(tshirtVeiwModel.getQuantity());

        tshirt = tshirtDao.addTshirt(tshirt);
        tshirtVeiwModel.setTshirtId(tshirt.getTshirtId());
        return tshirtVeiwModel;
    }

    public TshirtVeiwModel getTshirt(int tshirtId){
        Tshirt tshirt = tshirtDao.getTshirt(tshirtId);
        if(tshirt == null){
            return null;
        }
        TshirtVeiwModel tshirtVeiwModel = new TshirtVeiwModel();
        tshirtVeiwModel.setTshirtId(tshirt.getTshirtId());
        tshirtVeiwModel.setSize(tshirt.getSize());
        tshirtVeiwModel.setColor(tshirt.getColor());
        tshirtVeiwModel.setDescription(tshirt.getDescription());
        tshirtVeiwModel.setPrice(tshirt.getPrice());
        tshirtVeiwModel.setQuantity(tshirt.getQuantity());
        return tshirtVeiwModel;
    }

    public List<TshirtVeiwModel> getAllTshirts() {
        List<Tshirt> tshirtList = tshirtDao.getAllTshirts();
        List<TshirtVeiwModel> result = new ArrayList<>();
        for (Tshirt tshirt : tshirtList) {
            TshirtVeiwModel tshirtVeiwModel = new TshirtVeiwModel();
            tshirtVeiwModel.setTshirtId(tshirt.getTshirtId());
            tshirtVeiwModel.setSize(tshirt.getSize());
            tshirtVeiwModel.setColor(tshirt.getColor());
            tshirtVeiwModel.setDescription(tshirt.getDescription());
            tshirtVeiwModel.setPrice(tshirt.getPrice());
            tshirtVeiwModel.setQuantity(tshirt.getQuantity());

            result.add(tshirtVeiwModel);
        }
        return result;
    }

    public TshirtVeiwModel updateTshirt(TshirtVeiwModel tshirtVeiwModel){
        Tshirt tshirt = new Tshirt();
        tshirt.setSize(tshirtVeiwModel.getSize());
        tshirt.setColor(tshirtVeiwModel.getColor());
        tshirt.setDescription(tshirtVeiwModel.getDescription());
        tshirt.setPrice(tshirtVeiwModel.getPrice());
        tshirt.setQuantity(tshirtVeiwModel.getQuantity());
        tshirt.setTshirtId(tshirtVeiwModel.getTshirtId());
        tshirtDao.updateTshirt(tshirt);
        return tshirtVeiwModel;
    }

    public void deleteTshirt(int tshirtId){
        tshirtDao.deleteTshirt(tshirtId);
    }

    public List<TshirtVeiwModel>  findTshirtByColor(String color){
        List<Tshirt> tshirtList = tshirtDao.findTshirtByColor(color);
        List<TshirtVeiwModel> tshirtVeiwModelList = new ArrayList<>();
        for(Tshirt tshirt : tshirtList){
            TshirtVeiwModel tshirtVeiwModel = new TshirtVeiwModel();
            tshirtVeiwModel.setTshirtId(tshirt.getTshirtId());
            tshirtVeiwModel.setSize(tshirt.getSize());
            tshirtVeiwModel.setColor(tshirt.getColor());
            tshirtVeiwModel.setDescription(tshirt.getDescription());
            tshirtVeiwModel.setPrice(tshirt.getPrice());
            tshirtVeiwModel.setQuantity(tshirt.getQuantity());
            tshirtVeiwModelList.add(tshirtVeiwModel);

        }
        return tshirtVeiwModelList;
    }

    public List<TshirtVeiwModel> findTshirtBySize(String size){
        List<Tshirt> tshirtList = tshirtDao.findTshirtBySize(size);
        List<TshirtVeiwModel> tshirtVeiwModelList = new ArrayList<>();
        for(Tshirt tshirt : tshirtList){
            TshirtVeiwModel tshirtVeiwModel = new TshirtVeiwModel();
            tshirtVeiwModel.setTshirtId(tshirt.getTshirtId());
            tshirtVeiwModel.setSize(tshirt.getSize());
            tshirtVeiwModel.setColor(tshirt.getColor());
            tshirtVeiwModel.setDescription(tshirt.getDescription());
            tshirtVeiwModel.setPrice(tshirt.getPrice());
            tshirtVeiwModel.setQuantity(tshirt.getQuantity());
            tshirtVeiwModelList.add(tshirtVeiwModel);

        }
        return  tshirtVeiwModelList;
    }

}



