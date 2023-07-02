package com.Integration.NTI.Response;
import java.math.BigDecimal;

public class CartItemResponse {

    private String type;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public String getType() {
        return type;
    }
    public String getTitle() {
        return title;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public CartItemResponse(String type, String title, BigDecimal price, Integer quantity) {
        this.type = type;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}
