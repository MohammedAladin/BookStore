package com.Integration.NTI.Response;

import com.Integration.NTI.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    public String UserName;
    public boolean isAdmin;
    public Long cartId;

    public UserResponse(String userName, Long cartId, boolean isAdmin) {
        UserName = userName;
        this.cartId = cartId;
        this.isAdmin = isAdmin;
    }
    public UserResponse(String userName, boolean isAdmin) {
        UserName = userName;
        this.isAdmin = isAdmin;
    }

    public static List<UserResponse> ConvertToUserResponse(List<User> users){
        List<UserResponse> responses = new ArrayList<>();
        for(User user : users){
            UserResponse userResponse;
            try {
                userResponse = new UserResponse(user.getUserName(), user.getCart().getId(), user.isAdmin());

            }catch (NullPointerException e){
                userResponse = new UserResponse(user.getUserName(), user.isAdmin());
            }
            responses.add(userResponse);
        }

        return responses;
    }
    public static UserResponse ConvertToUserResponse(User user){
            UserResponse userResponse;
            try {
                userResponse = new UserResponse(user.getUserName(), user.getCart().getId(), user.isAdmin());

            }catch (NullPointerException e){
                userResponse = new UserResponse(user.getUserName(), user.isAdmin());
            }

        return userResponse;
    }
}
