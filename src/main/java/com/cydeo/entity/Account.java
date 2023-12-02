package com.cydeo.entity;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(columnDefinition = "TIMESTAMP")
    private Date creationDate;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
