package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {

    List<Account> findAllByAccountStatus(AccountStatus accountStatus);


}
