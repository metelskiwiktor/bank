package pl.marwik.bank.model.helper;

import java.math.BigDecimal;

public enum TransferMoney {
    RECIPIENT {
        @Override
        public BigDecimal transfer(BigDecimal amount, BigDecimal add) {
            return amount.add(add);
        }
    }, SENDER {
        @Override
        public BigDecimal transfer(BigDecimal amount, BigDecimal minus) {
            return amount.subtract(minus);
        }
    };

    public abstract BigDecimal transfer(BigDecimal amount, BigDecimal charge);
}
