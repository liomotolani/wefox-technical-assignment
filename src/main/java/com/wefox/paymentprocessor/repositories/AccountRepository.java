package com.wefox.paymentprocessor.repositories;

import com.wefox.paymentprocessor.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Override
    Optional<Account> findById(Integer integer);
}
