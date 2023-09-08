package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}
