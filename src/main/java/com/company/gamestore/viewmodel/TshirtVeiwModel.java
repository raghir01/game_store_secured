package com.company.gamestore.viewmodel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class TshirtVeiwModel {
    private int tshirtId;
    @NotEmpty(message = "Size cannot be empty.")
    private String size;
    @NotEmpty(message = "Color cannot be empty.")
    private String color;
    @NotEmpty(message = "Description cannot be empty.")
    private String description;
    @NotNull(message = "Price cannot be null.")
    @PositiveOrZero(message = "Price should be greater than zero")
    private BigDecimal price;
    @Positive(message = "Quantity must be a positive integer.")
    private int quantity;

    public int getTshirtId() {
        return tshirtId;
    }

    public void setTshirtId(int tshirtId) {
        this.tshirtId = tshirtId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
