package com.Integration.NTI.Controllers;
import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Requests.CartRequest;

import com.Integration.NTI.Models.Response.CartItemResponse;
import com.Integration.NTI.Services.CartServices;
import com.Integration.NTI.Templates.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/cart")
public class CartController {

    private final CartServices cartServices;

    @Autowired
    private CartController(CartServices cartServices){
        this.cartServices = cartServices;
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
    @GetMapping({"","/"})
    private ResponseEntity<ResponseWrapper<CartItemResponse>> getAllItems() {
        ResponseWrapper<CartItemResponse> cartItemResponseResponseWrapper = new ResponseWrapper<>();

        try {
            cartItemResponseResponseWrapper.setDataList(cartServices.getAllCartItems().getCartItemResponses());
            cartItemResponseResponseWrapper.setErrorMessage("THERE ARE NO ERRORS HERE...");
            return new ResponseEntity<>(cartItemResponseResponseWrapper,HttpStatus.OK);

        }catch (CustomException ex){
            cartItemResponseResponseWrapper.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(cartItemResponseResponseWrapper,ex.getStatus());

        }
    }
}
