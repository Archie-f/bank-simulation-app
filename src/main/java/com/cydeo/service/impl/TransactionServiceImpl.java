package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountRepository accountRepository, AccountService accountService, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {

        if(!underConstruction) {
        /*
               -if sender or receiver is null ?
               -if sender and receiver is the same account ?
               -if sender has enough balance to make transfer ?
               -if both accounts are checking, if not, one of them saving, it needs to be same userId
         */

            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

        /*
            after all validations are completed, and money is transferred, we need to create Transaction object and save/return it.
         */

            TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);
            //save into the db and return it
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            return transactionDTO;
        }else {
            throw new UnderConstructionException("App is under construction, please try again later.");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            AccountDTO senderAccountDTO = accountService.retrieveById(sender.getId());
            senderAccountDTO.setBalance(sender.getBalance());
            accountService.updateAccount(senderAccountDTO);

            AccountDTO receiverAccountDTO = accountService.retrieveById(receiver.getId());
            receiverAccountDTO.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAccountDTO);

        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        /*
            write an if statement that checks if one of the account is saving,
            and user of sender or receiver is not the same, throw AccountOwnershipException
         */
        if (sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING)
                && !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnershipException("If one of the account is saving, user must be the same for sender and receiver");
        }


    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -if the account exist in the database (repository)
         */
        if (sender==null || receiver==null){
            throw new BadRequestException("Sender or Receiver can not be null");
        }

        //if accounts are the same throw BadRequestException with saying accounts needs to be different
        System.out.println("sender.getId() = " + sender.getId());
        System.out.println("receiver.getId() = " + receiver.getId());
        if ((sender.getId()) == (receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver account");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());


    }

    private void findAccountById(Long id) {
        accountService.retrieveById(id);
    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        return transactionRepository.findAll().stream().map(transactionMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        return transactionRepository.findLast10Transactions()
                .stream().map(transactionMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        return transactionRepository.findTransactionListByAccountId(id)
                .stream().map(transactionMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
