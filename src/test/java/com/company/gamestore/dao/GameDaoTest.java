package com.company.gamestore.dao;

import com.company.gamestore.model.Game;
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
public class GameDaoTest {
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
        List<Game> gameList = gameDao.getAllGames();
        for(Game game : gameList){
            gameDao.deleteGame(game.getGameId());
        }
    }


    @Test
    public void addGame() {
        Game game = new Game();
        game.setTitle("Pacman");
        game.setErsbRating("E");
        game.setDescription("Very good");
        game.setPrice(new BigDecimal("300.00"));
        game.setStudio("Warner Bros");
        game.setQuantity(3);
        game = gameDao.addGame(game);

        Game game1 = gameDao.getGame(game.getGameId());
        assertEquals(game1, game);
    }

    @Test
    public void getGame() {
        Game game1 = new Game();
        game1.setTitle("Pacman");
        game1.setErsbRating("E");
        game1.setDescription("Very good");
        game1.setPrice(new BigDecimal("300.00"));
        game1.setStudio("Warner Bros");
        game1.setQuantity(3);
        game1 = gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setTitle("TempleRun");
        game2.setErsbRating("E");
        game2.setDescription("Very good");
        game2.setPrice(new BigDecimal("500.00"));
        game2.setStudio("Warner Bros");
        game2.setQuantity(3);
        game2 = gameDao.addGame(game2);

        Game game3 = gameDao.getGame(game1.getGameId());
        assertEquals(game1, game3);
        game3 = gameDao.getGame(game2.getGameId());
        assertEquals(game2, game3);
    }


    @Test
    public void getAllGame() {
        Game game1 = new Game();
        game1.setTitle("Pacman");
        game1.setErsbRating("E");
        game1.setDescription("Very good");
        game1.setPrice(BigDecimal.valueOf(200.88));
        game1.setStudio("Warner Bros");
        game1.setQuantity(3);
        game1 = gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setTitle("TempleRun");
        game2.setErsbRating("C");
        game2.setDescription("Very good");
        game2.setPrice(BigDecimal.valueOf(400.88));
        game2.setStudio("Warner Bros");
        game2.setQuantity(3);
        game2 = gameDao.addGame(game2);

        List<Game> gameList = gameDao.getAllGames();
        assertEquals(2, gameList.size());
    }

    @Test
    public void updateGame() {
        Game game = new Game();
        game.setTitle("Pacman");
        game.setErsbRating("E");
        game.setDescription("Very good");
        game.setPrice(new BigDecimal("300.00"));
        game.setStudio("Warner Bros");
        game.setQuantity(3);
        game = gameDao.addGame(game);

        game.setTitle("Minecraft");
        game.setErsbRating("C");
        gameDao.updateGame(game);

        Game game1 = gameDao.getGame(game.getGameId());
        assertEquals(game1, game);
    }

    @Test
    public void deleteGame() {
        Game game = new Game();
        game.setTitle("Pacman");
        game.setErsbRating("E");
        game.setDescription("Very good");
        game.setPrice(new BigDecimal("300.00"));
        game.setStudio("Warner Bros");
        game.setQuantity(3);
        game = gameDao.addGame(game);

        gameDao.deleteGame(game.getGameId());
        Game game1 = gameDao.getGame(game.getGameId());
        assertNull(game1);
    }

    @Test
    public void findGamesByStudio() {
        Game game = new Game();
        game.setTitle("Pacman");
        game.setErsbRating("E");
        game.setDescription("Very good");
        game.setPrice(new BigDecimal("300.00"));
        game.setStudio("Warner Bros");
        game.setQuantity(3);
        game = gameDao.addGame(game);

        List<Game> gameList = gameDao.findGamesByStudio("Warner Bros");
        assertEquals(1, gameList.size());
        assertEquals("Warner Bros", gameList.get(0).getStudio());
    }

    @Test
    public void findGamesByERSBRating() {
        Game game = new Game();
        game.setTitle("Pacman");
        game.setErsbRating("E");
        game.setDescription("Very good");
        game.setPrice(new BigDecimal("300.00"));
        game.setStudio("Warner Bros");
        game.setQuantity(3);
        game = gameDao.addGame(game);

        List<Game> gameList = gameDao.findGamesByERSBRating("E");
        assertEquals(1,gameList.size());
        assertEquals("E", gameList.get(0).getErsbRating());
    }

    @Test
    public void findGamesByTitle() {
        Game game = new Game();
        game.setTitle("Pacman");
        game.setErsbRating("E");
        game.setDescription("Very good");
        game.setPrice(new BigDecimal("300.00"));
        game.setStudio("Warner Bros");
        game.setQuantity(3);
        game = gameDao.addGame(game);

        List<Game> gameList = gameDao.findGamesByTitle("Pacman");
        assertEquals(1, gameList.size());
        assertEquals("Pacman", gameList.get(0).getTitle());
    }
}