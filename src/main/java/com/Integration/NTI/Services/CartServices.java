package com.Integration.NTI.Services;
import com.Integration.NTI.Models.Cart;
import com.Integration.NTI.Models.CartItem;
import com.Integration.NTI.Response.PaymentResponse;
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
    private BookService bookService;
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartServices(ShoppingCartRepo cartRepo, UserRepo userRepo,
                         UserService userService, PaymentService paymentService,
                         CartItemRepo cartItemRepo, BookService bookService){
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.paymentService = paymentService;
        this.cartItemRepo = cartItemRepo;
        this.bookService = bookService;
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


        List<CartItem> allItems = cartItemRepo.findAll();
        List<CartItem> items = new ArrayList<>();
        for(CartItem item : allItems){
            if(item.getCart().getId().equals(cartId))
                items.add(item);
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
    public void updateItems(List<CartItem> items){
        for(CartItem item : items){
            cartItemRepo.delete(item);
            bookService.deleteById(item.getBook().getId(), item.getQuantity());
        }
    }
    public String checkOutCart(PaymentRequest paymentRequest) throws PayPalRESTException {
        Long cartId = getCartForCurrentUser().getId();
        List<CartItem> items = getAllCartItems(cartId);
        BigDecimal total = calculateTotalAmount(items);

        paymentRequest.setCartItems(items);
        paymentRequest.setTotal(total);

        PaymentResponse paymentResponse = paymentService.executePayment(paymentRequest);

        updateItems(items);

        return paymentResponse.getApprovalLink();

    }

    public void saveCart(Cart cart){
        cartRepo.save(cart);
    }




}
