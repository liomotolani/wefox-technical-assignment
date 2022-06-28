package com.wefox.paymentprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

    private String payment_id;


    private Integer account_id;

    private String payment_type;

    private String credit_card;

    private Integer amount;

    private Integer delay;

}
