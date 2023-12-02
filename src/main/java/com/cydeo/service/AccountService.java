package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userID);

    List<AccountDTO> listAllAccounts();

    void deleteAccount(UUID id);

    void activateAccount(UUID id);

    AccountDTO retrieveById(UUID id);
}
