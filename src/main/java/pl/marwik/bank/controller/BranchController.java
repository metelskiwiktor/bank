package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.request.CreateBranchDTO;
import pl.marwik.bank.service.BranchService;

@RestController
@RequestMapping("/branch")
public class BranchController {
    private BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/{accountNumber}/branch")
    public String getBranchName(@RequestHeader("tokenValue") String tokenValue, @PathVariable String accountNumber) {
        return branchService.getBranchName(accountNumber);
    }

    @PostMapping(value = "/create-branch", consumes = "application/json")
    public void createBranch(@RequestBody CreateBranchDTO createBranchDTO){
        branchService.createBranch(createBranchDTO);
    }
}
