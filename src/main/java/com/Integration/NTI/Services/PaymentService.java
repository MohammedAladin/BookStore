package com.Integration.NTI.Services;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.paypal.base.rest.APIContext;
import com.paypal.api.payments.*;


@Service
public class PaymentService {

    @Value("${paypal.clientId}")
    private final String CLIENT_ID = "AdwbUW-uOWYBivFwXqfZQDrkz-XA_I8CgZQ9DI_xjfM0evMwOyLKqVx0FsU0aPtx4NXczvQlt1FEzpB5";

    @Value("${paypal.clientSecret}")
    private final String CLIENT_SECRET = "EETsQFw1dK4ek8qybGt1haY_KbeOjVOXdm4nr_jyYUC1lo5FEIHF6Tx-E624Xv-oA8KP2SJgG2myXiPP";

    @Value("${paypal.mode}")
    private final String MODE = "sandbox";

    public Payment createPayment(Double total, String currency, String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirect = new RedirectUrls();
        redirect.setCancelUrl(cancelUrl);
        redirect.setReturnUrl(successUrl);


        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirect);

        
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment createdPayment = payment.create(apiContext);
        String approvalURL = createdPayment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No approval_url link found in payment"))
                .getHref();

        System.out.println(approvalURL);

        return createdPayment;
    }
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        // create API context with PayPal credentials
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        // execute payment and get payment details
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
