package com.Integration.NTI.Services;
import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.CartItem;
import com.Integration.NTI.Payment.PayPalApi;
import com.Integration.NTI.Response.PaymentResponse;
import com.Integration.NTI.Payment.PayPalPaymentConverter;
import com.Integration.NTI.Requests.PaymentRequest;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.paypal.base.rest.APIContext;
import com.paypal.api.payments.*;


@Service
public class PaymentService {


    private PayPalApi payPalApi;
    private PayPalPaymentConverter payPalPaymentConverter;

    @Autowired
    public PaymentService(PayPalPaymentConverter payPalPaymentConverter, PayPalApi payPalApi){
        this.payPalPaymentConverter = payPalPaymentConverter;
        this.payPalApi = payPalApi;
    }



    public PaymentResponse executePayment(PaymentRequest request, List<CartItem> items) throws PayPalRESTException, CustomException {
        // Convert the PaymentRequest to a PayPal Payment object
        Payment payment = payPalPaymentConverter.toPayPalPayment(request, items);
        System.out.println("payment sett");

        // Create the payment using the PayPal API
        APIContext apiContext = new APIContext(payPalApi.getClientId(), payPalApi.getClientSecret(), payPalApi.getMode());
        Payment createdPayment = payment.create(apiContext);

        List<Links> links = createdPayment.getLinks();
        String approvalLink = null;
        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus(createdPayment.getState());
        paymentResponse.setPaymentId(createdPayment.getId());
        paymentResponse.setApprovalLink(approvalLink);
        return paymentResponse;
    }
}
