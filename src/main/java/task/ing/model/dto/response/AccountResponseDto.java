package task.ing.model.dto.response;

import task.ing.model.enums.Currency;

import java.math.BigDecimal;

public record AccountResponseDto(
        String iban,

        String accountNumber,

        BigDecimal balance,

        Currency currency

) {

}
