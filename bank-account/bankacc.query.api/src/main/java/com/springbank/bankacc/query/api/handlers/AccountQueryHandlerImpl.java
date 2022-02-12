package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.dto.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        var response = bankAccount.isPresent()
                ? new AccountLookupResponse("Bank Account Successfully Returned!", bankAccount.get())
                : new AccountLookupResponse("No Bank Account Found for Id - " + query.getId());
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findByHolderId(FindAccountByHolderIdQuery query) {
        var bankAccount = accountRepository.findByAccountHolderId(query.getFindHolderId());
        var response = bankAccount.isPresent()
                ? new AccountLookupResponse("Bank Account Successfully Returned!", bankAccount.get())
                : new AccountLookupResponse("No Bank Account Found for Holder Id - " + query.getFindHolderId());
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountsQuery query) {
        var bankAccountIterator = accountRepository.findAll();

        if (!bankAccountIterator.iterator().hasNext())
            return new AccountLookupResponse("No Bank Account were Found!");

        var bankAccounts = new ArrayList<BankAccount>();
        bankAccountIterator.forEach(i -> bankAccounts.add(i));
        var count = bankAccounts.size();
        return new AccountLookupResponse("Successfully Returned " + count + " Bank Account(s)! ", bankAccounts);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountsWithBalance(FindAccountWithBalanceQuery query) {
        var bankAccount = query.getEqualityType() == EqualityType.GREATHER_THAN
                ? accountRepository.findByBalanceGreatherThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());

        var respose = bankAccount != null && bankAccount.size() > 0
                ? new AccountLookupResponse("Successfully Returned " + bankAccount.size() + " Bank Account(s)!", bankAccount)
                : new AccountLookupResponse("No Bank Accounts were Found!");
        return respose;
    }
}
