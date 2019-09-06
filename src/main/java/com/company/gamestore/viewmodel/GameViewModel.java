package com.company.gamestore.viewmodel;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class GameViewModel {
    private int gameId;
    @NotEmpty(message = "Title cannot be empty.")
    private String title;
    @NotEmpty(message = "ERSB Rating cannot be empty.")
    private String ersbRating;
    @NotEmpty(message = "Description cannot be empty.")
    private String description;
    @NotNull(message = "Price cannot be null.")
    @PositiveOrZero(message = "Price should be greater than zero")
    private BigDecimal price;
    @NotEmpty(message = "Studio cannot be empty.")
    private String studio;
    @Positive(message = "Quantity must be a positive integer.")
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
}
