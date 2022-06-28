package com.wefox.paymentprocessor.payment;


import com.wefox.paymentprocessor.model.Payment;
import com.wefox.paymentprocessor.repositories.AccountRepository;
import com.wefox.paymentprocessor.repositories.PaymentRepository;
import com.wefox.paymentprocessor.utils.PaymentProcessorUtils;
import com.wefox.paymentprocessor.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;




@Slf4j
@RequiredArgsConstructor
public class OfflinePaymentProcessor implements PaymentProcessor {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final RestUtils restUtils;


    @Transactional
    public void savePayment(Payment payment){
        log.info("Started offline process {}", payment.toString());
        PaymentProcessorUtils paymentProcessorUtils = new PaymentProcessorUtils(paymentRepository,accountRepository,restUtils);
        paymentProcessorUtils.checkIfPaymentObjectIsNull(payment);
        paymentProcessorUtils.checkIfAccountExist(payment);
        paymentProcessorUtils.updateAccountSchema(payment);
        paymentRepository.save(payment);
        log.info("Payment was successfully saved  {}", payment);
    }
}
