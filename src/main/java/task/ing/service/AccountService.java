package task.ing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.ing.mapper.AccountMapper;
import task.ing.model.dto.request.AccountRequestDto;
import task.ing.model.dto.response.AccountResponseDto;
import task.ing.model.entity.Account;
import task.ing.model.entity.User;
import task.ing.repository.AccountRepository;
import task.ing.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountResponseDto createAccount(AccountRequestDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + dto.userId()));

        Account account = AccountMapper.toEntity(dto, user);
        account = accountRepository.save(account);
        return AccountMapper.toDto(account);
    }

    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAllByIsDeletedFalse();
        return accounts.stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
    }

    public AccountResponseDto getAccountById(int id) {
        Optional<Account> account = accountRepository.findByIdAndIsDeletedFalse(id); //optional tam olarak ne olduguna bak
        return account.map(AccountMapper::toDto).orElse(null);
    }

//    public AccountResponseDto updateAccount(int id, AccountRequestDto dto) {
//        Optional<Account> existingAccount = accountRepository.findByIdAndIsDeletedFalse(id);
//        if (existingAccount.isPresent()) {
//            Account account = existingAccount.get();
//            account.setAccountNumber(dto.accountNumber());
//            account = accountRepository.save(account);
//            return AccountMapper.toDto(account);
//        }
//        return null;
//    }

    public void deleteAccount(int id) {
        Account account = accountRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found or already deleted"));
        account.setDeleted(true);
        accountRepository.save(account);
    }
}
