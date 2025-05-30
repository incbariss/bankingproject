package task.ing.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.ing.model.dto.request.AccountRequestDto;
import task.ing.model.dto.response.AccountResponseDto;
import task.ing.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody @Valid AccountRequestDto dto) {
        AccountResponseDto savedAccount = accountService.createAccount(dto);
        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        List<AccountResponseDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable int id) {
        AccountResponseDto account = accountService.getAccountById(id);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable int id, @RequestBody @Valid AccountRequestDto dto) {
//        AccountResponseDto updatedAccount = accountService.updateAccount(id, dto);
//        return updatedAccount != null ? ResponseEntity.ok(updatedAccount) : ResponseEntity.notFound().build();
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
