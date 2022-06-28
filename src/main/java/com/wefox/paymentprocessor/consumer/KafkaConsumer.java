package com.wefox.paymentprocessor.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wefox.paymentprocessor.utils.PaymentProcessorUtils;
import com.wefox.paymentprocessor.utils.RestUtils;
import com.wefox.paymentprocessor.repositories.AccountRepository;
import com.wefox.paymentprocessor.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final RestUtils restUtils;


    @KafkaListener(topics = {"online","offline"}, groupId = "group_id")
    public void consumeTopics(String message) throws JsonProcessingException {
        log.info("Message from kafka {}", message );
       PaymentProcessorUtils paymentProcessorUtils = new PaymentProcessorUtils(paymentRepository,accountRepository,restUtils);
       paymentProcessorUtils.consumeMessageFromKafka(message);
    }


}
