package com.Integration.NTI.Response;

import com.Integration.NTI.Models.User;

import java.util.ArrayList;
import java.util.List;

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

    public static List<UserResponse> ConvertToUserResponse(List<User> users){
        List<UserResponse> responses = new ArrayList<>();
        for(User user : users){
            UserResponse userResponse;
            try {
                userResponse = new UserResponse(user.getUserName(), user.getPassword(), user.getCart().getId());
            }catch (NullPointerException e){
                userResponse = new UserResponse(user.getUserName(), user.getPassword());
            }
            responses.add(userResponse);
        }
        return responses;
    }
}
