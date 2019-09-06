package com.company.gamestore.viewmodel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class InvoiceViewModel {
    private int invoiceId;
    @NotEmpty(message = "Name cannot be empty.")
    private String name;
    @NotEmpty(message = "Street cannot be empty.")
    private String street;
    @NotEmpty(message = "City cannot be empty.")
    private String city;
    @NotEmpty(message = "State cannot be empty.")
    @Size(max = 2, min = 2, message = "Please insert 2-digit code for State")
    private String state;
    @NotEmpty(message = "Zipcode cannot be empty.")
    private String zipcode;
    @NotEmpty(message = "Item type cannot be empty.")
    private String itemType;
    @Positive(message = "Item id must be a positive integer.")
    private int itemId;
    private ConsoleViewModel console;
    private GameViewModel game;
    private TshirtVeiwModel tshirt;
    private BigDecimal unitPrice;
    @Positive(message = "Quantity must be a positive integer.")
    private int quantity;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal processingFee;
    private BigDecimal total;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public ConsoleViewModel getConsole() {
        return console;
    }

    public void setConsole(ConsoleViewModel console) {
        this.console = console;
    }

    public GameViewModel getGame() {
        return game;
    }

    public void setGame(GameViewModel game) {
        this.game = game;
    }

    public TshirtVeiwModel getTshirt() {
        return tshirt;
    }

    public void setTshirt(TshirtVeiwModel tshirt) {
        this.tshirt = tshirt;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(BigDecimal processingFee) {
        this.processingFee = processingFee;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
