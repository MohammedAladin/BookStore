package com.Integration.NTI.FactoryDesign;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.Item;

import java.math.BigDecimal;

public class ItemFactory {
    public static Item getItem(String type, BigDecimal price, Integer quantity) {
        if ("BOOK".equalsIgnoreCase(type))
            return new Book(price, quantity);
        //else if("other".equalsIgnoreCase(type)) return new Server(ram, hdd, cpu);

        return null;
    }
}
