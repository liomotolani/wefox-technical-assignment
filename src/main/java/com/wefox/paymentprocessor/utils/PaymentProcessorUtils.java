package com.wefox.paymentprocessor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.paymentprocessor.enums.ErrorType;
import com.wefox.paymentprocessor.enums.PaymentType;
import com.wefox.paymentprocessor.model.Account;
import com.wefox.paymentprocessor.model.Error;
import com.wefox.paymentprocessor.model.Payment;
import com.wefox.paymentprocessor.model.PaymentDTO;
import com.wefox.paymentprocessor.model.RepoHolder;
import com.wefox.paymentprocessor.payment.PaymentProcessor;
import com.wefox.paymentprocessor.payment.PaymentProcessorFactory;
import com.wefox.paymentprocessor.repositories.AccountRepository;
import com.wefox.paymentprocessor.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorUtils {


    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    private final RestUtils restUtils;



    public void checkIfPaymentObjectIsNull(Payment payment){
        if(payment == null){
            logError(ErrorType.OTHER,"null","Payment object is null");
            return;
        }
    }

    public void checkIfAccountExist(Payment payment){
        Optional<Account> account = fetchAccountById(payment);
        if(!account.isPresent()){
            logError(ErrorType.DATABASE,payment.getId(),"Account doesn't exist");
            return;
        }
    }

    public void updateAccountSchema(Payment payment){
        Account account = fetchAccountById(payment).get();
        account.setLastPaymentDate(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);
        log.info("Account was successfully updated");
    }

    private Optional<Account> fetchAccountById(Payment payment){
       return accountRepository.findById(payment.getAccount().getId());
    }

    public void checkIfPaymentIsValid(Payment payment){
        if(!restUtils.validatePayment(payment)){
            logError(ErrorType.NETWORK,payment.getId(),"Payment is invalid");
            return;
        }
    }

    public void consumeMessageFromKafka(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaymentDTO paymentDTO = mapper.readValue(message, PaymentDTO.class);
        savePayment(paymentDTO);

    }

    private void savePayment(PaymentDTO paymentDTO) {
        Payment payment = mapPaymentDTOtoPayment(paymentDTO);
        RepoHolder repoHolder = new RepoHolder(accountRepository, paymentRepository, restUtils);
        PaymentProcessor paymentProcessor = PaymentProcessorFactory.processPayment(payment.getPaymentType(),repoHolder);
        if (paymentProcessor == null) {
            logError(ErrorType.OTHER,payment.getId(),"Payment can't be processed");
            throw new AssertionError();
        }
        paymentProcessor.savePayment(payment);
    }

    private Payment mapPaymentDTOtoPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getPayment_id());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentType(PaymentType.getByAlias(paymentDTO.getPayment_type()));
        payment.setCreditCard(paymentDTO.getCredit_card());
        Account account = new Account();
        account.setId(paymentDTO.getAccount_id());
        payment.setAccount(account);
        payment.setDelay(paymentDTO.getDelay());
        return payment;
    }

    public static PaymentDTO mapPaymentToPaymentDTO(Payment payment){
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .account_id(payment.getAccount().getId())
                .payment_id(payment.getId())
                .payment_type(payment.getPaymentType().name())
                .credit_card(payment.getCreditCard())
                .delay(payment.getDelay())
                .amount(payment.getAmount()).build();
        return paymentDTO;
    }

    public void logError(ErrorType errorType, String paymentId, String description) {
        Error error = Error.builder()
                .payment_id(paymentId)
                .error_type(errorType.name())
                .error_description(description).build();
        restUtils.saveLoggedError(error);
    }


}
