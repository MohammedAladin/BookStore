package com.Integration.NTI.Services;
import com.Integration.NTI.Models.Cart;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Repositries.ShoppingCartRepo;
import com.Integration.NTI.Repositries.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CartServices {

    private ShoppingCartRepo cartRepo;
    private UserRepo userRepo;


    private UserService userService;

    @Autowired
    private CartServices(ShoppingCartRepo cartRepo, UserRepo userRepo, UserService userService){
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }


    public Cart getCartForCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        System.out.println(userName);

        User user = userService.getByUsername(userName);

        Cart cart = user.getCart();
        if(cart==null){
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }
        return cart;
    }

    public void saveCart(Cart cart){
        cartRepo.save(cart);
    }




}
