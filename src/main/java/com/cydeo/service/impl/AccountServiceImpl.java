package com.cydeo.service.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userID) {
        //we need to create Account object
        Account account = Account.builder().id(UUID.randomUUID()).userId(userID)
                .balance(balance).accountType(accountType).creationDate(creationDate)
                .accountStatus(AccountStatus.ACTIVE).build();

        //save into the database(repository)
        //return the object created
        return accountRepository.save(account);
    }

    @Override
    public List<Account> lisAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(UUID id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(UUID id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.ACTIVE);
    }


}
