package com.Integration.NTI.Templates;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResponseWrapper<T>{
    @Setter @Getter
    private List<T> dataList;
    @Setter @Getter
    private T data;
    @Setter @Getter
    private String errorMessage;
}
