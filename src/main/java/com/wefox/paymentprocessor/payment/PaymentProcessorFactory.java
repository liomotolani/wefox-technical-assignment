package com.wefox.paymentprocessor.payment;

import com.wefox.paymentprocessor.enums.PaymentType;
import com.wefox.paymentprocessor.model.RepoHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
public final class PaymentProcessorFactory {

    @Transactional
    public static PaymentProcessor processPayment(PaymentType paymentType, RepoHolder repoHolder){
        log.info("I got into the factory ");

        switch (paymentType){
            case ONLINE:
                log.info("I got into the online factory" );
                return new OnlinePaymentProcessor(repoHolder.getPaymentRepository(), repoHolder.getAccountRepository(),repoHolder.getRestUtils());
            case OFFLINE:
                log.info("I got into the offline factory");
                return new OfflinePaymentProcessor(repoHolder.getPaymentRepository(), repoHolder.getAccountRepository(),repoHolder.getRestUtils());
            default:
                return null;
        }
    }

}
