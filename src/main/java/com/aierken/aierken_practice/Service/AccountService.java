package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.repository.AccountRepository;
import com.aierken.aierken_practice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public double withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        return account.getBalance();
    }

    public List<Account> filterAccountsOver1000(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUser_Id(userId).stream()
                .filter(account -> account.getBalance() > 1000)
                .collect(Collectors.toList());
    }

    public double sumBalancesOver1000(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUser_Id(userId).stream()
                .filter(account -> account.getBalance() > 1000)
                .mapToDouble(Account::getBalance)
                .sum();
    }
}
