package pl.marwik.bank.initializer;

import pl.marwik.bank.model.entity.Branch;
import pl.marwik.bank.model.request.CreateBranchDTO;

public class BranchInitialize {
    public static Branch generate(CreateBranchDTO createBranchDTO){
        Branch branch = new Branch();
        branch.setBranchName(createBranchDTO.getBranchName());

        return branch;
    }
}
