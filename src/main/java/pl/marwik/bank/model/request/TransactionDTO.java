package pl.marwik.bank.model.request;

import pl.marwik.bank.model.OperationType;

import java.math.BigDecimal;

public interface TransactionDTO {
    String getSenderAccountNumber();
    BigDecimal getAmount();
    BigDecimal getSenderBalance();
    OperationType getOperationType();
}
