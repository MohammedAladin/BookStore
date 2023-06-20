package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Long> {
}
