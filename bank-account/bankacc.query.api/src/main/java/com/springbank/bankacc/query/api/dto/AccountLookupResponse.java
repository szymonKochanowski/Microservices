package com.springbank.bankacc.query.api.dto;

import com.springbank.bankacc.core.dto.BaseResponse;
import com.springbank.bankacc.core.models.BankAccount;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class AccountLookupResponse extends BaseResponse {



    private List<BankAccount> accountList;
    public AccountLookupResponse(String message) {
        super(message);
    }

    public AccountLookupResponse(String message, List<BankAccount> accountList) {
        super(message);
        this.accountList = accountList;
    }

    public AccountLookupResponse(String message, BankAccount account) {
        super(message);
        this.accountList = new ArrayList<>();
        this.accountList.add(account);
    }

    public List<BankAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<BankAccount> accountList) {
        this.accountList = accountList;
    }
}
