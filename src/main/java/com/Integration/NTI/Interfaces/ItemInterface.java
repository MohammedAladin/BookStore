package com.Integration.NTI.Interfaces;

import java.math.BigDecimal;

public interface ItemInterface {
    String title="";
    String auth="";
    BigDecimal price=BigDecimal.ZERO;
    public  String getTitle();
    public  BigDecimal getPrice();
    public  String getAuth();

    public  Integer getQuantity();



    public  void setTitle(String title);
    public  void setPrice(BigDecimal price);
    public  void setQuantity(Integer quantity);

    public  void setAuth(String auth);



}
