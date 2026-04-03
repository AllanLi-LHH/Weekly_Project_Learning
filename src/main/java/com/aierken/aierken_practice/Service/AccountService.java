package com.aierken.aierken_practice.Service;

import java.util.stream.Collectors;

public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public double withdraw(Long accountId, double amount){
        Account account1 = accountRepository.findById(accountId);
        if(account1 == null){
            throw new RuntimeException("Account not found");
        }
        if(account1.getBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }
        account1.setBalance(account1.getBalance()-amount);
        accountRepository.save(account1);

        return account1.getBalance();
    }

    public List<Account> filterAccountsOver1000(Long userId){
        return accountRepository.findByUserId(userId).stream()
                .filter(account -> account.getBalance()>1000)
                .collect(Collectors.toList());
    }

    public double sumBalancesOver1000(Long userId){
        return accountRepository.findByUserId(userId).stream()
                .filter(account -> account.getBalance()>1000)
                .mapToDouble(Account::getBalance)
                .sum();
    }
}
