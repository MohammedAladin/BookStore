package com.Integration.NTI.Models.Entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class BaseBook {


    @Getter @Setter
    String title;
    @Getter @Setter
    protected String author;

    @Getter @Setter
    protected BigDecimal price;

    @Getter @Setter
    protected Integer quantity;



}
