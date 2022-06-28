package com.wefox.paymentprocessor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {

    private String payment_id;
    private String error_type;
    private String error_description;

}
