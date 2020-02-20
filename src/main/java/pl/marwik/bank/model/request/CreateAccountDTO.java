package pl.marwik.bank.model.request;

public class CreateAccountDTO {
    private String branchName;
    private UserDTO userDTO;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "CreateAccountDTO{" +
                "branchName='" + branchName + '\'' +
                ", userDTO=" + userDTO +
                '}';
    }
}
