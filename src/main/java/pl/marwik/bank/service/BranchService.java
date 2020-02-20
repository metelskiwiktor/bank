package pl.marwik.bank.service;

import pl.marwik.bank.model.request.CreateBranchDTO;

public interface BranchService {
    String getBranchName(String accountNumber);
    void createBranch(CreateBranchDTO createBranchDTO);
}
