package pl.marwik.bank.exception;

public enum ExceptionCode {

    BALANCE_IS_TOO_SMALL("TF_001", "The balance of account is too small", 400),
    ACCOUNT_NOT_FOUND("TF_002", "The provided account hasn't been found", 400),
    AMOUNT_IS_TOO_SMALL("TF_003", "The amount is too small", 400),
    BALANCE_IS_DIFFERENCE("TF_004", "Provided amount of account is diffrence that should be", 400),
    BRANCH_NOT_FOUND("B_001", "Branch not found", 400),
    BRANCH_ALREADY_EXIST("B_002", "Branch already exist", 400),
    USER_ALREADY_EXIST("U_001", "User already exist", 400),
    USER_NOT_FOUND("U_002", "User not found", 400),
    INVALID_CREDENTIALS("U_003", "Passed credentials are invalid", 401),
    USER_ALREADY_LOGGED_IN("U_004", "User already logged in", 400),
    TOKEN_NOT_FOUND("T_001", "Token not exist or is expired", 400),
    BANK_NOT_EXIST("BA_001", "Bank not exist", 400),
    BANK_ALREADY_EXIST("BA_002", "Bank already exist", 400),
    NOT_PERMITTED("P_001", "You're not permitted to do this action", 400),
    NOT_AUTHENTICATED("P_002", "You're not authenticated", 401),
    ACCOUNT_IS_BLOCKED("P_003", "Account is blocked", 400);


    private String code;
    private String detailsPattern;
    private int httpStatus;

    ExceptionCode(String code, String detailPattern, Integer httpStatus) {
        this.code = code;
        this.detailsPattern = detailPattern;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getDetailsPattern() {
        return detailsPattern;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }
}
