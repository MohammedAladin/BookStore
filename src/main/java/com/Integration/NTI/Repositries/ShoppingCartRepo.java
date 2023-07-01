package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.Cart;
import com.Integration.NTI.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface ShoppingCartRepo extends JpaRepository<Cart, Long> {


}
