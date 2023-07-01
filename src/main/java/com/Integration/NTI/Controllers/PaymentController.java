package com.Integration.NTI.Controllers;

import com.Integration.NTI.Requests.PaymentRequest;
import com.Integration.NTI.Services.CartServices;
import com.Integration.NTI.Services.PaymentService;
import javax.servlet.http.HttpSession;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.api.payments.*;

@RestController
public class PaymentController {
    private PaymentService paymentService;
    private CartServices cartServices;

    public PaymentController(CartServices cartServices) {
        this.cartServices = cartServices;
    }
    @Autowired
    private HttpSession session;
    @GetMapping("/execute-payment")
    public ResponseEntity<String> executePayment(@RequestBody PaymentRequest paymentRequest) throws PayPalRESTException {

        cartServices.checkOutCart(paymentRequest);
        return new ResponseEntity<>("PAYMENT SUCCESSFULLY DONE", HttpStatus.OK);
    }


}
