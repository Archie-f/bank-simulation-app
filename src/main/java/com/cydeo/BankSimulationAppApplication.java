package com.cydeo;

import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {
        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);

//        //get the account and transaction service beans
        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);
//
//        //create 2 accounts sender and receiver
//        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70),new Date(), AccountType.SAVING,1L);
//        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50),new Date(), AccountType.SAVING,2L);
//        Account receiver2 = accountService.createNewAccount(BigDecimal.valueOf(5000),new Date(), AccountType.CHECKING,123L);
//        Account receiver3 = accountService.createNewAccount(BigDecimal.valueOf(7500),new Date(), AccountType.SAVING,124L);
//        Account receiver2 = null;
//
//        //see all created accounts first
//        accountService.lisAllAccounts().forEach(System.out::println);
//
//        //test if the conditions work and exceptions are thrown correctly
//        transactionService.makeTransfer(sender,receiver,BigDecimal.valueOf(40),new Date(),"Transaction 1");
//
//        //see transaction
//        System.out.println(transactionService.findAllTransaction().get(0));
//
//        //see all created accounts after transaction
//        accountService.lisAllAccounts().forEach(System.out::println);


    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
