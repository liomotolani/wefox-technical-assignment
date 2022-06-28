package com.wefox.paymentprocessor.model.response;

import lombok.Data;



@Data
public class ValidateResponse {

        private Boolean isValid;
        private String message;
        private int statusCode;


}
