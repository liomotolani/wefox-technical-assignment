package com.wefox.paymentprocessor.utils;

import com.wefox.paymentprocessor.enums.ErrorType;
import com.wefox.paymentprocessor.model.Error;
import com.wefox.paymentprocessor.model.Payment;
import com.wefox.paymentprocessor.model.PaymentDTO;
import com.wefox.paymentprocessor.model.response.ValidateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;



@RequiredArgsConstructor
@Component
@Slf4j
public class RestUtils {


    @Value("${payment.validation.url}")
    private  String paymentValidationUrl;

    @Value("${error.log.url}")
    private  String logErrorUrl;


    @SneakyThrows
    public boolean saveLoggedError(Error error) {
        try {
            RestTemplate restTemplate = getRestTemplate();

            HttpEntity entity = new HttpEntity<>(error);
            ResponseEntity<ValidateResponse> responseEntity = restTemplate.exchange(
                    logErrorUrl,
                    HttpMethod.POST,
                    entity,
                    ValidateResponse.class,
                    1
            );
            return isErrorLogged(responseEntity.getStatusCodeValue());
        } catch (HttpClientErrorException clientErrorException){
            log.info("Client Error {}",clientErrorException.getMessage());
            return false;
        }
    }

    private static boolean isErrorLogged(int statusCode) {
        return statusCode == 200;
    }

    @SneakyThrows
    public boolean validatePayment(Payment payment) {
        try {
            RestTemplate restTemplate = getRestTemplate();

            PaymentDTO paymentDTO = PaymentProcessorUtils.mapPaymentToPaymentDTO(payment);
            HttpEntity entity = new HttpEntity<>(paymentDTO);
            ResponseEntity<ValidateResponse> responseEntity = restTemplate.exchange(
                    paymentValidationUrl,
                    HttpMethod.POST,
                    entity,
                    ValidateResponse.class,
                    1
            );
            return isPaymentValid(responseEntity.getStatusCodeValue());
        } catch (HttpClientErrorException clientErrorException){
            return false;
        }
    }

    private static boolean isPaymentValid(int statusCode) {
        return statusCode == 200;
    }

    private static RestTemplate getRestTemplate() {
        CloseableHttpClient httpClient
                = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }
}
