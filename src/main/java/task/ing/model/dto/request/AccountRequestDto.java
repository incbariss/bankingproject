package task.ing.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import task.ing.model.enums.Currency;

public record AccountRequestDto(

        @Positive(message = "User id must be a positive number")
        int userId,

        @NotNull(message = "Currency(TRY, EUR, USD) cannot be null")
        Currency currency

) {
}
