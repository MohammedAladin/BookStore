package com.Integration.NTI.Models.Response;
import com.Integration.NTI.Models.Entities.Book;
import com.Integration.NTI.Models.Entities.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public static List<CartItemResponse> convertToItemResponse(List<CartItem> cartItems){
        List<CartItemResponse> responseList = new ArrayList<>();
        for(CartItem cartItem : cartItems){
            Book book = cartItem.getBook();
            CartItemResponse cartItemResponse = new CartItemResponse(cartItem.getType()
                    ,book.getTitle(),book.getPrice(), cartItem.getQuantity());

            responseList.add(cartItemResponse);
        }
        return responseList;

    }
}
