package com.Integration.NTI.Models;

import javax.persistence.*;
import java.math.BigDecimal;

public abstract class Item {

    public abstract String getType();
    public abstract String getTitle();
    public abstract BigDecimal getPrice();
    public abstract Integer getQuantity();
    public abstract String getAuthor();

    public abstract void setTitle(String title);
    public abstract void setPrice(BigDecimal price);
    public abstract void setQuantity(Integer quantity);


}
