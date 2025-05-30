package task.ing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import task.ing.model.dto.request.DepositDto;
import task.ing.model.dto.request.TransferDto;
import task.ing.model.dto.request.WithdrawDto;
import task.ing.model.dto.response.TransactionResponseDto;
import task.ing.service.TransactionService;
import task.ing.validation.Iban;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getById(@PathVariable int id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(
            @RequestBody @Valid DepositDto dto) {
        return ResponseEntity.ok(transactionService.deposit(dto));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDto> withdraw(
            @RequestBody @Valid WithdrawDto dto) {
        return ResponseEntity.ok(transactionService.withdraw(dto));
    }

    @PostMapping("/transfer")
    @Operation(summary = "transfer between accounts using IBAN")
    public ResponseEntity<TransactionResponseDto> transfer(
            @RequestBody @Valid TransferDto dto
//            @Valid @Iban @Parameter(description = "Sender IBAN") @RequestParam String senderIban,
//            @Valid @Iban @Parameter(description = "Receiver IBAN") @RequestParam String receiverIban,
//            @Parameter(description = "Transfer amount") @RequestParam BigDecimal amount
    ) {
        return ResponseEntity.ok(transactionService.transfer(dto));
    }


}
