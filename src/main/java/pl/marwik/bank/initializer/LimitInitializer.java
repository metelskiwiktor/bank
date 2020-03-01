package pl.marwik.bank.initializer;

import pl.marwik.bank.model.entity.TransferLimit;

import java.math.BigDecimal;

public class LimitInitializer {
    public static TransferLimit initialize(){
        TransferLimit transferLimit = new TransferLimit();
        transferLimit.setDepositLimit(new BigDecimal(1200));
        transferLimit.setTransferLimit(new BigDecimal(1000));
        transferLimit.setWithdrawLimit(new BigDecimal(800));

        return transferLimit;
    }
}
