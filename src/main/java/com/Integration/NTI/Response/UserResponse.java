package com.Integration.NTI.Response;

public class UserResponse {
    public String UserName;
    public String password;
    public Long cartId;

    public UserResponse(String userName, String password, Long cartId) {
        UserName = userName;
        this.password = password;
        this.cartId = cartId;
    }
    public UserResponse(String userName, String password) {
        UserName = userName;
        this.password = password;
    }
}
