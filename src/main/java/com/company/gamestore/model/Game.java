package com.company.gamestore.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Game {
    private int gameId;
    private String title;
    private String ersbRating;
    private String description;
    private BigDecimal price;
    private String studio;
    private int quantity;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getErsbRating() {
        return ersbRating;
    }

    public void setErsbRating(String ersbRating) {
        this.ersbRating = ersbRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId &&
                quantity == game.quantity &&
                Objects.equals(title, game.title) &&
                Objects.equals(ersbRating, game.ersbRating) &&
                Objects.equals(description, game.description) &&
                Objects.equals(price, game.price) &&
                Objects.equals(studio, game.studio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, title, ersbRating, description, price, studio, quantity);
    }
}

