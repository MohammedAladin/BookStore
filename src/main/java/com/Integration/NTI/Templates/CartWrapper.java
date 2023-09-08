package com.Integration.NTI.Templates;

import com.Integration.NTI.Models.Entities.CartItem;
import com.Integration.NTI.Models.Response.CartItemResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class CartWrapper {
    @Setter @Getter
    private CartItemResponse cartItemResponse;
    @Setter @Getter
    private CartItem cartItem;

    @Setter @Getter
    private List<CartItemResponse> cartItemResponses;
    @Setter @Getter
    private List<CartItem> cartItems;

    public CartWrapper(List<CartItemResponse> cartItemResponses, List<CartItem> cartItems) {
        this.cartItemResponses = cartItemResponses;
        this.cartItems = cartItems;
    }
    public CartWrapper(CartItemResponse cartItemResponse, CartItem cartItem) {
        this.cartItemResponse = cartItemResponse;
        this.cartItem = cartItem;
    }
}
