package task.ing.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.ing.mapper.TransactionMapper;
import task.ing.model.dto.request.DepositDto;
import task.ing.model.dto.request.TransferDto;
import task.ing.model.dto.request.WithdrawDto;
import task.ing.model.dto.response.TransactionResponseDto;
import task.ing.model.entity.Account;
import task.ing.model.entity.Transaction;
import task.ing.model.enums.TransactionType;
import task.ing.repository.AccountRepository;
import task.ing.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyConversionService currencyConversionService;

    @Autowired
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository, CurrencyConversionService currencyConversionService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.currencyConversionService = currencyConversionService;
    }

    public List<TransactionResponseDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TransactionResponseDto> getTransactionById(int id) {
        return transactionRepository.findById(id)
                .map(TransactionMapper::toDto);
    }


    @Transactional
    public TransactionResponseDto deposit(DepositDto dto) {
        Account account = getAccountByIban(dto.receiverIban());
        validateAmount(dto.amount());

        account.setBalance(account.getBalance().add(dto.amount()));
        accountRepository.save(account);

//        Transaction tx = new Transaction();
//        tx.setSenderAccount(null);
//        tx.setReceiverAccount(account);
//        tx.setAmount(dto.amount());
//        tx.setTransactionType(TransactionType.DEPOSIT);
        Transaction tx = TransactionMapper.toEntity(dto, account);
        tx = transactionRepository.save(tx);
        return TransactionMapper.toDto(tx);
    }

    @Transactional
    public TransactionResponseDto withdraw(WithdrawDto dto) {
        Account account = getAccountByIban(dto.senderIban());
        validateAmount(dto.amount());

        if (account.getBalance().compareTo(dto.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(dto.amount()));
        accountRepository.save(account);

//        Transaction tx = new Transaction();
//        tx.setSenderAccount(account);
//        tx.setReceiverAccount(null);
//        tx.setAmount(dto.amount());
//        tx.setTransactionType(TransactionType.WITHDRAW);
        Transaction tx = TransactionMapper.toEntity(dto, account);
        tx = transactionRepository.save(tx);
        return TransactionMapper.toDto(tx);
    }

    @Transactional
    public TransactionResponseDto transfer(TransferDto dto) {
        Account sender = getAccountByIban(dto.senderIban());
        Account receiver = getAccountByIban(dto.receiverIban());
        validateAmount(dto.amount());

        if (sender.getBalance().compareTo(dto.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        BigDecimal convertedAmount = dto.amount();
        if (!sender.getCurrency().equals(receiver.getCurrency())) {
            convertedAmount = currencyConversionService.convertAmount(
                    sender.getCurrency().name(),
                    receiver.getCurrency().name(),
                    dto.amount()
            );
        }

        sender.setBalance(sender.getBalance().subtract(dto.amount()));
        accountRepository.save(sender);

        receiver.setBalance(receiver.getBalance().add(convertedAmount));
        accountRepository.save(receiver);

//        Transaction tx = new Transaction();
//        tx.setSenderAccount(sender);
//        tx.setReceiverAccount(receiver);
//        tx.setAmount(dto.amount());
//        tx.setTransactionType(TransactionType.TRANSFER);
        Transaction tx = TransactionMapper.toEntity(dto, sender, receiver);
        tx = transactionRepository.save(tx);
        return TransactionMapper.toDto(tx);
    }

//    private Account getAccount(int accountId) {
//        return accountRepository.findById(accountId).
//                orElseThrow(() -> new IllegalArgumentException("Account not found " + accountId));
//    }

    private Account getAccountByIban(String iban) {
        return accountRepository.findByIban(iban)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with IBAN " + iban));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
