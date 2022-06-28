package com.wefox.paymentprocessor.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public enum PaymentType {

    ONLINE("online"),
    OFFLINE("offline");

    private final String alias;
    private static final Map<String, PaymentType> paymentTypeMap = new HashMap<String, PaymentType>();

    static {
        for (PaymentType paymentType : PaymentType.values()) {
            log.info("log alias {}", paymentType.alias);
            log.info("log payment type {}", paymentType);

            paymentTypeMap.put(paymentType.alias, paymentType);
        }
    }

    PaymentType(String alias) {
        this.alias=alias;
    }

    public static PaymentType getByAlias(String alias) {
        return paymentTypeMap.get(alias);
    }

    @Override
    public String toString(){
        return this.alias;
    }
}
