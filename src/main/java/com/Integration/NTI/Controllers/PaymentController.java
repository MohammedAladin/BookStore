package com.Integration.NTI.Controllers;

import com.Integration.NTI.Models.PaymentRequest;
import com.Integration.NTI.Services.PaymentService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.api.payments.*;
import java.net.URI;
import java.util.List;

@RestController
public class PaymentController {
    private PaymentService paymentService = new PaymentService();
    @Autowired
    private HttpSession session;
    @GetMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            Payment payment = paymentService.createPayment(paymentRequest.getTotal(), paymentRequest.getCurrency()
                    , paymentRequest.getDescription(), paymentRequest.getCancelUrl(), paymentRequest.getSuccessUrl());

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    Payer payer = payment.getPayer();
                    session.setAttribute("paymentId", payment.getId());


                    if (payer != null && payer.getPayerInfo() != null) {
                        session.setAttribute("payerId", payer.getPayerInfo().getPayerId());
                    } else {
                        return new ResponseEntity<>("Payer_id is null value", HttpStatus.NOT_FOUND);
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(link.getHref());
                }
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No approval URL found in PayPal response");
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/execute-payment")
    public ResponseEntity<String> executePayment() {
        try {
            String payerId = (String) session.getAttribute("payerId");
            String paymentId = (String) session.getAttribute("paymentId");
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return ResponseEntity.status(HttpStatus.OK).body("Payment successful");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment not approved");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
