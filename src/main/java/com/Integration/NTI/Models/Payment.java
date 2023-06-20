package com.Integration.NTI.Models;
import com.Integration.NTI.Payment.*;
import java.util.List;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class Payment {
    private String intent;
    private Payer payer;
    private List<Transaction> transactions;
    private Redirect redirect;

    public Redirect getRedirect() {
        return redirect;
    }
    public void setRedirect(Redirect redirect) {
        this.redirect = redirect;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
