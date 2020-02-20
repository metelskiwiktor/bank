package pl.marwik.bank.model.request;

import java.math.BigDecimal;

public interface TransactionDTO {
    String getSenderAccountNumber();
    BigDecimal getAmount();
    BigDecimal getSenderBalance();
}
