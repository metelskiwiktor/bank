package pl.marwik.bank.exception;

public class BankException extends RuntimeException {
    private final ExceptionCode code;

    public BankException(ExceptionCode code, Object... variables) {
        super(String.format(code.getDetailsPattern(), variables));
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "BankException{" +
                "code=" + code +
                '}';
    }
}

