package com.felixlm.accountsmodule.api;

import com.felixlm.accountsmodule.api.dto.AccountResponse;
import com.felixlm.accountsmodule.api.dto.AmountRequest;
import com.felixlm.accountsmodule.api.dto.CreateAccountRequest;
import com.felixlm.accountsmodule.api.dto.TransactionResponse;
import com.felixlm.accountsmodule.application.AccountService;
import com.felixlm.accountsmodule.domain.Account;
import com.felixlm.accountsmodule.domain.Transaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.userId(), request.type());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(account));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable UUID accountId,
            @Valid @RequestBody AmountRequest request
    ) {
        Account account = accountService.deposit(accountId, request.amount());
        return ResponseEntity.ok(mapToResponse(account));
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable UUID accountId,
            @Valid @RequestBody AmountRequest request
    ) {
        Account account = accountService.withdraw(accountId, request.amount());
        return ResponseEntity.ok(mapToResponse(account));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> transactions(@PathVariable UUID accountId) {
        List<TransactionResponse> transactions = accountService.getTransactions(accountId).stream()
            .map(this::mapToResponse)
            .toList();
        return ResponseEntity.ok(transactions);
    }

    private AccountResponse mapToResponse(Account account) {
        return new AccountResponse(
            account.getId(),
            account.getUserId(),
            account.getType(),
            account.getBalance().asDouble()
        );
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getAccountId(),
            transaction.getAmount().asDouble(),
            transaction.getOccurredAt(),
            transaction.getDescription()
        );
    }
}

