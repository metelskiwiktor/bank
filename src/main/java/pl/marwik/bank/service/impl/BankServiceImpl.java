package pl.marwik.bank.service.impl;

import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.initializer.BankInitialize;
import pl.marwik.bank.model.entity.Bank;
import pl.marwik.bank.model.request.CreateBankDTO;
import pl.marwik.bank.repository.BankRepository;
import pl.marwik.bank.service.BankService;

import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {
    private BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void addBank(CreateBankDTO createBankDTO) {
        throwIfBankExist(createBankDTO.getName());

        Bank bank = BankInitialize.initialize(createBankDTO.getName());

        bankRepository.save(bank);
    }

    private void throwIfBankExist(String bankName){
        Optional<Bank> bank = bankRepository
                .findBankByName(bankName);

        if(bank.isPresent()){
            throw new BankException(ExceptionCode.BANK_ALREADY_EXIST);
        }
    }

    private Bank getBankByBankName(String bankName){
        return bankRepository
                .findBankByName(bankName)
                .orElseThrow(() -> new BankException(ExceptionCode.BANK_NOT_EXIST));
    }
}
