package task.ing.mapper;

import task.ing.model.dto.request.DepositDto;
import task.ing.model.dto.request.TransactionRequestDto;
import task.ing.model.dto.request.TransferDto;
import task.ing.model.dto.request.WithdrawDto;
import task.ing.model.dto.response.TransactionResponseDto;
import task.ing.model.entity.Account;
import task.ing.model.entity.Transaction;
import task.ing.model.enums.TransactionType;

public interface TransactionMapper {

    static Transaction toEntity(DepositDto dto, Account receiver) {
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(null);
        transaction.setReceiverAccount(receiver);
        transaction.setAmount(dto.amount());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        return transaction;
    }

    static Transaction toEntity(WithdrawDto dto, Account sender) {
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(null);
        transaction.setAmount(dto.amount());
        transaction.setTransactionType(TransactionType.WITHDRAW);
        return transaction;
    }

    static Transaction toEntity(TransferDto dto, Account sender, Account receiver) {
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);
        transaction.setAmount(dto.amount());
        transaction.setTransactionType(TransactionType.TRANSFER);
        return transaction;
    }

    static TransactionResponseDto toDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getSenderAccount() != null ?
                        AccountMapper.toDto(transaction.getSenderAccount()) : null,
                transaction.getReceiverAccount() != null ?
                        AccountMapper.toDto(transaction.getReceiverAccount()) : null,
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getTransactionType()
        );
    }
}
