package com.Integration.NTI.Controllers;

import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Requests.PaymentRequest;
import com.Integration.NTI.Services.PaymentService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("api/")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/execute-payment")
    public ResponseEntity<String> executePayment(@RequestBody PaymentRequest paymentRequest) throws PayPalRESTException {

        try {
            return new ResponseEntity<>(paymentService.checkOutCart(paymentRequest), HttpStatus.OK);
        }catch (CustomException ex){
            return new ResponseEntity<>(ex.getDescription(),ex.getStatus());

        }
    }


}
