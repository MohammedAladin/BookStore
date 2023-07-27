package com.Integration.NTI.Controllers;
import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.CartItem;
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
    private ResponseEntity<String> AddToCart(@RequestBody CartRequest cartRequest)  {


            try {
                cartServices.saveCart(cartRequest);
                return new ResponseEntity<>("ITEM IS ADDED SUCCESSFULLY.. ", HttpStatus.CREATED);
            }catch (NullPointerException ex){
                return new ResponseEntity<>("THIS QUANTITY IS NOT AVAILABLE.. ", HttpStatus.CREATED);
            }catch (CustomException ex){
                return new ResponseEntity<>(ex.getDescription(),ex.getStatus());
            }


    }
    @PostMapping("/FINISHPAYMENT")
    private ResponseEntity<String> finishPayment(@RequestBody PaymentRequest paymentRequest) throws PayPalRESTException{
        try {
            return new ResponseEntity<>(cartServices.checkOutCart(paymentRequest), HttpStatus.OK);
        }catch (CustomException ex){
            return new ResponseEntity<>(ex.getStatus());
        }

    }
    @GetMapping({"","/"})
    private ResponseEntity<List<CartItemResponse>> getAllItems(){
        Long cartId = cartServices.getCartForCurrentUser().getId();
        List<CartItem> items = cartServices.getAllCartItems(cartId);
        List<CartItemResponse> responseItems = new ArrayList<>();
        for(CartItem cartItem : items){
            CartItemResponse cartItemResponse = new CartItemResponse(cartItem.getType(), cartItem.getBook().getTitle(), cartItem.getPrice(), cartItem.getQuantity());
            responseItems.add(cartItemResponse);
        }
        return new ResponseEntity<>(responseItems, HttpStatus.CREATED);

    }

}
