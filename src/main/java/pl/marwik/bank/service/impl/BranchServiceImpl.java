package pl.marwik.bank.service.impl;

import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.initializer.BranchInitialize;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Bank;
import pl.marwik.bank.model.entity.Branch;
import pl.marwik.bank.model.request.CreateBranchDTO;
import pl.marwik.bank.repository.AccountRepository;
import pl.marwik.bank.repository.BankRepository;
import pl.marwik.bank.repository.BranchRepository;
import pl.marwik.bank.service.BranchService;

import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {
    private BranchRepository branchRepository;
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public BranchServiceImpl(BranchRepository branchRepository, AccountRepository accountRepository, BankRepository bankRepository) {
        this.branchRepository = branchRepository;
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public String getBranchName(String accountNumber) throws BankException {
        Account account = getAccountByAccountNumber(accountNumber);

        return branchRepository
                .findBranchByAccountsContains(account)
                .orElseThrow(() -> new BankException(ExceptionCode.BRANCH_NOT_FOUND))
                .getBranchName();
    }

    @Override
    public void createBranch(CreateBranchDTO createBranchDTO) throws BankException {
        Bank bank = getBankByBankName(createBranchDTO.getBankName());
        Branch branch = BranchInitialize.generate(createBranchDTO);

        throwIfBranchExist(branch.getBranchName());

        bank.addBranch(branch);
        branchRepository.save(branch);
        bankRepository.save(bank);
    }

    private Bank getBankByBankName(String bankName){
        return bankRepository.findBankByName(bankName)
                .orElseThrow(() -> new BankException(ExceptionCode.BANK_NOT_EXIST));
    }

    private void throwIfBranchExist(String branchName) throws BankException{
        Optional<Branch> branch = branchRepository.findBranchByBranchName(branchName);

        if(branch.isPresent()){
            throw new BankException(ExceptionCode.BRANCH_ALREADY_EXIST);
        }
    }

    private Account getAccountByAccountNumber(String accountNumber) throws BankException{
        return accountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }
}
