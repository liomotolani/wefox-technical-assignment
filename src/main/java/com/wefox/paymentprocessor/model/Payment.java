package com.wefox.paymentprocessor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wefox.paymentprocessor.enums.PaymentType;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;



@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @Column(name = "payment_id")
    @JsonProperty("payment_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonProperty("account_id")
    private Account account;

    @Column(name = "payment_type")
    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @Column(name = "credit_card")
    @JsonProperty("credit_card")
    private String creditCard;

    private Integer amount;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    private Integer delay;

}
