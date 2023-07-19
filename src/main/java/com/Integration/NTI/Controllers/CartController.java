package com.Integration.NTI.Controllers;
import com.Integration.NTI.FactoryDesign.ItemFactory;
import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.Cart;
import com.Integration.NTI.Models.CartItem;
import com.Integration.NTI.Models.Item;
import com.Integration.NTI.Requests.CartRequest;
import com.Integration.NTI.Requests.PaymentRequest;
import com.Integration.NTI.Response.CartItemResponse;
import com.Integration.NTI.Services.BookService;
import com.Integration.NTI.Services.CartServices;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

            if(book.getQuantity() < cartRequest.getQuantity())
                return new ResponseEntity<>("YOUR QUANTITY IS HIGHER THAN THE QUANTITY OF REQUIRED BOOK", HttpStatus.NOT_FOUND);

            CartItem newCartItem = new CartItem("Book",book, cartRequest.getQuantity());

            Cart cart = cartServices.getCartForCurrentUser();

            cart.addCartItem(newCartItem);

            cartServices.saveCart(cart);

            return new ResponseEntity<>("ITEM IS ADDED SUCCESSFULLY TO : "+cart.getUser().getUserName() + " CART", HttpStatus.CREATED);

    }
    @PostMapping("/checkOutCart")
    private ResponseEntity<String> checkOutCart(@RequestBody PaymentRequest paymentRequest) throws PayPalRESTException {
        return new ResponseEntity<>(cartServices.checkOutCart(paymentRequest), HttpStatus.OK);
    }
    @GetMapping({"","/"})
    private ResponseEntity<List<CartItemResponse>> getAllItems(){
        Long cartId = cartServices.getCartForCurrentUser().getId();
        List<CartItem> items = cartServices.getAllCartItems(cartId);
        List<CartItemResponse> responseItems = new ArrayList<>();
        for(CartItem cartItem : items){
            CartItemResponse cartItemResponse = new CartItemResponse(cartItem.getType(), cartItem.getBook().getType(), cartItem.getPrice(), cartItem.getQuantity());
            responseItems.add(cartItemResponse);
        }
        return new ResponseEntity<>(responseItems, HttpStatus.CREATED);

    }

}
