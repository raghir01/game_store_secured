package com.company.gamestore.dao;

import com.company.gamestore.model.Game;

import java.util.List;

public interface GameDao {
    Game addGame(Game game);

    Game getGame(int gameId);

    List<Game> getAllGames();

    Game updateGame(Game game);

    void deleteGame(int gameId);

    List<Game> findGamesByStudio(String studio);

    List<Game> findGamesByERSBRating(String ersbRating);

    List<Game> findGamesByTitle(String title);

}

