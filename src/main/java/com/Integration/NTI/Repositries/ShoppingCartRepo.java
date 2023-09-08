package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepo extends JpaRepository<Cart, Long> {


}
