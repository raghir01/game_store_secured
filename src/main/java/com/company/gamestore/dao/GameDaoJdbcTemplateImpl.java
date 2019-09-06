package com.company.gamestore.dao;

import com.company.gamestore.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoJdbcTemplateImpl  implements GameDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoJdbcTemplateImpl(JdbcTemplate newjdbcTemplate){
        this.jdbcTemplate = newjdbcTemplate;
    }

    //Prepared Statement
    private static final String INSERT_GAME_SQL =
            "insert into game (title, ersb_rating, description, price, studio, quantity) values (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_GAME_SQL =
            "select * from game where game_id = ?";

    private static final String SELECT_ALL_GAME_SQL =
            "select * from game";

    private static final String DELETE_GAME_SQL =
            "delete from game where game_id = ?";

    private static final String UPDATE_GAME_SQL =
            "update game set title = ?, ersb_rating = ?, description = ?, price = ?, studio = ?, quantity = ? where game_id = ?";

    private static final String SELECT_GAME_BY_STUDIO_SQL =
            "select * from game where studio = ?";

    private static final String SELECT_GAME_BY_ERSBRATING_SQL =
            "select * from game where ersb_rating = ?";

    private static final String SELECT_GAME_BY_TITLE_SQL =
            "select * from game where title = ?";
    @Override
    public Game addGame(Game game) {
        jdbcTemplate.update(
                INSERT_GAME_SQL,
                game.getTitle(),
                game.getErsbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        game.setGameId(id);
        return game;
    }

    private Game mapRowToGame(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setGameId(rs.getInt("game_id"));
        game.setTitle(rs.getString("title"));
        game.setErsbRating(rs.getString("ersb_rating"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getBigDecimal("price"));
        game.setStudio(rs.getString("studio"));
        game.setQuantity(rs.getInt("quantity"));

        return game;
    }


    @Override
    public Game getGame(int gameId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_GAME_SQL, this::mapRowToGame, gameId);
        } catch (EmptyResultDataAccessException e){
            // if there is no match for this game id, return null
            return null;
        }
    }


    @Override
    public List<Game> getAllGames() {
        return jdbcTemplate.query(SELECT_ALL_GAME_SQL, this::mapRowToGame);
    }


    @Override
    public Game updateGame(Game game) {
        jdbcTemplate.update(
                UPDATE_GAME_SQL,
                game.getTitle(),
                game.getErsbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity(),
                game.getGameId());
        return game;
    }


    @Override
    public void deleteGame(int gameId) {
        jdbcTemplate.update(DELETE_GAME_SQL, gameId);
    }

    @Override
    public List<Game> findGamesByStudio(String studio) {
        return jdbcTemplate.query(SELECT_GAME_BY_STUDIO_SQL, this::mapRowToGame, studio);
    }


    @Override
    public List<Game> findGamesByERSBRating(String ersbRating) {
        return jdbcTemplate.query(SELECT_GAME_BY_ERSBRATING_SQL, this::mapRowToGame, ersbRating);
    }


    @Override
    public List<Game> findGamesByTitle(String title) {
        return jdbcTemplate.query(SELECT_GAME_BY_TITLE_SQL, this::mapRowToGame, title);
    }
}
