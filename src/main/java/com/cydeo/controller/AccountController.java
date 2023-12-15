package com.cydeo.controller;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class AccountController {

    /*
        write a method to return index.html including account list information
        endpoint:index
     */
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndexPage(Model model){

        model.addAttribute("accountList",accountService.listAllAccounts());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model){

        //we need to provide empty account object
        model.addAttribute("accountDTO", new AccountDTO());
        //we need to provide accountType enum info for filling the dropdown options
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }

    //create a method to capture information from ui
    //print them on the console.
    //trigger createNewAccount method, create the account based on the user input.
    //once user created return back to the index page.
    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("accountDTO") AccountDTO accountDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){

            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }
        System.out.println(accountDTO);
        accountService.createNewAccount(accountDTO);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id") Long id){

        accountService.deleteAccount(id);

        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") Long id){

        accountService.activateAccount(id);

        return "redirect:/index";
    }


}