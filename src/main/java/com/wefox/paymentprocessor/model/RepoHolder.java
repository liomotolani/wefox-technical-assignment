package com.wefox.paymentprocessor.model;


import com.wefox.paymentprocessor.utils.RestUtils;
import com.wefox.paymentprocessor.repositories.AccountRepository;
import com.wefox.paymentprocessor.repositories.PaymentRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class RepoHolder {

  private final AccountRepository accountRepository;
  private final PaymentRepository paymentRepository;
  private final RestUtils restUtils;
}
