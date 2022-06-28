package com.wefox.paymentprocessor.payment;

import com.wefox.paymentprocessor.model.Payment;

public interface PaymentProcessor {
     void savePayment(Payment payment);
}
