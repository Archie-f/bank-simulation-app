package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userID);

    List<AccountDTO> listAllAccounts();

    void deleteAccount(Long id);

    void activateAccount(Long id);

    AccountDTO retrieveById(Long id);
}
