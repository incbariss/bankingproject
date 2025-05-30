package task.ing.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import task.ing.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Details of money transfer, withdraw or deposit")
public record TransactionResponseDto(

        @Schema(description = "Sender account informations - only for transfer and withdraw")
        AccountResponseDto senderAccount,

        @Schema(description = "Receiver account informations - only for transfer and deposit")
        AccountResponseDto receiverAccount,

        @Schema(description = "Amount of money", example = "250.0")
        BigDecimal amount,

        @Schema(description = "Transaction date and hours")
        LocalDateTime transactionDate,

        @Schema(description = "Transaction type: (DEPOSIT, WITHDRAW, TRANSFER)", example = "TRANSFER")
        TransactionType transactionType
) {
}
