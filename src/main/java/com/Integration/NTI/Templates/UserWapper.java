package com.Integration.NTI.Templates;

import com.Integration.NTI.Models.User;
import com.Integration.NTI.Response.UserResponse;
import lombok.Getter;
import lombok.Setter;

public class UserWapper{
    @Setter @Getter
    private User user;
    @Setter @Getter
    private UserResponse userResponse;

    public UserWapper(User user, UserResponse userResponse) {
        this.user = user;
        this.userResponse = userResponse;
    }
}
