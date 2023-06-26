package com.Integration.NTI.Controllers;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.Cart;
import com.Integration.NTI.Models.CartItem;
import com.Integration.NTI.Requests.CartRequest;
import com.Integration.NTI.Services.BookService;
import com.Integration.NTI.Services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cart")
public class CartController {

    private CartServices cartServices;
    private BookService bookService;

    @Autowired
    private CartController(CartServices cartServices, BookService bookService){
        this.cartServices = cartServices;
        this.bookService = bookService;
    }

    @PostMapping("/AddToCart")
    private ResponseEntity<String> AddToCart(@RequestBody CartRequest cartRequest){
        Book book = bookService.getById(cartRequest.getBookId());

        CartItem newCartItem = new CartItem(book, cartRequest.getQuantity());

        Cart cart = cartServices.getCartForCurrentUser();

        cart.addCartItem(newCartItem);

        cartServices.saveCart(cart);


        return new ResponseEntity<>("ITEM IS ADDED SUCCESSFULLY TO : "+cart.getUser().getUserName() + " CART", HttpStatus.CREATED);

    }
}
