package com.wefox.paymentprocessor.enums;

import java.util.HashMap;
import java.util.Map;

public enum ErrorType {

    DATABASE("database"),
    NETWORK("network"),
    OTHER("other");


    private final String alias;
    private static final Map<String, ErrorType> errorTypeMap = new HashMap<String, ErrorType>();

    static {
        for (ErrorType errorType : ErrorType.values()) {
            errorTypeMap.put(errorType.alias, errorType);
        }
    }

    ErrorType(String alias) {
        this.alias=alias;
    }

    public static ErrorType getByAlias(String alias) {
        return errorTypeMap.get(alias);
    }

    @Override
    public String toString(){
        return this.alias;
    }
}
