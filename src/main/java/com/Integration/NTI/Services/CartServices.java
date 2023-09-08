package com.Integration.NTI.Services;
import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Entities.Book;
import com.Integration.NTI.Models.Entities.Cart;
import com.Integration.NTI.Models.Entities.CartItem;
import com.Integration.NTI.Models.Requests.CartRequest;
import com.Integration.NTI.Models.Response.CartItemResponse;
import com.Integration.NTI.Models.Entities.User;
import com.Integration.NTI.Repositries.CartItemRepo;
import com.Integration.NTI.Repositries.ShoppingCartRepo;
import com.Integration.NTI.Templates.CartWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServices {

    private final ShoppingCartRepo cartRepo;
    private final UserService userService;
    private final BookService bookService;
    private final CartItemRepo cartItemRepo;

    @Autowired
    private AuthService authService;
    @Autowired
    private CartServices(ShoppingCartRepo cartRepo,
                         UserService userService,
                         CartItemRepo cartItemRepo, BookService bookService){
        this.cartRepo = cartRepo;
        this.userService = userService;
        this.cartItemRepo = cartItemRepo;
        this.bookService = bookService;
    }
    public Cart getCartForCurrentUser() throws CustomException {
        User user;
        try {
            user = authService.returnUserAuth();
        }catch (CustomException ex){
            throw new CustomException(ex.getDescription(),ex.getStatus());
        }
        Cart cart = user.getCart();
        if(cart==null){
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }
        return cart;
    }

    public CartWrapper getAllCartItems() throws CustomException {
        User user;
        try {
            user = authService.returnUserAuth();
        }catch (CustomException ex){
            throw new CustomException(ex.getDescription(), ex.getStatus());
        }
        Long cartId = user.getCart().getId();

        List<CartItem> allItems = cartItemRepo.findAll();

        List<CartItem> items = new ArrayList<>();

        for(CartItem item : allItems){
            if(item.getCart().getId().equals(cartId))
                items.add(item);
        }

        List<CartItemResponse> cartItemResponses = CartItemResponse.convertToItemResponse(items);

        return new CartWrapper(cartItemResponses, items);

    }
    public BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemTotal = cartItem.getPrice();
            total = total.add(itemTotal);
        }
        return total;
    }
    public void updateItems(List<CartItem> items) throws CustomException{
        for(CartItem item : items){
            cartItemRepo.delete(item);
            try {
                bookService.deleteById(item.getBook().getBookId(), item.getQuantity());
            }
            catch (CustomException ex){
                throw new CustomException(ex.getDescription(), ex.getStatus());
            }
        }
    }
    public void saveCart(CartRequest cartRequest) throws NullPointerException, CustomException {
        Book book;
        try {
         book = bookService.getByIdLocal(cartRequest.getBookId());
        }catch (CustomException ex){
            throw new CustomException(ex.getDescription(),ex.getStatus());
        }

        CartItem newCartItem;
        Cart cart = getCartForCurrentUser();

        List<CartItem> items = getAllCartItems().getCartItems();
        boolean flg = false;
        for(CartItem cartItem : items){
            if(book.getTitle().equals(cartItem.getBook().getTitle())) {

                if(book.getQuantity() < cartItem.getQuantity() + cartRequest.getQuantity())
                    throw new CustomException("THIS QUANTITY IS NOT AVAILABLE..", HttpStatus.NO_CONTENT);

                cartItem.setQuantity(cartItem.getQuantity() + cartRequest.getQuantity());
                cartItemRepo.save(cartItem);
                flg = true;
                break;
            }
        }
        if(!flg) {
            newCartItem = new CartItem("Book", book, cartRequest.getQuantity());
            cart.addCartItem(newCartItem);
        }
        cartRepo.save(cart);
    }




}
