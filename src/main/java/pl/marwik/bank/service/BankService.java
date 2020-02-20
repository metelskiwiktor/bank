package pl.marwik.bank.service;

import pl.marwik.bank.model.request.CreateBankDTO;

public interface BankService {
    void addBank(CreateBankDTO createBankDTO);
}
