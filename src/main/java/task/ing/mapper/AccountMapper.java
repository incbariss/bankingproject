package task.ing.mapper;

import task.ing.model.dto.request.AccountRequestDto;
import task.ing.model.dto.response.AccountResponseDto;
import task.ing.model.entity.Account;
import task.ing.model.entity.User;

import java.math.BigDecimal;

import static task.ing.helper.AccountHelper.generateAccountNumber;
import static task.ing.helper.AccountHelper.generateIban;


public interface AccountMapper {

    static Account toEntity(AccountRequestDto dto, User user) {
        String accountNumber = generateAccountNumber();
        String iban = generateIban(accountNumber);

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setIban(iban);
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(dto.currency());
        return account;
    }

    static AccountResponseDto toDto(Account account) {
        return new AccountResponseDto(account.getIban(), account.getAccountNumber(), account.getBalance(), account.getCurrency());
    }


}
