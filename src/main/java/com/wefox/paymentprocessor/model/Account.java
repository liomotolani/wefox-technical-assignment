package com.wefox.paymentprocessor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;



@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    @JsonProperty("account_id")
    private Integer id;

    @Column(unique = true)
    private String email;

    @Column(name = "birth_date")
    @JsonProperty("birth_date")
    private Date birthDate;

    @Column(name = "last_payment_date")
    @JsonProperty("last_payment_date")
    private Timestamp lastPaymentDate;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());


}
