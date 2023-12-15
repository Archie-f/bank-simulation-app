package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {
        //set the fields that do not exist in account dto
        accountDTO.setCreationDate(new Date());
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        //save into the database(repository)
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

    @Override
    public List<AccountDTO> listAllAccounts() {
        return accountRepository.findAll().stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account object based on id
        Account account = accountRepository.findById(id).get();
        //set status to deleted
        account.setAccountStatus(AccountStatus.DELETED);
        //save the updated account object
        accountRepository.save(account);

    }

    @Override
    public void activateAccount(Long id) {
        //find the account object based on id
        Account account = accountRepository.findById(id).get();
        //set status to active
        account.setAccountStatus(AccountStatus.ACTIVE);
        //save the updated account object
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return accountMapper.convertToDTO(accountRepository.findById(id).get());
    }

    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        return accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE)
                .stream().map(accountMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }


}
