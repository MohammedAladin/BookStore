package com.Integration.NTI.Payment;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.CartItem;
import com.Integration.NTI.Requests.PaymentRequest;
import com.Integration.NTI.Services.BookService;
import com.paypal.api.payments.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PayPalPaymentConverter {

    private BookService bookService;

    @Autowired
    public PayPalPaymentConverter(BookService bookService){
        this.bookService = bookService;
    }


    public Payment toPayPalPayment(PaymentRequest request , List<CartItem> items) {
        // Create a PayPal Payment object
        Payment payment = new Payment();

        // Set the payment intent (sale or authorization)
        payment.setIntent("sale");

        // Set the payer information
        Payer payer = new Payer();

        payer.setPaymentMethod("paypal");

        payment.setPayer(payer);

        // Set the transaction information
        Transaction transaction = new Transaction();

       Amount amount =  new Amount(request.getCurrency(),request.getTotal().setScale(2, RoundingMode.HALF_UP).toString());
        transaction.setAmount(amount);

        transaction.setDescription(request.getDescription());


        ItemList itemList = new ItemList();
        List<Item> newItems = new ArrayList<>();

        for(CartItem cartItem : items){
            Book book = bookService.getById(cartItem.getBook().getId());

            Item item = new Item();

            item.setName(book.getTitle());

            item.setCurrency(request.getCurrency());

            item.setQuantity(String.valueOf(cartItem.getQuantity()));

            item.setPrice(book.getPrice().toString());

            newItems.add(item);
        }


        itemList.setItems(newItems);
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        payment.setTransactions(transactions);


        // Set the URLs for redirecting the user after the payment is complete
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(request.getSuccessUrl());
        redirectUrls.setCancelUrl(request.getCancelUrl());
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }
}
