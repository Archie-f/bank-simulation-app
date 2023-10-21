package com.cydeo.model;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Account {

    private UUID id;
    @NonNull
    @Positive
    private BigDecimal balance;
    @NonNull
    private AccountType accountType;
    private Date creationDate;
    @NonNull
    private Long userId;
    private AccountStatus accountStatus;


}
