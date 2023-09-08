package com.Integration.NTI.Models.Requests;

import lombok.Getter;
import lombok.Setter;


public class SignInRequest {

    @Getter @Setter
    private String userName;
    @Getter @Setter
    private String password;
    public SignInRequest(){}
    public SignInRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }


}
