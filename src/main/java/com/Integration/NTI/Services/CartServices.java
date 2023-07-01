package com.Integration.NTI.Services;
import com.Integration.NTI.Models.Cart;
import com.Integration.NTI.Models.CartItem;
import com.Integration.NTI.Models.PaymentResponse;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Repositries.CartItemRepo;
import com.Integration.NTI.Repositries.ShoppingCartRepo;
import com.Integration.NTI.Repositries.UserRepo;
import com.Integration.NTI.Requests.PaymentRequest;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServices {

    private ShoppingCartRepo cartRepo;
    private UserRepo userRepo;


    private UserService userService;

    private PaymentService paymentService;
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartServices(ShoppingCartRepo cartRepo, UserRepo userRepo,
                         UserService userService, PaymentService paymentService,
                         CartItemRepo cartItemRepo){
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.paymentService = paymentService;
        this.cartItemRepo = cartItemRepo;
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

    public List<CartItem> getAllCartItems(Long cartId){

        List<CartItem> items = new ArrayList<>();
        List<CartItem> allItems = cartItemRepo.findAll();
        for(CartItem item : allItems){
            if(item.getCart().getId()==cartId)items.add(item);
        }
        return items;
    }
    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemTotal = cartItem.getPrice();
            total = total.add(itemTotal);
        }
        return total;
    }
    public String checkOutCart(PaymentRequest paymentRequest) throws PayPalRESTException {
        Long cartId = getCartForCurrentUser().getId();
        List<CartItem> items = getAllCartItems(cartId);
        BigDecimal total = calculateTotalAmount(items);

        paymentRequest.setCartItems(items);
        paymentRequest.setTotal(total);

        PaymentResponse paymentResponse = paymentService.executePayment(paymentRequest);

        return paymentResponse.getApprovalLink();



    }

    public void saveCart(Cart cart){
        cartRepo.save(cart);
    }




}
