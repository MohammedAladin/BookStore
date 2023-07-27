package com.Integration.NTI.Interfaces;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

public class Item {


    @Getter @Setter
    String title;
    @Getter @Setter
    protected String author;

    @Getter @Setter
    protected BigDecimal price;

    @Getter @Setter
    protected Integer quantity;



}
