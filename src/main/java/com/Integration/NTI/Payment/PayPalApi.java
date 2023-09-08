package com.Integration.NTI.Payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PayPalApi {

    private String clientId = "AdwbUW-uOWYBivFwXqfZQDrkz-XA_I8CgZQ9DI_xjfM0evMwOyLKqVx0FsU0aPtx4NXczvQlt1FEzpB5";

    private  String clientSecret = "EETsQFw1dK4ek8qybGt1haY_KbeOjVOXdm4nr_jyYUC1lo5FEIHF6Tx-E624Xv-oA8KP2SJgG2myXiPP";

    private  String mode = "sandbox";


    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getMode() {
        return mode;
    }
}
